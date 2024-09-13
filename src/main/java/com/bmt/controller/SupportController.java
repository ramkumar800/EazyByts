package com.bmt.Crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.bmt.Crud.models.Support;
import com.bmt.Crud.models.SupportDto;
import com.bmt.Crud.repositories.SupportRepository;

import jakarta.validation.Valid;



	
@Controller
@RequestMapping("/supports")
public class SupportController {

    @Autowired
    private SupportRepository repon;

    // List all supports
    @GetMapping
    public String getSupports(Model model) {
        List<Support> supports = repon.getSupports();
        model.addAttribute("supports", supports);
        return "supports/new";
    }

    // Show create support form
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        SupportDto supportDto = new SupportDto(); // Consistent naming
        model.addAttribute("supportDto", supportDto);
        return "supports/create";
    }

    // Handle create support POST request
    @PostMapping("/create")
    public String createSupport(
        @Valid @ModelAttribute("supportDto") SupportDto supportDto, // Proper binding
        BindingResult result) {

        // Check if the email is already in use
        if (repon.getSupport(supportDto.getEmail()) != null) {
            result.addError(new FieldError("supportDto", "email", supportDto.getEmail(),
                    false, null, null, "Email address is already used"));
        }

        // Return to create form if validation errors exist
        if (result.hasErrors()) {
            return "supports/create";
        }

        // Create new Support entity
        Support support = new Support();
        support.setFirstname(supportDto.getFirstName());
        support.setLastname(supportDto.getLastName());
        support.setEmail(supportDto.getEmail());
        support.setPhone(supportDto.getPhone());
        support.setAddress(supportDto.getAddress());

        repon.createSupport(support); 
        
        // Save support entity
        
        return "redirect:/supports/create";  // Redirect after success
    }

   
}

