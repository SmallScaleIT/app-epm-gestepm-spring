package com.epm.gestepm.model.material.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.material.dao.constants.MaterialAttributes.ATTR_MAT_ID;

@Data
public class MaterialByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_MAT_ID, this.id);

    return map;
  }
}
