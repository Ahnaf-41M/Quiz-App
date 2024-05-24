package com.main.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.main.model.QuestionForm;
import com.main.model.Result;
import com.main.model.User;
import com.main.repository.QuizRepository;
import com.main.repository.ResultRepository;
import com.main.repository.TopicRepository;
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

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    TopicRepository topicRepository;


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
    public String startQuiz(Principal principal, Model model, RedirectAttributes ra,
            @RequestParam int topicId) {
        System.out.println("****Topic ID: " + topicId);
        return quizService.loginService(principal, model, ra, topicId);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        return quizService.dashboardService(model, principal);
    }

    @GetMapping("/myResult")
    public String myResult(Principal p, Model model) {
        System.out.println("myResult principle " + p.getName());
        // User userObj = userRepo.findByUserId(p.getName());
        // model.addAttribute("userObj", userObj);

        List<Result> resultList = resultRepo.findByUserId(p.getName());
        model.addAttribute("resultList", resultList);
        return "myResult";
    }

    @PostMapping("/submitQuiz")
    public String submitQuiz(@ModelAttribute QuestionForm questionForm, @RequestParam int topicId,
            Principal principal, Model model) {
        return quizService.submitQuizService(questionForm, principal.getName(), model, topicId);
    }

    @GetMapping("/topScores")
    public String topScores(Model model) {
        return quizService.topScoresService(model);
    }

}
