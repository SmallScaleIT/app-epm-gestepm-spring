package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapIToInspectionUpdateDto {

    @Mapping(target = "materialsFile", ignore = true)
    InspectionUpdateDto from(InspectionDto dto);

}
