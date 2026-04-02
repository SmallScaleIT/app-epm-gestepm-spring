package com.epm.gestepm.rest.material.mappers;

import com.epm.gestepm.modelapi.material.dto.creator.MaterialCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateMaterialV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATToMaterialCreateDto {

  MaterialCreateDto from(CreateMaterialV1Request req);

}
