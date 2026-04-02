package com.epm.gestepm.model.material.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.material.dao.constants.MaterialInspectionAttributes.ATTR_MATI_INSPECTION_ID;

@Data
public class MaterialInspectionDelete implements CollectableAttributes {

    @NotNull
    private Integer inspectionId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_MATI_INSPECTION_ID, this.inspectionId);

        return map;
    }

}
