package com.samta.ai.controller;

import com.samta.ai.global.CustomException;
import com.samta.ai.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/play")
    public ResponseEntity<?> getQuestion(){
        return questionService.getQuestion();
    }

    @PostMapping("/next")
    public ResponseEntity<?> getNextQuestion(@RequestBody final Map<String, Optional<Object>> request) throws CustomException {
        return questionService.getNextQuestion(request);
    }
}
