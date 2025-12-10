package com.epm.gestepm.rest.inspection.mappers;

import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateInspectionV1RequestData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapIToInspectionUpdateDto {

    @Mapping(target = "files", ignore = true)
    @Mapping(target = "materialsFile", ignore = true)
    InspectionUpdateDto from(UpdateInspectionV1RequestData req);

}
