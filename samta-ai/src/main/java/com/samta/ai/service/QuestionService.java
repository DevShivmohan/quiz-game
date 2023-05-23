package com.samta.ai.service;

import com.samta.ai.global.CustomException;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface QuestionService {
    ResponseEntity<?> getQuestion();
    ResponseEntity<?> getNextQuestion(final Map<String, Optional<Object>> requestBody) throws CustomException;
}
