package com.epm.gestepm.modelapi.material.service;

import com.epm.gestepm.modelapi.material.dto.creator.MaterialInspectionCreateDto;
import com.epm.gestepm.modelapi.material.dto.deleter.MaterialInspectionDeleteDto;

import javax.validation.Valid;

public interface MaterialInspectionService {

    void create(@Valid MaterialInspectionCreateDto createDto);

    void delete(@Valid MaterialInspectionDeleteDto deleteDto);

}
