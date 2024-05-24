package com.main.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.main.model.Question;
import com.main.model.QuestionForm;
import com.main.model.Result;
import com.main.model.Topic;
import com.main.model.User;
import com.main.repository.QuestionRepository;
import com.main.repository.QuizRepository;
import com.main.repository.ResultRepository;
import com.main.repository.TopicRepository;
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
    TopicRepository topicRepository;

    public QuestionForm getQuestions(int topicId) {
        List<Question> allQues = questionRepository.findByTopicId(topicId);
        List<Question> qList = new ArrayList<Question>();
        int totQues = allQues.size();

        Random rand = new Random();

        for (int i = 0; i < Math.min(5, totQues); i++) {
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

    public String topScoresService(Model model) {
        List<Result> topResults =
                resultRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
        List<Topic> topicList = topicRepository.findAll();
        model.addAttribute("topResults", topResults);
        model.addAttribute("topicList", topicList);
        return "topScores";
    }

    public String loginService(Principal principal, Model model, RedirectAttributes ra,
            int topicId) {

        System.out.println("*****loginService " + principal);
        if (principal == null) {
            ra.addFlashAttribute("warning", "Fields can't be empty!");
            return "home";
        } else if (!userRepository.existsByUserId(principal.getName())) {
            ra.addFlashAttribute("warning", "Invalid User Id or Password!");
            return "home";
        } else {
            User userObj = userRepository.findByUserId(principal.getName());
            return startQuizService(userObj, model, ra, topicId);
        }
    }

    public String startQuizService(User userObj, Model model, RedirectAttributes ra, int topicId) {
        questionForm = getQuestions(topicId);
        model.addAttribute("questionForm", questionForm);
        model.addAttribute("userObj", userObj);
        model.addAttribute("topicName", topicRepository.findByTopicId(topicId).getTopicName());
        model.addAttribute("topicId", topicId);

        System.out.println("*****startQuizService: " + userObj);
        return "quiz";
    }

    public String submitQuizService(QuestionForm questionForm, String userId, Model model,
            int topicId) {

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
        newResult.setTopicId(topicId);
        newResult.setTopicName(topicRepository.findByTopicId(topicId).getTopicName());
        newResult.setTotalCorrect(userObj.getTotalCorrect());

        userRepository.save(userObj);
        resultRepository.save(newResult);

        // model.addAttribute("userObj", userObj);
        return "redirect:/myResult";
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
        List<Topic> topicList = new ArrayList<>();
        List<Topic> allTopics = topicRepository.findAll();

        for (Topic cur : allTopics) {
            if (!resultRepository.existsByUserIdAndTopicId(principal.getName(), cur.getTopicId())) {
                topicList.add(cur);
            }
        }
        model.addAttribute("topicList", topicList);
        return "dashboard";
    }
}
