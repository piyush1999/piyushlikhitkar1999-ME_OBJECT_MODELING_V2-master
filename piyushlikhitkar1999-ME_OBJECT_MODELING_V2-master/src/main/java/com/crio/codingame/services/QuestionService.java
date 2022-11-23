package com.crio.codingame.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.Question;
import com.crio.codingame.repositories.IQuestionRepository;

public class QuestionService implements IQuestionService{
    private final IQuestionRepository questionRepository;
    // private  final Contest contest;

    
    // public QuestionService(Contest contest) {
    //     this.contest = contest;
    // }

    public QuestionService(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Override
    public Question create(String title, Level level, Integer difficultyScore) {
     final Question question = new Question(title,level, difficultyScore);
        return questionRepository.save(question);
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Get All Questions if level is not specified.
    // Or
    // Get List of Question which matches the level provided.

    @Override
    public List<Question> getAllQuestionLevelWise(Level level) {

        List<Question> questions = new ArrayList<>();
        if(level == null) {
            questions = questionRepository.findAll();
            // System.out.println("Case of null");
            // for(Question q : questions) {
            //     System.out.println(q + " ");
            // }
            
        }
        else if(level.equals(Level.LOW) || level.equals(Level.MEDIUM) || level.equals(Level.HIGH)) {
            return questionRepository.findAllQuestionLevelWise(level);
        }
        return questions;


     
    }
    
}
