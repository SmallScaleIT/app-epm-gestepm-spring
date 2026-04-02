package com.epm.gestepm.model.material.service.mapper;

import com.epm.gestepm.model.material.dao.entity.deleter.MaterialInspectionDelete;
import com.epm.gestepm.modelapi.material.dto.deleter.MaterialInspectionDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapMATIToMaterialInsepctionDelete {

    MaterialInspectionDelete from(MaterialInspectionDeleteDto deleteDto);

}
