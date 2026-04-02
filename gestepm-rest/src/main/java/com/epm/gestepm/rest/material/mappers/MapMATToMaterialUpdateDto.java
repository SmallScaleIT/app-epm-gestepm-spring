package com.epm.gestepm.rest.material.mappers;

import com.epm.gestepm.modelapi.material.dto.updater.MaterialUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateMaterialV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATToMaterialUpdateDto {

  MaterialUpdateDto from(UpdateMaterialV1Request req);

}
