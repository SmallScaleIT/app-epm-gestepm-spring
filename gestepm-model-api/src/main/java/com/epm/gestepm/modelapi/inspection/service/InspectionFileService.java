package com.epm.gestepm.modelapi.inspection.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.inspection.dto.InspectionFileDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionFileCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.deleter.InspectionFileDeleteDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFileFilterDto;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionFileByIdFinderDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionFileUpdateDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareFileDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFileFilterDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface InspectionFileService {

    List<@Valid InspectionFileDto> list(@Valid InspectionFileFilterDto filterDto);

    Page<@Valid InspectionFileDto> list(@Valid InspectionFileFilterDto filterDto, Long offset, Long limit);

    Optional<@Valid InspectionFileDto> find(@Valid InspectionFileByIdFinderDto finderDto);

    @Valid
    InspectionFileDto findOrNotFound(@Valid InspectionFileByIdFinderDto finderDto);

    @Valid
    InspectionFileDto create(@Valid InspectionFileCreateDto createDto);

    @Valid
    InspectionFileDto update(@Valid InspectionFileUpdateDto updateDto);

    void delete(@Valid InspectionFileDeleteDto deleteDto);

}
