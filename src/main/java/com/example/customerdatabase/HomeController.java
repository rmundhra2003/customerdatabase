package com.example.customerdatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class HomeController {
    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/")
    public String HomePage(Model model){
        model.addAttribute("customers", customerRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String customerForm(Model model){
        model.addAttribute("customer", new Customer());

        return "customerform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Customer customer, BindingResult results){
        if (results.hasErrors()){
            return "customerform";
        }
        customerRepository.save(customer);
        return "redirect:/";
    }

    @GetMapping("/find")
    public String findForm(Model model){
        model.addAttribute("lastname", new String());

        return "findForm";
    }

    @PostMapping("/processfind")
    public String processForm(@RequestParam ("lastname")String lastname, Model model){

        model.addAttribute("customers", customerRepository.findAllByLastNameContainingIgnoreCase(lastname));
        return "list";
    }
}
