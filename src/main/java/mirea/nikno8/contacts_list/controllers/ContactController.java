package mirea.nikno8.contacts_list.controllers;

import lombok.RequiredArgsConstructor;
import mirea.nikno8.contacts_list.entities.Contact;
import mirea.nikno8.contacts_list.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contactService.findAll());

        return "index";
    }

    @GetMapping("/contacts/create")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "create";
    }

    @PostMapping("/contacts/create")
    public String createContact(@ModelAttribute Contact contact) {
        contactService.save(contact);
        return "redirect:/";

    }

    @GetMapping("contacts/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.findById(id);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "create";
        }
        return "redirect:/";
    }

    @PostMapping("/contacts/edit")
    public String editContact(@ModelAttribute Contact contact) {
        contactService.update(contact);
        return "redirect:/";
    }

    @GetMapping("/contacts/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteById(id);

        return "redirect:/";
    }
}
