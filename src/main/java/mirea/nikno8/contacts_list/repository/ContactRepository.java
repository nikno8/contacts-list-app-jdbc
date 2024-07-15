package mirea.nikno8.contacts_list.repository;

import mirea.nikno8.contacts_list.entities.Contact;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ContactRepository {

    List<Contact> findAll();
    Optional<Contact> findById(Long id);

    Contact save(Contact task);

    Contact update(Contact task);

    void deleteById(Long id);

}
