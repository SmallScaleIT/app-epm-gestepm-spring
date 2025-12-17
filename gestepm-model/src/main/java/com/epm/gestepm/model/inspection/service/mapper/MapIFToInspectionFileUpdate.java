package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionFileUpdate;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionFileUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapIFToInspectionFileUpdate {

    InspectionFileUpdate from(InspectionFileUpdateDto updateDto);

}
