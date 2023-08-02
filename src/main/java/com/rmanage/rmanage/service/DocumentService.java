package com.rmanage.rmanage.service;

import com.rmanage.rmanage.config.s3Setting.S3Uploader;
import com.rmanage.rmanage.dto.ResponseDocument;
import com.rmanage.rmanage.entity.Document;
import com.rmanage.rmanage.entity.Worker;
import com.rmanage.rmanage.repository.DocumentRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private DocumentRepository documentRepository;
    private EntityManager entityManager;
    private S3Uploader s3Uploader;

    @Autowired
    public DocumentService(DocumentRepository theDocumentRepository, EntityManager entityManager, S3Uploader s3Uploader){
        this.documentRepository = theDocumentRepository;
        this.entityManager = entityManager;
        this.s3Uploader = s3Uploader;
    }

    public List<ResponseDocument> getDocuments(int workEmployeesId) {
        try {
            Document document = null;
            // 근무 근로자 찾기
            Worker worker = entityManager.find(Worker.class, workEmployeesId);
            if(worker == null){
                throw new IllegalArgumentException("해당하는 근무지, 근로자 정보가 없음");
            }
            // 근무 근로자 아이디로 문서 찾기
            List<Document> documents = documentRepository.findDocumentByWorker(worker);
            // 조회 성공
            List<ResponseDocument> responseDocuments = new ArrayList<>();
            for(Document d : documents){
                responseDocuments.add(new ResponseDocument(d.getDocumentId(),d.getType(),d.getImageUrl(),d.getExpireDate()));
            }
            return responseDocuments;
        }   catch (Exception e){
            System.out.println(e);
            throw new IllegalArgumentException("서류 조회 실패");
        }
    }

    public void postDocument(int workEmployeesId, String type, LocalDate expireDate, MultipartFile image) {
                try {
                    // 근무 근로자 조회
                    Worker worker = entityManager.find(Worker.class, workEmployeesId);
                    if(worker == null){
                        throw new IllegalArgumentException("해당하는 근무지, 근로자 정보가 없음");
                    }
                    // 이미지 업로드
                    String filename = null;
                    filename = s3Uploader.uploadFiles(image, "image");
                    if(filename == null){
                        throw new IllegalArgumentException("이미지 업로드에 실패함.");
                    }
                    // 서류 등록 성공
                    Document document = new Document(worker.getUser(), worker.getWorkPlace(), type, expireDate, worker, filename);
                    Document theDocument = documentRepository.save(document);
                }   catch (Exception e) {
                    System.out.println(e);
                    throw new IllegalArgumentException("서류 등록 실패");
                }
    }

    public void deleteDocument(Long documentId) {
        try {
            // 근무 근로자 조회
            Optional<Document> document = documentRepository.findById(documentId);
            // 이미지 삭제
            if(document == null){
                throw new IllegalArgumentException("no document");
            }
            Document document1 = document.get();
            System.out.println(document1);
            s3Uploader.fileDelete(document1.getImageUrl());
            // 서류 삭제 성공
            documentRepository.delete(document1);
        }   catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("failed");
        }
    }


}

