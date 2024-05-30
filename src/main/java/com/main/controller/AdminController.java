package com.main.controller;

import java.security.Principal;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.main.model.Question;
import com.main.model.Test;
import com.main.repository.QuestionRepository;
import com.main.repository.QuizRepository;
import com.main.repository.TestRepository;
import com.main.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    AdminService adminService;

    @GetMapping("/dashboard")
    public String admin(Model model, Principal principal) {
        return adminService.dashboardService(model, principal);
    }

    @GetMapping("/editQuiz")
    public String editQuiz(@RequestParam int quizId, Model model) {
        return adminService.editQuizService(quizId, model);
    }

    @GetMapping("/deleteQuiz")
    public String deleteQuiz(@RequestParam int quizId, RedirectAttributes redirectAttributes) {
        return adminService.deleteQuizService(quizId, redirectAttributes);
    }

    @GetMapping("/createQuiz")
    public String createQuiz(Model model) {
        return adminService.createQuizService(model);
    }

    @PostMapping("/saveQuiz")
    public String saveQuiz(@ModelAttribute Test test, RedirectAttributes redirectAttributes) {
        return adminService.saveQuizService(test, redirectAttributes);
    }

    @GetMapping("/addQuestion")
    public String addQuestion(Model model) {
        return adminService.addQuestionService(model);
    }

    @GetMapping("/quizQuestions")
    public String quizQuestions(@RequestParam int quizId, Model model, HttpSession session) {
        return adminService.adminQuizQuestionsService(quizId, model, session, "");
    }

    @GetMapping("/addQuestionToQuiz")
    public String addQuestionToQuiz(@RequestParam int quizId, @RequestParam int quesId,
            RedirectAttributes redirectAttributes) {
        return adminService.addQuestionToQuizService(quizId, quesId, redirectAttributes);
    }

    @GetMapping("/allQuestions")
    public String allQuestions(Model model) {
        return adminService.allQuestionsService(model);
    }

    @GetMapping("/editQuestion")
    public String editQuestion(@RequestParam int quesId, Model model) {
        adminService.editQuestionService(quesId, model);
        return "adminQuestion";
    }

    @PostMapping("/saveQuestion")
    public String saveQuestion(@ModelAttribute Question question,
            RedirectAttributes redirectAttributes) {
        adminService.saveQuestionService(question, redirectAttributes);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam int quesId, RedirectAttributes redirectAttributes) {
        adminService.deleteQuestionService(quesId, redirectAttributes);
        return "redirect:/admin/allQuestions";
    }

    @GetMapping("/deleteQuizQuestion")
    public String deleteQuizQuestion(@RequestParam int quizId, @RequestParam int quesId,
            HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        adminService.deleteQuestionService(quesId, redirectAttributes);
        redirectAttributes.addAttribute("quizId", quizId);
        return "redirect:/admin/quizQuestions?quizId=" + quizId;
    }

    @GetMapping("/removeQuizQues")
    public String removeQuizQues(@RequestParam int quizId, @RequestParam int quesId,
            RedirectAttributes redirectAttributes) {
        adminService.removeQuizQuesService(quizId, quesId, redirectAttributes);
        return "redirect:/admin/editQuiz?quizId=" + quizId;
    }
}
