package com.epm.gestepm.model.projectmaterial.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.projectmaterial.dao.ProjectMaterialOptionalDao;
import com.epm.gestepm.model.projectmaterial.dao.entity.creator.ProjectMaterialOptionalCreate;
import com.epm.gestepm.model.projectmaterial.dao.entity.deleter.ProjectMaterialOptionalDelete;
import com.epm.gestepm.model.projectmaterial.service.mapper.*;
import com.epm.gestepm.modelapi.projectmaterial.dto.creator.ProjectMaterialOptionalCreateDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.deleter.ProjectMaterialOptionalDeleteDto;
import com.epm.gestepm.modelapi.projectmaterial.service.ProjectMaterialOptionalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static org.mapstruct.factory.Mappers.getMapper;

@AllArgsConstructor
@Validated
@Service("projectMaterialOptionalService")
@EnableExecutionLog(layerMarker = SERVICE)
public class ProjectMaterialOptionalServiceImpl implements ProjectMaterialOptionalService {

    private final ProjectMaterialOptionalDao projectMaterialOptionalDao;

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new project material optional",
            msgOut = "New project material optional created OK",
            errorMsg = "Failed to create new project material optional")
    public void create(final ProjectMaterialOptionalCreateDto createDto) {

        final ProjectMaterialOptionalCreate create = getMapper(MapPMOToProjectMaterialOptionalCreate.class).from(createDto);

        this.projectMaterialOptionalDao.create(create);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting project material optional",
            msgOut = "Project material optional deleted OK",
            errorMsg = "Failed to delete project material optional")
    public void delete(final ProjectMaterialOptionalDeleteDto deleteDto) {

        final ProjectMaterialOptionalDelete delete = getMapper(MapPMOToProjectMaterialOptionalDelete.class).from(deleteDto);

        this.projectMaterialOptionalDao.delete(delete);
    }
}
