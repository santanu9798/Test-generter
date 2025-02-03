package com.santanu.Test.generate.service.impl;

import com.santanu.Test.generate.dto.PaperDTO;
import com.santanu.Test.generate.mapper.TestPaperMapper;
import com.santanu.Test.generate.model.Distribution;
import com.santanu.Test.generate.model.Paper;
import com.santanu.Test.generate.repository.TestPaperRepository;
import com.santanu.Test.generate.service.TestPaperService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestPaperServiceImpl implements TestPaperService {

    private final TestPaperRepository testPaperRepository;

    private final TestPaperMapper testPaperMapper;

    public TestPaperServiceImpl(TestPaperRepository testPaperRepository, TestPaperMapper testPaperMapper) {
        this.testPaperRepository = testPaperRepository;
        this.testPaperMapper = testPaperMapper;
    }


    @Override
    public Paper createTestPaper(PaperDTO request) {

        Paper paper = testPaperMapper.toEntity(request);

        if (paper.getDistributions() != null) {
            for (Distribution distribution : paper.getDistributions()) {
                distribution.setPaper(paper);  // Ensure that the paper reference is set
            }
        }

        return testPaperRepository.save(paper);
    }

    @Override
    public Optional<Paper> getTestPaper(Long id) {
        return testPaperRepository.findById(id);
    }
}
