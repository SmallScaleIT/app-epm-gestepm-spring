package com.epm.gestepm.storageapi.service;

import com.epm.gestepm.storageapi.dto.FileResponse;
import com.epm.gestepm.storageapi.dto.creator.FileCreate;
import com.epm.gestepm.storageapi.dto.deleter.FileDelete;
import com.epm.gestepm.storageapi.dto.finder.FileByNameFinder;
import javax.validation.Valid;

public interface GoogleCloudStorageService {

    @Valid
    FileResponse uploadFile(@Valid FileCreate create);

    FileResponse getFile(@Valid FileByNameFinder find);

    boolean deleteFile(@Valid FileDelete delete);

}
