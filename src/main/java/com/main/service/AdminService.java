package com.main.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.main.model.Question;
import com.main.model.QuestionForm;
import com.main.model.Quiz;
import com.main.model.Result;
import com.main.model.Test;
import com.main.repository.QuestionRepository;
import com.main.repository.QuizRepository;
import com.main.repository.ResultRepository;
import com.main.repository.TestRepository;
import com.main.repository.UserRepository;

@Service
public class AdminService {
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

    public void dashboardService(Model model, Principal principal) {
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
        model.addAttribute("testList", allTest);
    }

    public void createQuizService(Model model) {
        model.addAttribute("test", new Test());
    }

    public void editQuizService(int quizId, Model model) {
        System.out.println("********Editquiz: " + quizId);
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
    }

    public void saveQuizService(Test test, RedirectAttributes redirectAttributes) {
        testRepository.save(test);
        redirectAttributes.addFlashAttribute("msg", "Quiz added successfully!");
    }


    public void deleteQuizService(int quizId, RedirectAttributes redirectAttributes) {
        List<Quiz> quizList = quizRepository.findByQuizId(quizId);
        for (Quiz quiz : quizList) {
            quizRepository.delete(quiz);
        }
        testRepository.delete(testRepository.findByTestId(quizId));
        redirectAttributes.addFlashAttribute("msg", "Quiz deleted successfully!");
    }

    public void adminQuizQuestionsService(int quizId, Model model, HttpSession session,
            String msg) {
        List<Question> questionList = questionRepository.findByTestId(quizId);
        List<Test> testList = testRepository.findAll();
        List<Quiz> quizList = quizRepository.findAll();
        Set<Integer> quesIdList = new HashSet<>();
        for (Quiz quiz : quizList) {
            quesIdList.add(quiz.getQuesId());
        }
        model.addAttribute("questionList", questionList);
        model.addAttribute("testList", testList);
        model.addAttribute("quesIdList", quesIdList);
        model.addAttribute("quizName", testRepository.findByTestId(quizId).getTestName());
        // session.setAttribute("msg", msg);

    }

    public void addQuestionService(Model model) {
        List<Test> testList = testRepository.findAll();
        model.addAttribute("testList", testList);
        model.addAttribute("question", new Question());
        model.addAttribute("questionPageTitle", "Add Question");
    }

    public void editQuestionService(int quesId, Model model) {
        Question question = questionRepository.findById(quesId).get();
        List<Test> testList = testRepository.findAll();
        model.addAttribute("testList", testList);
        model.addAttribute("question", question);
        model.addAttribute("questionPageTitle", "Edit Question");
        // System.out.println("edit: " + question);
    }

    public void saveQuestionService(Question question, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "Question Saved!");
        questionRepository.save(question);
    }

    public void deleteQuestionService(int quesId, RedirectAttributes redirectAttributes) {
        questionRepository.delete(questionRepository.findByQuesId(quesId));
        if (quizRepository.existsByQuesId(quesId)) {
            quizRepository.delete(quizRepository.findByQuesId(quesId));
        }
        redirectAttributes.addFlashAttribute("msg", "Question Deleted!");
    }

    public void allQuestionsService(Model model) {
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
    }

    public void addQuestionToQuizService(int quizId, int quesId,
            RedirectAttributes redirectAttributes) {
        // System.out.println("addtoQuiz: " + quizId + " " + quesId);
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);
        quiz.setQuesId(quesId);
        quizRepository.save(quiz);
        String msg = "Question added to quiz!";
        redirectAttributes.addFlashAttribute("msg", msg);
    }

    public void removeQuizQuesService(int quizId, int quesId,
            RedirectAttributes redirectAttributes) {
        if (quizRepository.existsByQuizIdAndQuesId(quizId, quesId)) {
            quizRepository.delete(quizRepository.findByQuizIdAndQuesId(quizId, quesId));
        }
        redirectAttributes.addFlashAttribute("msg", "Question removed!");
        redirectAttributes.addAttribute("quizId", quizId);
    }
}
