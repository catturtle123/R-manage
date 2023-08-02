package com.rmanage.rmanage.controller;

import com.rmanage.rmanage.dto.ResponseDocument;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.service.DocumentService;
import jakarta.persistence.EntityManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@RestController
public class Controller {

    private DocumentService documentService;

    private EntityManager entityManager;

    public Controller(DocumentService theDocumentService){
        this.documentService = theDocumentService;
    }

    @GetMapping("/every/workEmployees/{workEmployeesId}/documents")
    public ResponseEntity<List<ResponseDocument>> getDocuments(@PathVariable int workEmployeesId){
        return ResponseEntity.ok(documentService.getDocuments(workEmployeesId));
    }

    @PostMapping("/every/workEmployees/{workEmployeesId}/documents")
    public ResponseEntity postDocument(@PathVariable int workEmployeesId, @RequestParam(value = "type") String type,
                                       @RequestParam(value = "expireDate") LocalDate expireDate, @RequestParam(value = "image", required = false) MultipartFile image){
        documentService.postDocument(workEmployeesId, type, expireDate, image);
        return ResponseEntity.ok("서류 등록이 완료 되었습니다.");
    }

    @DeleteMapping("/every/documents/{documentId}")
    public ResponseEntity postDocument(@PathVariable int documentId){
        documentService.deleteDocument((long)documentId);
        return ResponseEntity.ok("서류 삭제가 완료 되었습니다.");
    }

    @PostMapping("/test")
    public void test(){
        User user = new User();
        entityManager.persist(user);
    }
}
