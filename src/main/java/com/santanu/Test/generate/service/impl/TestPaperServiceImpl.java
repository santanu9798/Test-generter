package com.santanu.Test.generate.service.impl;

import com.santanu.Test.generate.dto.PaperDTO;
import com.santanu.Test.generate.dto.PaperQuestionsDTO;
import com.santanu.Test.generate.dto.QuestionDTO;
import com.santanu.Test.generate.dto.QuestionGroupDTO;
import com.santanu.Test.generate.dto.enumaration.Difficulty;
import com.santanu.Test.generate.dto.enumaration.QuestionType;
import com.santanu.Test.generate.mapper.TestPaperMapper;
import com.santanu.Test.generate.model.Distribution;
import com.santanu.Test.generate.model.Paper;
import com.santanu.Test.generate.repository.QuestionBankRepository;
import com.santanu.Test.generate.repository.TestPaperRepository;
import com.santanu.Test.generate.service.TestPaperService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestPaperServiceImpl implements TestPaperService {

    private final TestPaperRepository testPaperRepository;

    private final QuestionBankRepository questionBankRepository;

    private final TestPaperMapper testPaperMapper;

    public TestPaperServiceImpl(TestPaperRepository testPaperRepository, QuestionBankRepository questionBankRepository, TestPaperMapper testPaperMapper) {
        this.testPaperRepository = testPaperRepository;
        this.questionBankRepository = questionBankRepository;
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

    @Override
    public PaperQuestionsDTO fetchQuestionByTestPaper(Long paperId) {
        //get questions by types and no of questions based on define for each types and return by group types table question and testpaper

        Paper paper = testPaperRepository.findById(paperId)
                .orElseThrow(() -> new RuntimeException("Paper not found"));

        List<QuestionGroupDTO> groupedQuestions = new ArrayList<>();

        for (Distribution dist : paper.getDistributions()) {
            List<Object[]> questionResults = questionBankRepository.findRandomQuestionsByType(dist.getType(), dist.getCount());

            List<QuestionDTO> questions = questionResults.stream().map(row ->
                    QuestionDTO.builder()
                           // .id((Long) row[0])
                            .text((String) row[1])
                            .type(QuestionType.valueOf((String) row[2]))
                            .topic((String) row[3])
                            .difficulty(Difficulty.valueOf(row[4].toString()))
                            .options(Collections.singletonList((String) row[5]))
                            .correctAnswer((String) row[6])
                            .marks((Integer) row[7])
                            .build()
            ).collect(Collectors.toList());

            groupedQuestions.add(new QuestionGroupDTO(dist.getType(), questions));
        }

        return PaperQuestionsDTO.builder()
                .paperId(paper.getId())
                .paperName(paper.getName())
                .groupedQuestions(groupedQuestions)
                .build();
    }
}
