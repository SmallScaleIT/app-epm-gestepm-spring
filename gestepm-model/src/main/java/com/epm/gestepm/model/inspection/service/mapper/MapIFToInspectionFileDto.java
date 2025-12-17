package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.inspection.dao.entity.InspectionFile;
import com.epm.gestepm.modelapi.inspection.dto.InspectionFileDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapIFToInspectionFileDto {

    InspectionFileDto from(InspectionFile file);

    List<InspectionFileDto> from(List<InspectionFile> files);

    default Page<InspectionFileDto> from(Page<InspectionFile> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }
}
