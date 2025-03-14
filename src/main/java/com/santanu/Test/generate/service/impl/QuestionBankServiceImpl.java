package com.santanu.Test.generate.service.impl;

import com.santanu.Test.generate.dto.QuestionDTO;
import com.santanu.Test.generate.dto.enumaration.QuestionType;
import com.santanu.Test.generate.mapper.QuestionBankMapper;
import com.santanu.Test.generate.model.Question;
import com.santanu.Test.generate.repository.QuestionBankRepository;
import com.santanu.Test.generate.service.QuestionBankService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionBankServiceImpl implements QuestionBankService {

    private final QuestionBankRepository questionBankRepository;


    private final QuestionBankMapper questionBankMapper;


    @Autowired
    public QuestionBankServiceImpl(QuestionBankRepository questionBankRepository, QuestionBankMapper questionBankMapper) {
        this.questionBankRepository = questionBankRepository;
        this.questionBankMapper = questionBankMapper;
    }


    @Override
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        Question request = questionBankMapper.toEntity(questionDTO);
        Question response = questionBankRepository.save(request);

        return questionBankMapper.toDTO(response);
    }

    @Override
    public List<QuestionDTO> getAllQuestion() {

        return questionBankRepository.findAll().stream().map(questionBankMapper::toDTO).toList();
    }

    @Override
    public Map<String, List<Question>> getAllQuestionByQuestionType() {

        return questionBankRepository.findAll().stream()
                .collect(Collectors.groupingBy(q -> q.getType().name()));

    }

    @Override
    public QuestionDTO updateQuestion(Long id, QuestionDTO updatedQuestion) {
        return questionBankRepository.findById(id)
                .map(question -> {
                    question.setQuestion(updatedQuestion.getQuestion());
                    question.setType(updatedQuestion.getType());
                    question.setTopic(updatedQuestion.getTopic());
                    question.setDifficulty(updatedQuestion.getDifficulty());
                    question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
                    question.setMarks(updatedQuestion.getMarks());
                    return questionBankRepository.save(question);
                })
                .map(questionBankMapper::toDTO) // Convert to DTO
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + id));
    }

    @Override
    public boolean deleteQuestion(Long id) {
        if(questionBankRepository.existsById(id)){
            questionBankRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
