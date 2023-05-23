package com.samta.ai.service.impl;

import com.samta.ai.constants.ApiConstants;
import com.samta.ai.global.CustomException;
import com.samta.ai.reporitory.QuestionRepository;
import com.samta.ai.service.QuestionService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final EntityManager entityManager;

    /**
     * Getting random question
     * @return
     */
    @Override
    public ResponseEntity<?> getQuestion() {
        final List<Object[]> resultObject= entityManager.createNativeQuery("select id, question from question order by rand() limit 1;").getResultList();
        Map<String,Object> response=new HashMap<>();
        for(Object[] objects:resultObject){
            response.put(ApiConstants.QUESTION_ID,objects[0]);
            response.put(ApiConstants.QUESTION,objects[1]);
        }
        log.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Getting correct answer of the question and next random question
     * @param requestBody
     * @return
     * @throws CustomException
     */
    @Override
    public ResponseEntity<?> getNextQuestion(final Map<String, Optional<Object>> requestBody) throws CustomException {
        if(!requestBody.containsKey(ApiConstants.QUESTION_ID))
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "question id required");
        int questionId= (int) requestBody.get(ApiConstants.QUESTION_ID).orElseThrow(()->new CustomException(HttpStatus.BAD_REQUEST.value(), "Question id cannot be null"));
        final var question= questionRepository.findById((long) questionId).orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND.value(), "Incorrect question id"));
        Map<String,Object> response=new HashMap<>();
        response.put(ApiConstants.CORRECT_ANSWER,question.getAnswer());
        response.put(ApiConstants.NEXT_QUESTION,getQuestion().getBody());
        log.info(response.toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
