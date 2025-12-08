package com.epm.gestepm.modelapi.shares.construction.dto.updater;

import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareFileCreateDto;
import lombok.Data;
import lombok.Singular;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ConstructionShareUpdateDto {

    @NotNull
    private Integer id;

    private Integer projectId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String observations;

    private String operatorSignature;

    @Singular
    private List<MultipartFile> files;

    private Boolean notify;

}
