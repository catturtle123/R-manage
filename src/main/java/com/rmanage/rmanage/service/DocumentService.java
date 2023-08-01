package com.rmanage.rmanage.service;

import com.rmanage.rmanage.config.s3Setting.S3Uploader;
import com.rmanage.rmanage.dto.ResponseDocument;
import com.rmanage.rmanage.entity.Document;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;
import com.rmanage.rmanage.entity.Worker;
import com.rmanage.rmanage.repository.DocumentRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

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

    public ResponseDocument getDocument(int workEmployeesId, String documentsType) {
        try {
            Document document = null;
            Worker worker = entityManager.find(Worker.class, workEmployeesId);
            if(worker == null){
                return new ResponseDocument(false,3012,"해당하는 근무지, 근로자 정보가 없음",new Document(),null);
            }
            List<Document> documents = documentRepository.findDocumentByWorker(worker);
            for(Document d : documents){
                if(documentsType.equals(d.getType())){
                    document = d;
                    break;
                }
            }
            if(document == null) {
                return new ResponseDocument(false,3013,"해당하는 문서가 존재하지 않음",new Document(),null);
            }

            ResponseDocument responseDocument = new ResponseDocument(true,1011,"서류 조회 성공",document,null);
            return responseDocument;
        }   catch (Exception e){
            System.out.println(e);
            return new ResponseDocument(false,3021,"서류 조회 실패",new Document(),null);
        }

    }

    public ResponseDocument postDocument(int workEmployeesId, String documentsType, LocalDate validity, MultipartFile image) {
        try {
            List<String> types = List.of("employmentContract","insuranceDocument","insuranceCard");

            if(!types.contains(documentsType)){
                return new ResponseDocument(false,2070,"타입이 누락되거나 잘못됨",new Document(),null);
            }

            Worker worker = entityManager.find(Worker.class, workEmployeesId);
            if(worker == null){
                return new ResponseDocument(false,3012,"해당하는 근무지, 근로자 정보가 없음",new Document(),null);
            }
            List<Document> documents = documentRepository.findDocumentByWorker(worker);
            for(Document d : documents){
                if(documentsType.equals(d.getType())){
                    return new ResponseDocument(false,2080,"이미 겹치는 서류가 존재함.",new Document(),null);
                }
            }
            String filename = "";
            s3Uploader.uploadFiles(image,"image");
            Document document = new Document(worker.getUser(), worker.getWorkPlace(), documentsType, validity, worker);
            Document theDocument = documentRepository.save(document);
            ResponseDocument responseDocument = new ResponseDocument(true,1012,"서류 등록 성공",theDocument,filename);
            return responseDocument;
        }   catch (Exception e) {
            System.out.println(e);
            return new ResponseDocument(false,3022,"서류 등록 실패",new Document(),null);
        }

    }
}

