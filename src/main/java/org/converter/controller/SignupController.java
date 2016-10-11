package org.converter.controller;

import org.converter.controller.form.SignupForm;
import org.converter.domain.User;
import org.converter.exceptions.UserExistsException;
import org.converter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignupController {

    private static final String SIGNUP_FORM_ATTRIBUTE_NAME = "signupForm";

    private UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm form, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if(!form.getPassword().equals(form.getPasswordConfirmation())) {
            bindingResult.addError(new FieldError(SIGNUP_FORM_ATTRIBUTE_NAME, "passwordConfirmation", "The password doesn't match. Please try again."));
            return "signup";
        }

        User userToSave = buildUser(form);
        try {
            userService.save(userToSave);
        } catch (UserExistsException ex) {
            bindingResult.addError(new FieldError(SIGNUP_FORM_ATTRIBUTE_NAME, "email", "This email already registered."));
            return "signup";
        }

        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String getSignupForm(Model model){
        model.addAttribute(SIGNUP_FORM_ATTRIBUTE_NAME, new SignupForm());
        return "signup";
    }

    private User buildUser(SignupForm signupForm) {
        User user = new User();
        user.setEmail(signupForm.getEmail());
        user.setPassword(signupForm.getPassword());
        user.setCountry(signupForm.getCountry());
        user.setCity(signupForm.getCity());
        user.setStreet(signupForm.getStreet());
        user.setZipCode(signupForm.getZipCode());

        return user;
    }
}
