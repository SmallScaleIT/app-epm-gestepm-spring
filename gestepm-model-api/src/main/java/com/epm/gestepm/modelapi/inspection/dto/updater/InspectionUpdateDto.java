package com.epm.gestepm.modelapi.inspection.dto.updater;

import com.epm.gestepm.modelapi.inspection.dto.ActionEnumDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionFileCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.MaterialCreateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class InspectionUpdateDto {

    @NotNull
    private Integer id;

    private Integer userId;

    private Integer userSigningId;

    private Integer shareId;

    private ActionEnumDto action;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;

    private Integer firstTechnicalId;

    private Integer secondTechnicalId;

    private String signature;

    private String operatorSignature;

    private String clientName;

    private List<MaterialCreateDto> materials;

    private MultipartFile materialsFile;

    private String materialsStoragePath;

    private Integer equipmentHours;

    private Integer topicId;

    @JsonIgnore
    private List<MultipartFile> files;

    private Boolean notify;

}
