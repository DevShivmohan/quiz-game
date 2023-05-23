package com.samta.ai;

import com.samta.ai.entity.Question;
import com.samta.ai.reporitory.QuestionRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@Slf4j
public class ScreeningAssignmentApplication {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private QuestionRepository questionRepository;

	public static void main(String[] args) {
		SpringApplication.run(ScreeningAssignmentApplication.class, args);
	}

	/**
	 * collecting 5 random questions
	 * and store it into our database
	 */
	@PostConstruct
	public void startInsertionOfQuestions(){
		List<Question> questions=new ArrayList<>();
		// If there is not any question in our database then perform this operation
		if(questionRepository.findAll().size()==0){
			// Collecting 5 random questions in List
			for(int i=1;i<=5;i++){
				var response= restTemplate.getForEntity("https://jservice.io/api/random", Question[].class);
				questions.add(Objects.requireNonNull(response.getBody())[0]);
				log.info("Received "+i+" random question");
			}
			// storing all questions
			questionRepository.saveAll(questions);
			log.info("Saved 5 random questions in our database");
		}
	}

}
