package com.main.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.main.model.Question;
import com.main.model.Test;
import com.main.repository.QuestionRepository;
import com.main.repository.TestRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/dashboard")
    public String admin(Model model) {
        List<Test> testList = testRepository.findAll();
        model.addAttribute("testList", testList);
        return "adminDashboard";
    }

    @GetMapping("/addQuestion")
    public String addQuestion(Model model) {
        List<Test> testList = testRepository.findAll();
        model.addAttribute("testList", testList);
        model.addAttribute("question", new Question());
        model.addAttribute("questionPageTitle", "Add Question");
        return "adminQuestion";
    }

    @GetMapping("/editQuestion")
    public String editQuestion(Model model, @RequestParam int quesId) {
        Question question = questionRepository.findById(quesId).get();
        List<Test> testList = testRepository.findAll();
        model.addAttribute("testList", testList);
        model.addAttribute("question", question);
        model.addAttribute("questionPageTitle", "Edit Question");
        // System.out.println("edit: " + question);
        return "adminQuestion";
    }

    @PostMapping("/saveQuestion")
    public String saveQuestion(@ModelAttribute Question question) {
        // System.out.println("saveQuestion: "+question);
        questionRepository.save(question);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/deleteQuiz")
    public String deleteQuiz(@RequestParam int testId, HttpSession session) {
        Test test = testRepository.findByTestId(testId);
        System.out.println(test);
        testRepository.delete(test);
        session.setAttribute("msg", "Quiz has been deleted!");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/allQuestions")
    public String allQuestions(Model model) {
        List<Question> questionList = questionRepository.findAll();
        model.addAttribute("questionList", questionList);
        return "adminAllQuestions";
    }
}
