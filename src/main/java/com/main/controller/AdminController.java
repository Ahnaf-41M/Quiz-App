package com.main.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.main.model.QuestionForm;
import com.main.model.Quiz;
import com.main.model.Test;
import com.main.repository.QuestionRepository;
import com.main.repository.QuizRepository;
import com.main.repository.TestRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuizRepository quizRepository;

    @GetMapping("/dashboard")
    public String admin(Model model, Principal principal) {
        List<Quiz> quizList = new ArrayList<>();
        List<Test> allTest = testRepository.findAll();
        List<Test> testList = new ArrayList<>();

        for (Test test : allTest) {
            if (quizRepository.existsByQuizId(test.getTestId())) {
                testList.add(test);
                for (Quiz quiz : quizRepository.findByQuizId(test.getTestId())) {
                    quizList.add(quiz);
                }
            }
        }

        model.addAttribute("quizList", quizList);
        model.addAttribute("testList", testList);
        return "adminDashboard";
    }

    @GetMapping("/editQuiz")
    public String editQuiz(@RequestParam int quizId, Model model) {
        List<Quiz> quizList = quizRepository.findByQuizId(quizId);
        List<Question> questionList = new ArrayList<>();
        QuestionForm questionForm = new QuestionForm();
        for (Quiz quiz : quizList) {
            questionList.add(questionRepository.findByQuesId(quiz.getQuesId()));
        }
        questionForm.setQuestions(questionList);
        model.addAttribute("quizName", testRepository.findByTestId(quizId).getTestName());
        model.addAttribute("quizId", quizId);
        model.addAttribute("questionList", questionList);
        model.addAttribute("questionForm", questionForm);
        return "adminEditQuiz";
    }

    @GetMapping("/deleteQuiz")
    public String deleteQuiz(@RequestParam int quizId, RedirectAttributes redirectAttributes) {
        List<Quiz> quizList = quizRepository.findByQuizId(quizId);
        for (Quiz quiz : quizList) {
            System.out.println("delete " + quiz);
            quizRepository.delete(quiz);
        }
        redirectAttributes.addFlashAttribute("msg", "Quiz Deleted");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/allQuestions")
    public String allQuestions(Model model) {
        List<Question> questionList = questionRepository.findAll();
        List<Test> testList = testRepository.findAll();
        List<Quiz> quizList = quizRepository.findAll();
        Set<Integer> quesIdList = new HashSet<>();
        for (Quiz quiz : quizList) {
            quesIdList.add(quiz.getQuesId());
        }
        model.addAttribute("questionList", questionList);
        model.addAttribute("testList", testList);
        model.addAttribute("quesIdList", quesIdList);
        return "adminAllQuestions";
    }

    @GetMapping("/addToQuiz")
    public String addToQuiz(@RequestParam int quizId, @RequestParam int quesId,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "Question Added to quiz!");
        System.out.println("addtoQuiz: " + quizId + " " + quesId);
        if (quizRepository.existsByQuizIdAndQuesId(quizId, quesId)) {
            System.out.println("***TRUE");
        }
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);
        quiz.setQuesId(quesId);
        quizRepository.save(quiz);

        return "redirect:/admin/allQuestions";
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
    public String editQuestion(@RequestParam int quesId, Model model) {
        Question question = questionRepository.findById(quesId).get();
        List<Test> testList = testRepository.findAll();
        model.addAttribute("testList", testList);
        model.addAttribute("question", question);
        model.addAttribute("questionPageTitle", "Edit Question");
        // System.out.println("edit: " + question);
        return "adminQuestion";
    }

    @PostMapping("/saveQuestion")
    public String saveQuestion(@ModelAttribute Question question,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "Question Saved!");
        questionRepository.save(question);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam int quesId, RedirectAttributes redirectAttributes) {
        questionRepository.delete(questionRepository.findByQuesId(quesId));
        redirectAttributes.addFlashAttribute("msg", "Question Deleted!");
        return "redirect:/admin/allQuestions";
    }

    @GetMapping("/deleteQuizQuestion")
    public String deleteQuizQuestion(@RequestParam int quizId, @RequestParam int quesId,
            HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        // System.out.println(quizId + " " + quesId);
        Quiz quiz = quizRepository.findByQuizIdAndQuesId(quizId, quesId);
        quizRepository.delete(quiz);

        System.out.println("delete quiz ques: " + quiz);
        redirectAttributes.addFlashAttribute("msg", "Question Deleted!");
        redirectAttributes.addAttribute("quizId", quizId);
        return "redirect:/admin/editQuiz";
    }
}
