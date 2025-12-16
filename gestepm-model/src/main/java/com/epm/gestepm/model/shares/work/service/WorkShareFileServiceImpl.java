package com.epm.gestepm.model.shares.work.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShareFile;
import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareFileUpdate;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSFToConstructionShareFileDto;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSFToConstructionShareFileUpdate;
import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShareFile;
import com.epm.gestepm.model.shares.programmed.dao.entity.filter.ProgrammedShareFileFilter;
import com.epm.gestepm.model.shares.programmed.service.mapper.MapPSFToProgrammedShareFileDto;
import com.epm.gestepm.model.shares.programmed.service.mapper.MapPSFToProgrammedShareFileFilter;
import com.epm.gestepm.model.shares.work.dao.WorkShareFileDao;
import com.epm.gestepm.model.shares.work.dao.entity.WorkShareFile;
import com.epm.gestepm.model.shares.work.dao.entity.creator.WorkShareFileCreate;
import com.epm.gestepm.model.shares.work.dao.entity.deleter.WorkShareFileDelete;
import com.epm.gestepm.model.shares.work.dao.entity.filter.WorkShareFileFilter;
import com.epm.gestepm.model.shares.work.dao.entity.finder.WorkShareFileByIdFinder;
import com.epm.gestepm.model.shares.work.dao.entity.updater.WorkShareFileUpdate;
import com.epm.gestepm.model.shares.work.service.mapper.*;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareFileDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareFileUpdateDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareFileDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareFileDto;
import com.epm.gestepm.modelapi.shares.work.dto.creator.WorkShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.work.dto.deleter.WorkShareFileDeleteDto;
import com.epm.gestepm.modelapi.shares.work.dto.filter.WorkShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.work.dto.finder.WorkShareFileByIdFinderDto;
import com.epm.gestepm.modelapi.shares.work.dto.updater.WorkShareFileUpdateDto;
import com.epm.gestepm.modelapi.shares.work.exception.WorkShareFileNotFoundException;
import com.epm.gestepm.modelapi.shares.work.service.WorkShareFileService;
import com.epm.gestepm.storageapi.dto.FileResponse;
import com.epm.gestepm.storageapi.dto.deleter.FileDelete;
import com.epm.gestepm.storageapi.dto.finder.FileByNameFinder;
import com.epm.gestepm.storageapi.service.GoogleCloudStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_EDIT_CS;
import static com.epm.gestepm.modelapi.shares.programmed.security.ProgrammedSharePermission.PRMT_READ_PS;
import static com.epm.gestepm.modelapi.shares.work.security.WorkSharePermission.PRMT_EDIT_WS;
import static com.epm.gestepm.modelapi.shares.work.security.WorkSharePermission.PRMT_READ_WS;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@RequiredArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class WorkShareFileServiceImpl implements WorkShareFileService {

    private final GoogleCloudStorageService googleCloudStorageService;

    private final WorkShareFileDao workShareFileDao;

    @Override
    @RequirePermits(value = PRMT_READ_WS, action = "List work shares")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating work share file files",
            msgOut = "Paginating work share file files OK",
            errorMsg = "Failed to paginate work share file files")
    public List<WorkShareFileDto> list(WorkShareFileFilterDto filterDto) {
        final WorkShareFileFilter filter = getMapper(MapWSFToWorkShareFileFilter.class).from(filterDto);

        final List<WorkShareFile> list = this.workShareFileDao.list(filter);
        list.forEach(this::populateFileUrl);

        return getMapper(MapWSFToWorkShareFileDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PS, action = "Page work shares")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating work share file files",
            msgOut = "Paginating work share file files OK",
            errorMsg = "Failed to paginate work share file files")
    public Page<WorkShareFileDto> list(WorkShareFileFilterDto filterDto, Long offset, Long limit) {
        final WorkShareFileFilter filter = getMapper(MapWSFToWorkShareFileFilter.class).from(filterDto);

        final Page<WorkShareFile> list = this.workShareFileDao.list(filter, offset, limit);
        list.forEach(this::populateFileUrl);

        return getMapper(MapWSFToWorkShareFileDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_WS, action = "Find work share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding work share file by ID, result can be empty",
            msgOut = "Found work share file by ID",
            errorMsg = "Failed to find work share file by ID")
    public Optional<WorkShareFileDto> find(final WorkShareFileByIdFinderDto finderDto) {
        final WorkShareFileByIdFinder finder = getMapper(MapWSFToWorkShareFileByIdFinder.class).from(finderDto);

        final Optional<WorkShareFile> found = this.workShareFileDao.find(finder);
        found.ifPresent(this::populateFileUrl);

        return found.map(getMapper(MapWSFToWorkShareFileDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_WS, action = "Find work share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding work share file by ID, result is expected or will fail",
            msgOut = "Found work share file by ID",
            errorMsg = "Work share by ID not found")
    public WorkShareFileDto findOrNotFound(final WorkShareFileByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new WorkShareFileNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_WS, action = "Create new work share file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new work share file",
            msgOut = "New country work share file OK",
            errorMsg = "Failed to create new work share file")
    public WorkShareFileDto create(WorkShareFileCreateDto createDto) {
        final WorkShareFileCreate create = getMapper(MapWSFToWorkShareFileCreate.class).from(createDto);

        final WorkShareFile result = this.workShareFileDao.create(create);
        this.populateFileUrl(result);
        
        return getMapper(MapWSFToWorkShareFileDto.class).from(result);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_CS, action = "Update work share file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Updating new work share file",
            msgOut = "Work share file OK",
            errorMsg = "Failed to update work share file")
    public WorkShareFileDto update(WorkShareFileUpdateDto updateDto) {
        final WorkShareFileUpdate update = getMapper(MapWSFToWorkShareFileUpdate.class).from(updateDto);

        final WorkShareFile result = this.workShareFileDao.update(update);
        this.populateFileUrl(result);

        return getMapper(MapWSFToWorkShareFileDto.class).from(result);
    }
    
    @Override
    @RequirePermits(value = PRMT_EDIT_WS, action = "Delete work share file")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting work share file",
            msgOut = "Work share file deleted OK",
            errorMsg = "Failed to delete work share file")
    public void delete(WorkShareFileDeleteDto deleteDto) {

        final WorkShareFileByIdFinderDto finderDto = new WorkShareFileByIdFinderDto(deleteDto.getId());

        final WorkShareFileDto fileDto = this.findOrNotFound(finderDto);

        this.googleCloudStorageService.deleteFile(new FileDelete(fileDto.getStoragePath()));

        final WorkShareFileDelete delete = getMapper(MapWSFToWorkShareFileDelete.class).from(deleteDto);

        this.workShareFileDao.delete(delete);
    }

    private void populateFileUrl(final WorkShareFile file) {
        if (file.getStoragePath() == null) { // FIXME: to remove
            return;
        }

        final FileByNameFinder finder = new FileByNameFinder(file.getStoragePath());
        final FileResponse fileResponse = this.googleCloudStorageService.getFile(finder);

        file.setUrl(fileResponse.getUrl());
    }
}
