package com.epm.gestepm.model.shares.work.dao;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.work.dao.entity.WorkShareFile;
import com.epm.gestepm.model.shares.work.dao.entity.creator.WorkShareFileCreate;
import com.epm.gestepm.model.shares.work.dao.entity.deleter.WorkShareFileDelete;
import com.epm.gestepm.model.shares.work.dao.entity.filter.WorkShareFileFilter;
import com.epm.gestepm.model.shares.work.dao.entity.finder.WorkShareFileByIdFinder;
import com.epm.gestepm.model.shares.work.dao.entity.updater.WorkShareFileUpdate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface WorkShareFileDao {

  List<WorkShareFile> list(@Valid WorkShareFileFilter filter);

  Page<WorkShareFile> list(@Valid WorkShareFileFilter filter, Long offset, Long limit);

  Optional<@Valid WorkShareFile> find(@Valid WorkShareFileByIdFinder finder);

  @Valid
  WorkShareFile create(@Valid WorkShareFileCreate create);

  @Valid
  WorkShareFile update(@Valid WorkShareFileUpdate update);

  void delete(@Valid WorkShareFileDelete delete);

}
