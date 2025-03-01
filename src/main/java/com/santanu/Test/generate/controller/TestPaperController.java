package com.santanu.Test.generate.controller;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.*;
import com.santanu.Test.generate.dto.PaperDTO;
import com.santanu.Test.generate.dto.PaperQuestionsDTO;
import com.santanu.Test.generate.model.Distribution;
import com.santanu.Test.generate.model.Paper;
import com.santanu.Test.generate.model.Question;
import com.santanu.Test.generate.service.QuestionBankService;
import com.santanu.Test.generate.service.TestPaperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.santanu.Test.generate.utils.TestPaperUtils.getOption;

@RestController
@RequestMapping("/api/test-papers")
@Tag(name = "Test Paper Management")
@Validated
public class TestPaperController {

    private final QuestionBankService questionBankService;

    private final TestPaperService testPaperService;

    @Autowired
    public TestPaperController(QuestionBankService questionBankService, TestPaperService testPaperService) {
        this.questionBankService = questionBankService;
        this.testPaperService = testPaperService;
    }

    @Operation(summary = "Generate a Test Paper", description = "Creates a new test paper based on provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test paper generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid test paper data")
    })
    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Paper> generateTestPaper(@Valid @RequestBody PaperDTO request) {
        Paper response = testPaperService.createTestPaper(request);
        return ResponseEntity.ok()
                .header("Custom-Header", "Value")
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paper> getTestPaper(@PathVariable Long id) {
        Optional<Paper> response = testPaperService.getTestPaper(id);

        return response.map(paper -> ResponseEntity.ok()
                .header("Custom-Header", "Value")
                .body(paper)).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{paperId}/questions")
    public ResponseEntity<PaperQuestionsDTO> getQuestionsForPaper(@PathVariable Long paperId) {
        PaperQuestionsDTO response = testPaperService.fetchQuestionByTestPaper(paperId);

        return ResponseEntity.ok()
                .header("Custom-Header", "Value")
                .body(response);
    }


    @GetMapping("/view-question/{id}")
    public ResponseEntity<Paper> viewQuestion(@PathVariable Long id) {
        Optional<Paper> response = testPaperService.getTestPaper(id);

        return response.map(paper -> ResponseEntity.ok()
                .header("Custom-Header", "Value")
                .body(paper)).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @GetMapping("/{id}/export")
    public ResponseEntity<byte[]> exportTestPaper(@PathVariable Long id){
        Optional<Paper> response = testPaperService.getTestPaper(id);

        Map<String, List<Question>> questionByQuestionType = questionBankService.getAllQuestionByQuestionType();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        Paper paper = new Paper();
        if (response.isPresent()){
             paper = response.get();
        }
        // Add exam details
        document.add(new Paragraph(paper.getName()).setTextAlignment(TextAlignment.CENTER).setFontSize(15).setBold());
        document.add(new Paragraph(paper.getName()).setTextAlignment(TextAlignment.CENTER).setFontSize(15).setBold());

        Div leftDiv = new Div();
        leftDiv.setWidth(UnitValue.createPercentValue(50)); 
        leftDiv.add(new Paragraph("Course Name: ").setBold().add(paper.getCourseName()));
        leftDiv.setHorizontalAlignment(HorizontalAlignment.LEFT);
        leftDiv.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);


        Div rightDiv = new Div();
        rightDiv.setWidth(UnitValue.createPercentValue(50)); 
        rightDiv.add(new Paragraph("Course Code: ").setBold().add(paper.getCourseCode()));
        rightDiv.setHorizontalAlignment(HorizontalAlignment.LEFT);
        rightDiv.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);


        document.add(leftDiv);
        document.add(rightDiv);

        Div marksDiv = new Div();
        marksDiv.setWidth(UnitValue.createPercentValue(30)); 
        marksDiv.add(new Paragraph("Max. Marks: ").setBold().add(String.valueOf(paper.getTotalMarks())));
        marksDiv.setHorizontalAlignment(HorizontalAlignment.LEFT);
        marksDiv.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);


        Div creditDiv = new Div();
        creditDiv.setWidth(UnitValue.createPercentValue(30));
        creditDiv.add(new Paragraph("Credit: ").setBold().add(String.valueOf(paper.getCredit())));
        creditDiv.setHorizontalAlignment(HorizontalAlignment.CENTER);
        creditDiv.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);

        Div timeDiv = new Div();
        timeDiv.setWidth(UnitValue.createPercentValue(40));
        timeDiv.add(new Paragraph("Time: ").setBold().add(paper.getTotalTime()+" hours"));
        timeDiv.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        timeDiv.setProperty(Property.FLOAT, FloatPropertyValue.RIGHT);


        document.add(marksDiv);
        document.add(creditDiv);
        document.add(timeDiv);
        for (Distribution distribution: paper.getDistributions()){
            switch (distribution.getType()) {
                case "MCQ" -> {
                    document.add(new Paragraph("Section - A").setTextAlignment(TextAlignment.LEFT).setFontSize(12).setBold());
                    document.add(new Paragraph("1. This is MCQ questions. (" + distribution.getCount() + " X " + distribution.getMarksPerQuestion() + " =" + distribution.getCount() * distribution.getMarksPerQuestion() + ")"));
                    printQuestions(questionByQuestionType.get(distribution.getType()), document);
                }
                case "TRUE_FALSE" -> {
                    document.add(new Paragraph("Section - B").setTextAlignment(TextAlignment.LEFT).setFontSize(12).setBold());
                    document.add(new Paragraph("2. This is Ture/False questions. (" + distribution.getCount() + " X " + distribution.getMarksPerQuestion() + " =" + distribution.getCount() * distribution.getMarksPerQuestion() + ")"));
                    printQuestions(questionByQuestionType.get(distribution.getType()), document);
                }
                case "SHORT" -> {
                    document.add(new Paragraph("Section - C").setTextAlignment(TextAlignment.LEFT).setFontSize(12).setBold());
                    document.add(new Paragraph("3. This is Short questions. (" + distribution.getCount() + " X " + distribution.getMarksPerQuestion() + " =" + distribution.getCount() * distribution.getMarksPerQuestion() + ")"));
                    printQuestions(questionByQuestionType.get(distribution.getType()), document);
                }
                case "LONG" -> {
                    document.add(new Paragraph("Section - D").setTextAlignment(TextAlignment.LEFT).setFontSize(12).setBold());
                    document.add(new Paragraph("4. This is Long questions. (" + distribution.getCount() + " X " + distribution.getMarksPerQuestion() + " =" + distribution.getCount() * distribution.getMarksPerQuestion() + ")"));
                    printQuestions(questionByQuestionType.get(distribution.getType()), document);
                }
                default -> throw new IllegalStateException("Unexpected value: " + distribution.getType());
            }

        }


        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("0624-End-Term-Exam.pdf").build());
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);


    }

    private static void printQuestions(List<Question> allQuestion, Document document) {

        AtomicInteger i = new AtomicInteger(1);
        allQuestion.forEach(questionDTO -> {
            Paragraph paragraphQuestion = new Paragraph();
            paragraphQuestion.add(i.getAndIncrement()+". "+questionDTO.getText());
            paragraphQuestion.setFirstLineIndent(20f);
            document.add(paragraphQuestion);

            AtomicInteger j = new AtomicInteger(0);
            Arrays.stream(questionDTO.getOptions().split(",")).forEach(option -> {
                Paragraph optionParagraph = new Paragraph();
                optionParagraph.add(getOption(j.getAndIncrement()) + ". " + option);
                optionParagraph.setFirstLineIndent(30f);
                document.add(optionParagraph);
            });
        });
    }
}