package com.santanu.Test.generate.service.impl;

import com.santanu.Test.generate.dto.QuestionDTO;
import com.santanu.Test.generate.mapper.QuestionBankMapper;
import com.santanu.Test.generate.model.Question;
import com.santanu.Test.generate.repository.QuestionBankRepository;
import com.santanu.Test.generate.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
