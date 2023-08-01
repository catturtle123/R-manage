package com.rmanage.rmanage.dto;

import com.rmanage.rmanage.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDocument {
    private boolean isSuccess;
    private int code;
    private String message;
    private Document result;
    private String imageUrl;
}
