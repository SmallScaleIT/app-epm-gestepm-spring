package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.updater.WorkShareFileUpdate;
import com.epm.gestepm.modelapi.shares.work.dto.updater.WorkShareFileUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSFToWorkShareFileUpdate {

    WorkShareFileUpdate from(WorkShareFileUpdateDto updateDto);

}
