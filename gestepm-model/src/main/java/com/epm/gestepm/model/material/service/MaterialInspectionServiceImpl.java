package com.epm.gestepm.model.material.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.material.dao.MaterialInspectionDao;
import com.epm.gestepm.model.material.dao.entity.creator.MaterialInspectionCreate;
import com.epm.gestepm.model.material.dao.entity.deleter.MaterialInspectionDelete;
import com.epm.gestepm.model.material.service.mapper.*;
import com.epm.gestepm.modelapi.material.dto.creator.MaterialInspectionCreateDto;
import com.epm.gestepm.modelapi.material.dto.deleter.MaterialInspectionDeleteDto;
import com.epm.gestepm.modelapi.material.service.MaterialInspectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static org.mapstruct.factory.Mappers.getMapper;

@AllArgsConstructor
@Validated
@Service("materialInspectionService")
@EnableExecutionLog(layerMarker = SERVICE)
public class MaterialInspectionServiceImpl implements MaterialInspectionService {

    private final MaterialInspectionDao materialInspectionDao;

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new project material optional",
            msgOut = "New project material optional created OK",
            errorMsg = "Failed to create new project material optional")
    public void create(final MaterialInspectionCreateDto createDto) {

        final MaterialInspectionCreate create = getMapper(MapMATIToMaterialInspectionCreate.class).from(createDto);

        this.materialInspectionDao.create(create);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting project material optional",
            msgOut = "Project material optional deleted OK",
            errorMsg = "Failed to delete project material optional")
    public void delete(final MaterialInspectionDeleteDto deleteDto) {

        final MaterialInspectionDelete delete = getMapper(MapMATIToMaterialInsepctionDelete.class).from(deleteDto);

        this.materialInspectionDao.delete(delete);
    }
}
