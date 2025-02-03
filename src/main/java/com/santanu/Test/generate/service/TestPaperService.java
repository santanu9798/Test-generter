package com.santanu.Test.generate.service;

import com.santanu.Test.generate.dto.PaperDTO;
import com.santanu.Test.generate.model.Paper;

import java.util.Optional;

public interface TestPaperService {

    Paper createTestPaper(PaperDTO request);

    Optional<Paper> getTestPaper(Long id);
}
