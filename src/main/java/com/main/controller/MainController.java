package com.main.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import com.main.repository.ResultRepo;
import com.main.repository.UserRepo;
import com.main.service.QuizService;

@Controller
public class MainController {

    @Autowired
    Result result;

    Boolean submitted = false;

    @Autowired
    QuizService qService;

    @Autowired
    UserRepo uRepo;

    @Autowired
    ResultRepo rRepo;

    @ModelAttribute
    public Result getResult() {
        return result;
    }

    @GetMapping({"/", "/home"})
    public String home(@ModelAttribute("userObj") User userObj, Model mod) {

        return "index.html";
    }

    @PostMapping("/home")
    public String home(RedirectAttributes ra, Model mod, User userObj) {
        if (userObj == null) {
            ra.addFlashAttribute("warning", "object is null");
        } else if (userObj.getUserId() == -1 || userObj.getUserName().isEmpty()
                || userObj.getUserPass().isEmpty()) {
            ra.addFlashAttribute("warning", "Fields can't be empty!");
        } else if (uRepo.existsById(userObj.getUserId()) == true) {
            ra.addFlashAttribute("warning", "User ID already exists!");
        } else {
            uRepo.save(userObj);
            ra.addFlashAttribute("warning", "Account created successfully!");
        }
        mod.addAttribute("userObj", userObj);
        return "redirect:/home";
    }

    @PostMapping("/quiz")
    public String quiz(@ModelAttribute("userObj") User userObj, Model mod, RedirectAttributes ra) {
        User tmpObj = uRepo.getOne(userObj.getUserId());
        System.out.println("## Quiz: " + userObj.getUserId());

        if (tmpObj.getUserPass().equals(userObj.getUserPass()) == false) {
            ra.addFlashAttribute("warning", "Invalid Password!");
            return "redirect:/home";
        }
        userObj = tmpObj;
        QuestionForm qForm = qService.getQuestions();
        mod.addAttribute("qForm", qForm);
        mod.addAttribute("userObj", tmpObj);

        return "quiz.html";
    }

    @PostMapping("/submit")
    public String submit(@RequestParam int userId, @ModelAttribute QuestionForm qForm, Model mod) {
        // System.out.println("##Submit: " + userId);

        User tmpObj = uRepo.getOne(userId);
        boolean ok = tmpObj.isSubmitted();
        mod.addAttribute("userObj", tmpObj);

        if (!ok) {
            int totCorrect = qService.getResult(qForm);
            System.out.println(
                    "Submit Entered!" + " " + tmpObj.getUserId() + " " + tmpObj.getUserName());

            tmpObj.setSubmitted(true);
            tmpObj.setTotalCorrect(totCorrect);

            result.setId(tmpObj.getUserId());
            result.setUsername(tmpObj.getUserName());
            result.setTotalCorrect(tmpObj.getTotalCorrect());

            uRepo.save(tmpObj);
            rRepo.save(result);
        }
        return "result.html";
    }

    @GetMapping(value = "/score")
    public String score(Model mod) {
        // List<Result> sList = qService.getTopScore();
        List<Result> sList = rRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
        mod.addAttribute("sList", sList);
        return "scoreboard.html";
    }

}
