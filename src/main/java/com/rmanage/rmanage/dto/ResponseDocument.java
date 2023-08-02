package com.rmanage.rmanage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmanage.rmanage.entity.Document;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;
import com.rmanage.rmanage.entity.Worker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ResponseDocument {
    private Long documentId;
    private String type;
    private String imageUrl;
    private LocalDate expireDate;
}
