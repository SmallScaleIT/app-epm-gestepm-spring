package com.epm.gestepm.storageapi.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {

    @NotNull
    private String fileName;

    @NotNull
    private byte[] fileData;

    @NotNull
    private String fileType;

    @NotNull
    private String url;

}
