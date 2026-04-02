package com.epm.gestepm.model.material.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.material.dao.MaterialDao;
import com.epm.gestepm.model.material.dao.entity.Material;
import com.epm.gestepm.model.material.dao.entity.creator.MaterialCreate;
import com.epm.gestepm.model.material.dao.entity.deleter.MaterialDelete;
import com.epm.gestepm.model.material.dao.entity.filter.MaterialFilter;
import com.epm.gestepm.model.material.dao.entity.finder.MaterialByIdFinder;
import com.epm.gestepm.model.material.dao.entity.updater.MaterialUpdate;
import com.epm.gestepm.model.material.service.mapper.*;
import com.epm.gestepm.modelapi.material.dto.MaterialDto;
import com.epm.gestepm.modelapi.material.dto.creator.MaterialCreateDto;
import com.epm.gestepm.modelapi.material.dto.deleter.MaterialDeleteDto;
import com.epm.gestepm.modelapi.material.dto.filter.MaterialFilterDto;
import com.epm.gestepm.modelapi.material.dto.finder.MaterialByIdFinderDto;
import com.epm.gestepm.modelapi.material.dto.updater.MaterialUpdateDto;
import com.epm.gestepm.modelapi.material.exception.MaterialNotFoundException;
import com.epm.gestepm.modelapi.material.service.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.material.security.MaterialPermission.PRMT_EDIT_MAT;
import static com.epm.gestepm.modelapi.material.security.MaterialPermission.PRMT_READ_MAT;
import static org.mapstruct.factory.Mappers.getMapper;

@AllArgsConstructor
@Validated
@Service("materialService")
@EnableExecutionLog(layerMarker = SERVICE)
public class MaterialServiceImpl implements MaterialService {

    private final MaterialDao materialDao;

    @Override
    @RequirePermits(value = PRMT_READ_MAT, action = "List project materials")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing project materials",
            msgOut = "Listing project materials OK",
            errorMsg = "Failed to list project materials")
    public List<MaterialDto> list(MaterialFilterDto filterDto) {

        final MaterialFilter filter = getMapper(MapMATToMaterialFilter.class).from(filterDto);

        final List<Material> list = this.materialDao.list(filter);

        return getMapper(MapMATToMaterialDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_MAT, action = "Page project materials")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating project materials",
            msgOut = "Paginating project materials OK",
            errorMsg = "Failed to paginate project materials")
    public Page<MaterialDto> list(MaterialFilterDto filterDto, Long offset, Long limit) {

        final MaterialFilter filter = getMapper(MapMATToMaterialFilter.class).from(filterDto);

        final Page<Material> page = this.materialDao.list(filter, offset, limit);

        return getMapper(MapMATToMaterialDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_MAT, action = "Find project material by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding project material by ID, result can be empty",
            msgOut = "Found project material by ID",
            errorMsg = "Failed to find project material by ID")
    public Optional<MaterialDto> find(MaterialByIdFinderDto finderDto) {

        final MaterialByIdFinder finder = getMapper(MapMATToMaterialByIdFinder.class).from(finderDto);

        final Optional<Material> found = this.materialDao.find(finder);

        return found.map(getMapper(MapMATToMaterialDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_MAT, action = "Find project material by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding project material by ID, result is expected or will fail",
            msgOut = "Found project material by ID",
            errorMsg = "Personal expense sheet by ID not found")
    public MaterialDto findOrNotFound(MaterialByIdFinderDto finderDto) {

        final Supplier<RuntimeException> notFound = () -> new MaterialNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_MAT, action = "Create new project material")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new project material",
            msgOut = "New project material created OK",
            errorMsg = "Failed to create new project material")
    public MaterialDto create(MaterialCreateDto createDto) {

        final MaterialCreate create = getMapper(MapMATToMaterialCreate.class).from(createDto);

        final Material material = this.materialDao.create(create);

        return getMapper(MapMATToMaterialDto.class).from(material);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_MAT, action = "Update project material")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating project material",
            msgOut = "Personal expense sheet updated OK",
            errorMsg = "Failed to update project material")
    public MaterialDto update(MaterialUpdateDto updateDto) {

        final MaterialByIdFinderDto finderDto = new MaterialByIdFinderDto();
        finderDto.setId(updateDto.getId());

        findOrNotFound(finderDto);

        final MaterialUpdate update = getMapper(MapMATToMaterialUpdate.class).from(updateDto);

        final Material updated = this.materialDao.update(update);

        return getMapper(MapMATToMaterialDto.class).from(updated);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_MAT, action = "Delete project material")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting project material",
            msgOut = "Personal expense sheet deleted OK",
            errorMsg = "Failed to delete project material")
    public void delete(MaterialDeleteDto deleteDto) {

        final MaterialByIdFinderDto finderDto = new MaterialByIdFinderDto();
        finderDto.setId(deleteDto.getId());

        findOrNotFound(finderDto);

        final MaterialDelete delete = getMapper(MapMATToMaterialDelete.class).from(deleteDto);

        this.materialDao.delete(delete);
    }
}
