package com.epm.gestepm.model.material.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.model.material.dao.constants.MaterialAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialFilter extends Orderable implements CollectableAttributes {

  private List<Integer> ids;

  private List<Integer> projectIds;

  private String nameContains;

  private Boolean required;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_MAT_IDS, this.ids);
    map.putLike(ATTR_MAT_NAME_CONTAINS, this.nameContains);
    map.putBooleanAsInt(ATTR_MAT_REQUIRED, this.required);

    return map;
  }

}
