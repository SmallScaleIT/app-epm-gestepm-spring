package com.epm.gestepm.rest.material.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.material.dto.MaterialDto;
import com.epm.gestepm.restapi.openapi.model.Material;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapMATToMaterialResponse {

  Material from(MaterialDto dto);

  List<Material> from(Page<MaterialDto> list);

}
