package com.santanu.Test.generate.service;


import com.santanu.Test.generate.dto.QuestionDTO;
import com.santanu.Test.generate.dto.enumaration.QuestionType;
import com.santanu.Test.generate.model.Question;

import java.util.List;
import java.util.Map;

public interface QuestionBankService {

    QuestionDTO createQuestion(QuestionDTO user);

    List<Question> getAllQuestion();

    Map<String, List<Question>> getAllQuestionByQuestionType();
}
