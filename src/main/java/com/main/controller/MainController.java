package com.main.controller;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.main.model.QuestionForm;
import com.main.model.User;
import com.main.repository.ResultRepository;
import com.main.repository.UserRepository;
import com.main.service.QuizService;

@Controller
public class MainController {

    @Autowired
    User userObj;
    @Autowired
    QuizService quizService;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ResultRepository resultRepo;


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


    @GetMapping("/startQuiz")
    public String startQuiz(Principal principal, Model model, RedirectAttributes ra) {
        return quizService.loginService(principal, model, ra);
    }

    @GetMapping("/myScore")
    public String myResult(Principal p, Model model) {
        System.out.println("myScore principle " + p.getName());
        User userObj = userRepo.findByUserId(p.getName());
        model.addAttribute("userObj", userObj);
        return "result";
    }

    @PostMapping("/submitQuiz")
    public String submitQuiz(@ModelAttribute("questionForm") QuestionForm questionForm,
            @RequestParam String userId, Model model) {
        return quizService.submitQuizService(questionForm, userId, model);
    }

    @GetMapping(value = "/scoreList")
    public String scoreList(Model model) {
        return quizService.scoreListService(model);
    }

}
