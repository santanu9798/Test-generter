package com.santanu.Test.generate.controller;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.*;
import com.santanu.Test.generate.dto.QuestionDTO;
import com.santanu.Test.generate.model.Question;
import com.santanu.Test.generate.service.QuestionBankService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController("/api/test-papers")
@Tag(name = "Test Paper Management")
public class TestPaperController {

    private final QuestionBankService questionBankService;

    @Autowired
    public TestPaperController(QuestionBankService questionBankService) {
        this.questionBankService = questionBankService;
    }

    @PostMapping("/generate")
    public String generateTestPaper() {
        return "generate";
    }

    @GetMapping("/{id}")
    public String getTestPaper() {
        return "get";
    }

    @GetMapping("/{id}/export")
    public ResponseEntity<byte[]> exportTestPaper(@PathVariable String id){


        List<Question> allQuestion = questionBankService.getAllQuestion();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        // Add exam details
        document.add(new Paragraph("0624").setTextAlignment(TextAlignment.CENTER).setFontSize(15).setBold());
        document.add(new Paragraph("End Term Exam").setTextAlignment(TextAlignment.CENTER).setFontSize(15).setBold());

        Div leftDiv = new Div();
        leftDiv.setWidth(UnitValue.createPercentValue(50)); 
        leftDiv.add(new Paragraph("Course Name: Theory of Computation"));
        leftDiv.setHorizontalAlignment(HorizontalAlignment.LEFT);
        leftDiv.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);


        Div rightDiv = new Div();
        rightDiv.setWidth(UnitValue.createPercentValue(50)); 
        rightDiv.add(new Paragraph("Course Code: MCA 504"));
        rightDiv.setHorizontalAlignment(HorizontalAlignment.LEFT);
        rightDiv.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);


        document.add(leftDiv);
        document.add(rightDiv);

        Div marksDiv = new Div();
        marksDiv.setWidth(UnitValue.createPercentValue(30)); 
        marksDiv.add(new Paragraph("CMax. Marks: 120"));
        marksDiv.setHorizontalAlignment(HorizontalAlignment.LEFT);
        marksDiv.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);


        Div creditDiv = new Div();
        creditDiv.setWidth(UnitValue.createPercentValue(30));
        creditDiv.add(new Paragraph("Credit: 4"));
        creditDiv.setHorizontalAlignment(HorizontalAlignment.CENTER);
        creditDiv.setProperty(Property.FLOAT, FloatPropertyValue.LEFT);

        Div timeDiv = new Div();
        timeDiv.setWidth(UnitValue.createPercentValue(40));
        timeDiv.add(new Paragraph("Time: 3 hours"));
        timeDiv.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        timeDiv.setProperty(Property.FLOAT, FloatPropertyValue.RIGHT);


        document.add(marksDiv);
        document.add(creditDiv);
        document.add(timeDiv);
        document.add(new Paragraph("Section - A").setTextAlignment(TextAlignment.LEFT).setFontSize(12).setBold());

        document.add(new Paragraph("1. Question (20 objective type questions which include MCQs (Maximum ten), true/false, fill in the blanks, match the followings, one or two line answers of one mark each). (20×1=20)"));

        allQuestion.forEach(questionDTO -> {
            document.add(new Paragraph("\n"));
            document.add(new Paragraph(questionDTO.getText()));

            Arrays.stream(questionDTO.getOptions().split(",")).forEach(option->{
                document.add(new Paragraph(option));
            });
            document.add(new Paragraph("\n"));
        });

        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("0624-End-Term-Exam.pdf").build());
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);


    }
}