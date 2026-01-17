package com.epm.gestepm.modelapi.shares.programmed.dto.updater;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Singular;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProgrammedShareUpdateDto {

    @NotNull
    private Integer id;

    private Integer projectId;

    private Integer secondTechnicalId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String observations;

    private String customerSignature;

    private String operatorSignature;

    @JsonIgnore
    private List<MultipartFile> files;

    private Boolean notify;

}
