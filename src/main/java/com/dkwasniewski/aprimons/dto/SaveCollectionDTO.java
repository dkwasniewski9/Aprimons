package com.dkwasniewski.aprimons.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SaveCollectionDTO {
    @NotNull
    private String userId;
    @NotNull
    private List<String> selectedIds;
}
