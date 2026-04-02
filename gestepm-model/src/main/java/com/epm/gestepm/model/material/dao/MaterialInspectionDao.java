package com.epm.gestepm.model.material.dao;

import com.epm.gestepm.model.material.dao.entity.creator.MaterialInspectionCreate;
import com.epm.gestepm.model.material.dao.entity.deleter.MaterialInspectionDelete;

import javax.validation.Valid;

public interface MaterialInspectionDao {

    void create(@Valid MaterialInspectionCreate create);

    void delete(@Valid MaterialInspectionDelete delete);

}
