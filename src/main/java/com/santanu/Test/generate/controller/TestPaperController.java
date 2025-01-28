package com.santanu.Test.generate.controller;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/test-papers")
@Tag(name = "Test Paper Management")
public class TestPaperController {

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

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        document.add(new Table(3).getHeader());
        document.add(new Paragraph("This sample test paper"));
        document.add(new Paragraph("You can include more content such as tables, images, etc."));

        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("generated-file.pdf").build());
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);


    }
}