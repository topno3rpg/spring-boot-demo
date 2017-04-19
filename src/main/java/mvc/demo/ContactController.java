package mvc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2016/8/12.
 */
@Controller
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping("/list")
    @ResponseBody
    public List<Contact> home(Map<String, Object> model) {
        List<Contact> contacts = contactRepository.findAll();
        return contacts;
    }

    @RequestMapping("add")
    @ResponseBody
    public String save() {
        Contact contact = new Contact();
        contact.setFirstName("1");
        contact.setLastName("1");
        contact.setPhoneNumber("1");
        contact.setEmailAddress("1");
        contactRepository.save(contact);
        return "OK";
    }

}
