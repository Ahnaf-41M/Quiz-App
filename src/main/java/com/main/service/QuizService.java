package com.main.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.main.model.Question;
import com.main.model.QuestionForm;
import com.main.model.Quiz;
import com.main.model.Result;
import com.main.model.Test;
import com.main.model.User;
import com.main.repository.QuestionRepository;
import com.main.repository.QuizRepository;
import com.main.repository.ResultRepository;
import com.main.repository.TestRepository;
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
    QuizRepository quizRepository;
    @Autowired
    Result result;
    @Autowired
    ResultRepository resultRepository;

    @Autowired
    TestRepository testRepository;

    public QuestionForm getQuestions(int quizId) {
        List<Quiz> quizList = quizRepository.findByQuizId(quizId);
        List<Question> qList = new ArrayList<Question>();

        for (Quiz quiz : quizList) {
            qList.add(questionRepository.findByQuesId(quiz.getQuesId()));
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

    public String topScoresService(Model model) {
        List<Result> topResults =
                resultRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
        List<Test> testList = testRepository.findAll();
        model.addAttribute("topResults", topResults);
        model.addAttribute("testList", testList);
        return "topScores";
    }

    public String loginService(Principal principal, Model model, RedirectAttributes ra,
            int testId) {

        System.out.println("*****loginService " + principal);
        if (principal == null) {
            ra.addFlashAttribute("warning", "Fields can't be empty!");
            return "home";
        } else if (!userRepository.existsByUserId(principal.getName())) {
            ra.addFlashAttribute("warning", "Invalid User Id or Password!");
            return "home";
        } else {
            User userObj = userRepository.findByUserId(principal.getName());
            return startQuizService(userObj, model, ra, testId);
        }
    }

    public String startQuizService(User userObj, Model model, RedirectAttributes ra, int quizId) {
        questionForm = getQuestions(quizId);
        model.addAttribute("questionForm", questionForm);
        model.addAttribute("userObj", userObj);
        model.addAttribute("quizName", testRepository.findByTestId(quizId).getTestName());
        model.addAttribute("quizId", quizId);

        System.out.println("*****startQuizService: " + userObj);
        return "quiz";
    }

    public String submitQuizService(QuestionForm questionForm, String userId, Model model,
            int quizId) {

        User userObj = userRepository.findByUserId(userId);
        int totCorrect = getResult(questionForm);
        String quizName = testRepository.findByTestId(quizId).getTestName();

        Result newResult = new Result();
        userObj.setTotalCorrect(totCorrect);
        newResult.setUserId(userObj.getUserId());
        newResult.setUserName(userObj.getUserName());
        newResult.setQuizId(quizId);
        newResult.setQuizName(quizName);
        newResult.setTotalQuestions(questionForm.getQuestions().size());
        newResult.setTotalCorrect(userObj.getTotalCorrect());

        userRepository.save(userObj);
        resultRepository.save(newResult);

        List<Result> resulList = resultRepository.findByUserId(userObj.getUserId());
        model.addAttribute("result", resulList);
        return "redirect:/user/myResult";
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

    public String dashboardService(Model model, Principal principal) {
        List<Quiz> allQuiz = quizRepository.findAll();
        List<Quiz> quizList = new ArrayList<>();
        Set<Integer> quizIds = new HashSet<>();
        List<Test> testList = testRepository.findAll();

        for (Quiz quiz : allQuiz) {
            if (!resultRepository.existsByUserIdAndQuizId(principal.getName(), quiz.getQuizId())) {
                if (!quizIds.contains(quiz.getQuizId())) {
                    quizList.add(quiz);
                    quizIds.add(quiz.getQuizId());
                }
            }
        }

        model.addAttribute("quizList", quizList);
        model.addAttribute("testList", testList);
        return "dashboard";
    }
}
