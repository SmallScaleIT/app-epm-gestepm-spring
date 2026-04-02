package com.epm.gestepm.model.material.dao.entity.creator;

import com.epm.gestepm.lib.audit.AuditCreate;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.material.dao.constants.MaterialInspectionAttributes.ATTR_MATI_INSPECTION_ID;
import static com.epm.gestepm.model.material.dao.constants.MaterialInspectionAttributes.ATTR_MATI_MATERIAL_ID;

@Data
public class MaterialInspectionCreate implements AuditCreate, CollectableAttributes {

    @NotNull
    private Integer materialId;

    @NotNull
    private Integer inspectionId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_MATI_MATERIAL_ID, this.materialId);
        map.put(ATTR_MATI_INSPECTION_ID, this.inspectionId);

        return map;
    }

}
