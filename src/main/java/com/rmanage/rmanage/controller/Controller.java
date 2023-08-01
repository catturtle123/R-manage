package com.rmanage.rmanage.controller;

import com.rmanage.rmanage.dto.ResponseDocument;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.service.DocumentService;
import jakarta.persistence.EntityManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;

@RestController
public class Controller {

    private DocumentService documentService;

    private EntityManager entityManager;

    public Controller(DocumentService theDocumentService){
        this.documentService = theDocumentService;
    }

    @GetMapping("/every/workEmployees/{workEmployeesId}/documents/{documentsType}")
    public ResponseDocument getDocument(@PathVariable int workEmployeesId, @PathVariable String documentsType){
        return documentService.getDocument(workEmployeesId,documentsType);
    }

    @PostMapping("/every/workEmployees/{workEmployeesId}/documents/{documentsType}")
    public ResponseDocument postDocument(@PathVariable int workEmployeesId, @PathVariable String documentsType,
                                         @RequestParam(value = "validity") LocalDate validity, @RequestParam(value = "image", required = false) MultipartFile image){
        return documentService.postDocument(workEmployeesId,documentsType, validity, image);
    }

    @PostMapping("/test")
    public void test(){
        User user = new User();
        entityManager.persist(user);
    }
}
