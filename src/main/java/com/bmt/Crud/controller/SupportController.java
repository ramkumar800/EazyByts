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
import org.springframework.web.bind.annotation.RequestParam;

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

    // Show edit form for support
    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        Support support = repon.getSupport(id);

        // Redirect if support doesn't exist
        if (support == null) {
            return "redirect:/supports";
        }

        // Populate the DTO for editing
        SupportDto supportDto = new SupportDto();
        supportDto.setFirstName(support.getFirstname());
        supportDto.setLastName(support.getLastname());
        supportDto.setEmail(support.getEmail());
        supportDto.setPhone(support.getPhone());
        supportDto.setAddress(support.getAddress());

        model.addAttribute("support", support); // Pass the support entity
        model.addAttribute("supportDto", supportDto); // Pass the DTO
        return "supports/edit";
    }

    // Handle edit support POST request
    @PostMapping("/edit")
    public String updateSupport(
        Model model,
        @RequestParam int id,
        @Valid @ModelAttribute("supportDto") SupportDto supportDto,
        BindingResult result) {

        Support support = repon.getSupport(id);

        // Redirect if the support entity doesn't exist
        if (support == null) {
            return "redirect:/supports";
        }

        // Return to edit form if validation errors exist
        if (result.hasErrors()) {
            model.addAttribute("support", support); // Ensure model has the original entity
            return "supports/edit";
        }

        // Update support details
        support.setFirstname(supportDto.getFirstName());
        support.setLastname(supportDto.getLastName());
        support.setEmail(supportDto.getEmail());
        support.setPhone(supportDto.getPhone());
        support.setAddress(supportDto.getAddress());

        repon.updateSupport(support); // Update support entity in the repository
        return "redirect:/supports";  // Redirect after successful update
    }

    // Handle delete support request
    @GetMapping("/delete")
    public String deleteSupport(@RequestParam int id) {
        repon.deleteSupport(id); // Delete support by ID
        return "redirect:/supports";
    }
}

