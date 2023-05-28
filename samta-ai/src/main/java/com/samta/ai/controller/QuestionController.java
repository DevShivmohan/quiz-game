package com.samta.ai.controller;

import com.samta.ai.global.CustomException;
import com.samta.ai.service.QuestionService;
import io.github.bucket4j.Bucket;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final Bucket bucket;

    /**
     * It will consume 1 token in every request
     * @return
     * @throws CustomException
     */
    @GetMapping("/play")
    public ResponseEntity<?> getQuestion() throws CustomException {
        System.out.println("Available tokens-"+bucket.getAvailableTokens());
        if(!bucket.tryConsume(1))
            throw new CustomException(HttpStatus.TOO_MANY_REQUESTS.value(), "API request limit exceeded");
        return questionService.getQuestion();
    }

    @PostMapping("/next")
    public ResponseEntity<?> getNextQuestion(@RequestBody final Map<String, Optional<Object>> request) throws CustomException {
        if(!bucket.tryConsume(1))
            throw new CustomException(HttpStatus.TOO_MANY_REQUESTS.value(), "API request limit exceeded");
        return questionService.getNextQuestion(request);
    }
}
