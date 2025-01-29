package com.santanu.Test.generate.service;


import com.santanu.Test.generate.dto.QuestionDTO;
import com.santanu.Test.generate.model.Question;

import java.util.List;

public interface QuestionBankService {

    QuestionDTO createQuestion(QuestionDTO user);

    List<Question> getAllQuestion();
}
