package com.rmanage.rmanage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workplace_id")
    private WorkPlace workPlace;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workerId")
    private Worker worker;
    private String type;

    private String imageUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expireDate;

    public Document(User user, WorkPlace workPlace, String type, LocalDate expireDate, Worker worker, String imageUrl) {
        this.user = user;
        this.workPlace = workPlace;
        this.type = type;
        this.expireDate = expireDate;
        this.worker = worker;
        this.imageUrl = imageUrl;
    }

    public Document(Long documentId, User user, WorkPlace workPlace, Worker worker, String type, String imageUrl, LocalDate expireDate) {
        this.documentId = documentId;
        this.user = user;
        this.workPlace = workPlace;
        this.worker = worker;
        this.type = type;
        this.imageUrl = imageUrl;
        this.expireDate = expireDate;
    }
}
