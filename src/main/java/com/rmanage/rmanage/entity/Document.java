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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expireDate;

    public Document(User user, WorkPlace workPlace, String type, LocalDate expireDate, Worker worker) {
        this.user = user;
        this.workPlace = workPlace;
        this.type = type;
        this.expireDate = expireDate;
        this.worker = worker;
    }
}
