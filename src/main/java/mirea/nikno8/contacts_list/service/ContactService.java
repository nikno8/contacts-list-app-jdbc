package mirea.nikno8.contacts_list.service;

import mirea.nikno8.contacts_list.entities.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> findAll();

    Contact findById(Long id);
    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);

}
