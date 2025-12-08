package com.epm.gestepm.rest.inspection.mappers;

import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionFileCreateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateInspectionV1RequestFilesInner;
import org.mapstruct.Mapper;

@Mapper
public interface MapIFToFileDto {

    InspectionFileCreateDto from(UpdateInspectionV1RequestFilesInner req);

}
