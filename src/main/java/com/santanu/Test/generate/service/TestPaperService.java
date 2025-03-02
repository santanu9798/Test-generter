package com.santanu.Test.generate.service;

import com.santanu.Test.generate.dto.PaperDTO;
import com.santanu.Test.generate.dto.PaperQuestionsDTO;
import com.santanu.Test.generate.model.Paper;

import java.util.List;
import java.util.Optional;

public interface TestPaperService {

    Paper createTestPaper(PaperDTO request);

    List<PaperDTO> getAllTestPapers();

    Optional<Paper> getTestPaper(Long id);

    PaperQuestionsDTO fetchQuestionByTestPaper(Long id);
}
