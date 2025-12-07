package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.updater.ProgrammedShareFileUpdate;
import com.epm.gestepm.modelapi.shares.programmed.dto.updater.ProgrammedShareFileUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSFToProgrammedShareFileUpdate {

    ProgrammedShareFileUpdate from(ProgrammedShareFileUpdateDto updateDto);

}
