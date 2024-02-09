package com.dkwasniewski.aprimons.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveCollectionDTO {
    private String userId;
    private List<String> selectedIds;
}
