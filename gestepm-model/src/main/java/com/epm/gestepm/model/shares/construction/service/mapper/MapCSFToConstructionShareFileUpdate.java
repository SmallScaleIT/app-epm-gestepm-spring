package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareFileUpdate;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareFileUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSFToConstructionShareFileUpdate {

    ConstructionShareFileUpdate from(ConstructionShareFileUpdateDto updateDto);

}
