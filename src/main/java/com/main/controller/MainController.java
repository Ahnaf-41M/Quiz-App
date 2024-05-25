package com.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.main.model.User;
import com.main.service.QuizService;

@Controller
public class MainController {

    @Autowired
    QuizService quizService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("userObj", new User());
        return "userSignIn.html";
    }

    @PostMapping("/registerUser")
    public String registerUser(RedirectAttributes ra, Model model,
            @ModelAttribute("userObj") User userObj) {
        return quizService.registerUserService(ra, model, userObj);
    }
}
