package com.epm.gestepm.storageapi.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileCreate {

    @NotNull
    private UUID id;

    @NotNull
    private MultipartFile file;

}
