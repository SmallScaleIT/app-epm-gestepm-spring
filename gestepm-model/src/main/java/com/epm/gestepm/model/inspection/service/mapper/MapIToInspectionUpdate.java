package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionUpdate;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import org.mapstruct.*;

@Mapper
public interface MapIToInspectionUpdate {

  @Mapping(target = "materialsFile", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  InspectionUpdate from(InspectionUpdateDto source, @MappingTarget InspectionUpdate target);

  @Mapping(target = "materialsFile", ignore = true)
  InspectionUpdate from(InspectionUpdateDto updateDto);

  InspectionUpdate from(InspectionDto dto);

}
