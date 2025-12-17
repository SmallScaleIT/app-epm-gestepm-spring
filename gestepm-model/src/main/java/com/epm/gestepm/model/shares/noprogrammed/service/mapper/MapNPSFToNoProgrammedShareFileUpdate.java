package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareFileUpdate;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareFileUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSFToNoProgrammedShareFileUpdate {

    NoProgrammedShareFileUpdate from(NoProgrammedShareFileUpdateDto updateDto);

}
