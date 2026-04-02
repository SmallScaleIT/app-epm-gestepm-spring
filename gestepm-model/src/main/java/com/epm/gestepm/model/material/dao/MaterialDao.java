package com.epm.gestepm.model.material.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.material.dao.entity.Material;
import com.epm.gestepm.model.material.dao.entity.creator.MaterialCreate;
import com.epm.gestepm.model.material.dao.entity.deleter.MaterialDelete;
import com.epm.gestepm.model.material.dao.entity.filter.MaterialFilter;
import com.epm.gestepm.model.material.dao.entity.finder.MaterialByIdFinder;
import com.epm.gestepm.model.material.dao.entity.updater.MaterialUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface MaterialDao {

  List<Material> list(@Valid MaterialFilter filter);

  Page<Material> list(@Valid MaterialFilter filter, Long offset, Long limit);

  Optional<@Valid Material> find(@Valid MaterialByIdFinder finder);

  @Valid
  Material create(@Valid MaterialCreate create);

  @Valid
  Material update(@Valid MaterialUpdate update);

  void delete(@Valid MaterialDelete delete);

}
