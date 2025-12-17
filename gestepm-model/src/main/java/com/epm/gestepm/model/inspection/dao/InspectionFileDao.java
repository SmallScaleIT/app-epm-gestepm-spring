package com.epm.gestepm.model.inspection.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.inspection.dao.entity.InspectionFile;
import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionFileCreate;
import com.epm.gestepm.model.inspection.dao.entity.deleter.InspectionFileDelete;
import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFileFilter;
import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionFileByIdFinder;
import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionFileUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface InspectionFileDao {

    List<InspectionFile> list(@Valid InspectionFileFilter filter);

    Page<InspectionFile> list(@Valid InspectionFileFilter filter, Long offset, Long limit);

    Optional<@Valid InspectionFile> find(@Valid InspectionFileByIdFinder finder);

    @Valid
    InspectionFile create(@Valid InspectionFileCreate create);

    @Valid
    InspectionFile update(@Valid InspectionFileUpdate update);

    void delete(@Valid InspectionFileDelete delete);

}
