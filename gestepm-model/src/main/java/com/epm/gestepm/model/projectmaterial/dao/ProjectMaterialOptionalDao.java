package com.epm.gestepm.model.projectmaterial.dao;

import com.epm.gestepm.model.projectmaterial.dao.entity.creator.ProjectMaterialOptionalCreate;
import com.epm.gestepm.model.projectmaterial.dao.entity.deleter.ProjectMaterialOptionalDelete;

import javax.validation.Valid;

public interface ProjectMaterialOptionalDao {

    void create(@Valid ProjectMaterialOptionalCreate create);

    void delete(@Valid ProjectMaterialOptionalDelete delete);

}
