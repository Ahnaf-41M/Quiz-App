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

    /* Dashboard */
    @GetMapping("/dashboard")
    public String admin(Model model, Principal principal) {
        adminService.dashboardService(model, principal);
        return "adminDashboard";
    }

    /* Quiz Part */
    @GetMapping("/createQuiz")
    public String createQuiz(Model model) {
        adminService.createQuizService(model);
        return "adminCreateQuiz";
    }

    @GetMapping("/editQuiz")
    public String editQuiz(@RequestParam int quizId, Model model) {
        adminService.editQuizService(quizId, model);
        return "adminEditQuiz";
    }

    @PostMapping("/saveQuiz")
    public String saveQuiz(@ModelAttribute Test test, RedirectAttributes redirectAttributes) {
        if (testRepository.existsByTestName(test.getTestName())) {
            redirectAttributes.addFlashAttribute("msg", "Quiz name exists!");
            return "redirect:/admin/createQuiz";
        } else {
            adminService.saveQuizService(test, redirectAttributes);
            return "redirect:/admin/dashboard";
        }
    }

    @GetMapping("/deleteQuiz")
    public String deleteQuiz(@RequestParam int quizId, RedirectAttributes redirectAttributes) {
        adminService.deleteQuizService(quizId, redirectAttributes);
        return "redirect:/admin/dashboard";
    }

    /* Question Part */
    @GetMapping("/allQuestions")
    public String allQuestions(Model model) {
        adminService.allQuestionsService(model);
        return "adminAllQuestions";
    }

    @GetMapping("/addQuestion")
    public String addQuestion(Model model) {
        adminService.addQuestionService(model);
        return "adminQuestion";
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

    /* Quiz Question Part */
    @GetMapping("/quizQuestions")
    public String quizQuestions(@RequestParam int quizId, Model model, HttpSession session) {
        adminService.adminQuizQuestionsService(quizId, model, session, "");
        return "adminQuizQuestions";
    }

    @GetMapping("/addQuestionToQuiz")
    public String addQuestionToQuiz(@RequestParam int quizId, @RequestParam int quesId,
            RedirectAttributes redirectAttributes) {
        adminService.addQuestionToQuizService(quizId, quesId, redirectAttributes);
        return "redirect:/admin/allQuestions";
    }

    @GetMapping("/addQuestionToQuizFromQuiz")
    public String addQuestionToQuizFromQuiz(@RequestParam int quizId, @RequestParam int quesId,
            RedirectAttributes redirectAttributes) {
        adminService.addQuestionToQuizService(quizId, quesId, redirectAttributes);
        return "redirect:/admin/quizQuestions?quizId=" + quizId;
    }

    @GetMapping("/deleteQuizQuestion")
    public String deleteQuizQuestion(@RequestParam int quizId, @RequestParam int quesId,
            HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        adminService.deleteQuestionService(quesId, redirectAttributes);
        redirectAttributes.addAttribute("quizId", quizId);
        return "redirect:/admin/quizQuestions?quizId=" + quizId;
    }

    @GetMapping("/removeQuizQuestion")
    public String removeQuizQuestion(@RequestParam int quizId, @RequestParam int quesId,
            RedirectAttributes redirectAttributes) {
        adminService.removeQuizQuestionService(quizId, quesId, redirectAttributes);
        return "redirect:/admin/editQuiz?quizId=" + quizId;
    }
}
