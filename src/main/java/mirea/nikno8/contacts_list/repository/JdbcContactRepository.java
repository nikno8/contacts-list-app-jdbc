package mirea.nikno8.contacts_list.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mirea.nikno8.contacts_list.entities.Contact;
import mirea.nikno8.contacts_list.exception.ContactNotFoundException;
import mirea.nikno8.contacts_list.repository.mapper.ContactRowMapper;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("Calling JdbcContactRepository -> findAll");
        String sql = "SELECT * FROM contacts_schema.contact";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("Calling JdbcContactRepository -> findById");
        String sql = "SELECT * FROM contacts_schema.contact WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                )
        );
        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Calling JdbcContactRepository -> save with Contact: {}", contact);

        contact.setId(System.currentTimeMillis());
        String sql = "INSERT INTO contacts_schema.contact (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(),
                contact.getEmail(), contact.getPhone(), contact.getId());

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Calling JdbcContactRepository -> update with Contact: {}", contact);
        Contact existedContact = findById(contact.getId()).orElse(null);
        if (existedContact != null) {
            String sql = "UPDATE contacts_schema.contact SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(),
                    contact.getEmail(), contact.getPhone(), contact.getId());
            return contact;
        }
        log.warn("Contact with ID: {} was not found", contact.getId());

        throw new ContactNotFoundException("Contact for update not found! ID: " + contact.getId());

    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling JdbcContactRepository -> deleteById with ID: {}", id);

        String sql = "DELETE FROM contacts_schema.contact WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


}
