package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShareFile;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareFileDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapPSFToProgrammedShareFileDto {

    ProgrammedShareFileDto from(ProgrammedShareFile file);

    List<ProgrammedShareFileDto> from(List<ProgrammedShareFile> files);

    default Page<ProgrammedShareFileDto> from(Page<ProgrammedShareFile> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
