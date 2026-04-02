package com.epm.gestepm.rest.material.mappers;

import com.epm.gestepm.modelapi.material.dto.filter.MaterialFilterDto;
import com.epm.gestepm.rest.material.request.MaterialListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATToMaterialFilterDto {

  MaterialFilterDto from(MaterialListRestRequest req);

}
