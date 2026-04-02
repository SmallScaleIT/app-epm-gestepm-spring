package com.epm.gestepm.model.material.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.material.dao.entity.Material;
import com.epm.gestepm.modelapi.material.dto.MaterialDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapMATToMaterialDto {

  MaterialDto from(Material material);

  List<MaterialDto> from(List<Material> material);

  default Page<MaterialDto> from(Page<Material> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

}
