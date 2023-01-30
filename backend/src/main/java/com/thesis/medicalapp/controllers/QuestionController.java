package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Answer;
import com.thesis.medicalapp.models.Favorite;
import com.thesis.medicalapp.models.Question;
import com.thesis.medicalapp.models.User;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.repository.AnswerRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.services.QuestionService;
import com.thesis.medicalapp.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerRepository answerRepository;
    private final UserService userService;

    @Data
    public static class QuestionRequest {
        @NotNull
        private String question;
        private String name;
        @NotNull
        private String username;
        @NotNull
        private String objectId;
    }

    @Data
    public static class AnswerRequest {
        @NotNull
        private String answer;
        private String username;
        @NotNull
        private String questionId;
    }

    @PostMapping("")
    public ResponseEntity<Object> saveFavorite(@RequestBody @Valid QuestionRequest questionRequest) {
        Question question = new Question();
        String username = questionRequest.getUsername();
        if (!userService.existsByUsername(username))
            throw new ApiRequestException("Không tìm thấy user!");
        User user = userService.findByUsername(username);
        question.setUser(user);
        question.setName(questionRequest.getName());
        question.setDate(new Date());
        question.setQuestion(questionRequest.getQuestion());
        question.setObjectId(questionRequest.getObjectId());
        question.setAnswers(new ArrayList<>());
        Question questionRes = questionService.save(question);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(questionRes)
        );
    }

    @PostMapping("/reply")
    public ResponseEntity<Object> setAnswer(@RequestBody AnswerRequest answerRequest) {
        System.out.println("reply");
        Answer answer = new Answer();
        String username = answerRequest.getUsername();
        if ( !userService.existsByUsername(username))
            throw new ApiRequestException("Không tìm thấy user!");
        User user = userService.findByUsername(username);
        answer.setUser(user);
        answer.setAnswer(answerRequest.getAnswer());
        answer.setDate(new Date());
        Optional<Question> questionOp = questionService.findById(answerRequest.getQuestionId());
        if (!questionOp.isPresent())
            throw new ApiRequestException("Không tìm thấy câu hỏi!");
        Question question = questionOp.get();
        Answer answerRes = answerRepository.save(answer);
        question.getAnswers().add(answerRes);
        questionService.save(question);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(answerRes)
        );
    }

    @GetMapping("")
    public ResponseEntity<Object> getQuestions(
            @RequestParam String objectId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        SearchRequest request = new SearchRequest(objectId, page, size);
        Page<Question> pageResponse = questionService.getAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(pageResponse.getContent(), pageResponse)
        );
    }
}
