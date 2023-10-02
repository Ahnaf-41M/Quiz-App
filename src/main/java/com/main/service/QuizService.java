package com.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.main.model.Question;
import com.main.model.QuestionForm;
import com.main.model.Result;
import com.main.repository.QuestionRepo;
import com.main.repository.ResultRepo;

@Service
public class QuizService {

    @Autowired
    Question ques;
    @Autowired
    QuestionForm qForm;
    @Autowired
    QuestionRepo qRepo;
    @Autowired
    Result result;
    @Autowired
    ResultRepo rRepo;

    public QuestionForm getQuestions() {
        List<Question> allQues = qRepo.findAll();
        List<Question> qList = new ArrayList<Question>();

        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            int ind = rand.nextInt(allQues.size());
            qList.add(allQues.get(ind));
            allQues.remove(ind);
        }
        qForm.setQuestions(qList);
        return qForm;
    }

    public int getResult(QuestionForm qForm) {
        int correct = 0;

        for (Question q : qForm.getQuestions()) {
            if (q.getAns() == q.getChose()) {
                correct++;
            }
        }
        return correct;
    }

    public void saveScore(Result result) {
        Result saveResult = new Result();
        saveResult.setUsername(result.getUsername());
        saveResult.setTotalCorrect(result.getTotalCorrect());
        rRepo.save(saveResult);
    }

    public List<Result> getTopScore() {
        List<Result> sList = rRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
        return sList;
    }
}
