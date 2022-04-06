package com.project.dto.response;

import lombok.Data;


@Data
public class ResponseDTO {
    private String type;
    private String message;
    private String fileBase64;

    public ResponseDTO(String type, String message, String fileBase64) {
        this.type = type;
        this.message = message;
        this.fileBase64 = fileBase64;
    }
}
