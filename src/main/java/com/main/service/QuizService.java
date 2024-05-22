package com.main.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.main.model.Question;
import com.main.model.QuestionForm;
import com.main.model.Result;
import com.main.model.User;
import com.main.repository.QuestionRepository;
import com.main.repository.ResultRepository;
import com.main.repository.UserRepository;

@Service
public class QuizService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    Question ques;
    @Autowired
    QuestionForm questionForm;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    Result result;
    @Autowired
    ResultRepository resultRepository;

    public QuestionForm getQuestions() {
        List<Question> allQues = questionRepository.findAll();
        List<Question> qList = new ArrayList<Question>();

        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            int ind = rand.nextInt(allQues.size());
            qList.add(allQues.get(ind));
            allQues.remove(ind);
        }
        questionForm.setQuestions(qList);
        return questionForm;
    }

    public int getResult(QuestionForm questionForm) {
        int correct = 0;

        for (Question q : questionForm.getQuestions()) {
            if (q.getAns() == q.getChosen()) {
                correct++;
            }
        }
        return correct;
    }

    public void saveScore(Result result) {
        Result saveResult = new Result();
        saveResult.setUserName(result.getUserName());
        saveResult.setTotalCorrect(result.getTotalCorrect());
        resultRepository.save(saveResult);
    }

    public String scoreListService(Model model) {
        List<Result> scoreList =
                resultRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
        model.addAttribute("scoreList", scoreList);
        return "scoreboard.html";
    }

    public String loginService(Principal principal, Model model, RedirectAttributes ra) {
        System.out.println("*****loginService " + principal);
        if (principal == null) {
            ra.addFlashAttribute("warning", "Fields can't be empty!");
            return "home";
        } else if (!userRepository.existsByUserId(principal.getName())) {
            ra.addFlashAttribute("warning", "Invalid User Id or Password!");
            return "home";
        } else {
            User userObj = userRepository.findByUserId(principal.getName());
            if (userObj.isSubmitted()) {
                model.addAttribute("userObj", userObj);
                return "redirect:/myScore";
            }
            System.out.println("*****loginService: " + userObj);
            return startQuizService(userObj, model, ra);
        }
    }

    public String startQuizService(User userObj, Model model, RedirectAttributes ra) {
        questionForm = getQuestions();
        model.addAttribute("questionForm", questionForm);
        model.addAttribute("userObj", userObj);

        System.out.println("*****startQuizService: " + userObj);
        return "quiz";
    }

    public String submitQuizService(QuestionForm questionForm, String userId, Model model) {

        User userObj = userRepository.findByUserId(userId);
        // System.out.println("*****submitQuizService " + userObj);
        // System.out.println("*****submitQuizService " + questionForm);
        int totCorrect = getResult(questionForm);
        // System.out.println("*****Submit Entered!" + " " + userObj.getUserId() + " "
        // + userObj.getUserName() + " " + totCorrect);

        userObj.setSubmitted(true);
        userObj.setTotalCorrect(totCorrect);

        Result newResult = new Result();
        newResult.setUserId(userObj.getUserId());
        newResult.setUserName(userObj.getUserName());
        newResult.setTotalCorrect(userObj.getTotalCorrect());

        userRepository.save(userObj);
        resultRepository.save(newResult);

        // model.addAttribute("userObj", userObj);
        return "redirect:/myScore";
    }

    public String registerUserService(RedirectAttributes ra, Model model, User userObj) {
        if (userObj == null) {
            ra.addFlashAttribute("warning", "object is null");
        } else if (userObj.getUserId().isEmpty() || userObj.getUsername().isEmpty()
                || userObj.getUserPass().isEmpty()) {
            ra.addFlashAttribute("warning", "Fields can't be empty!");
        } else if (userRepository.existsByUserId(userObj.getUserId()) == true) {
            ra.addFlashAttribute("warning", "User ID already exists!");
        } else {
            String pass = new BCryptPasswordEncoder().encode(userObj.getPassword());
            userObj.setUserPass(pass);
            userRepository.save(userObj);
            ra.addFlashAttribute("success", "Account created successfully!");
        }

        return "redirect:/home";
    }
}
