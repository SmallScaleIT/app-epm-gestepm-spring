package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.work.dao.entity.WorkShareFile;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareFileDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapWSFToWorkShareFileDto {

    WorkShareFileDto from(WorkShareFile file);

    List<WorkShareFileDto> from(List<WorkShareFile> files);

    default Page<WorkShareFileDto> from(Page<WorkShareFile> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }
}
