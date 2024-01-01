package com.dkwasniewski.aprimons.dto;

import lombok.Data;

@Data
public class ResponseDTO {
    public int status;

    public ResponseDTO(int status) {
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
