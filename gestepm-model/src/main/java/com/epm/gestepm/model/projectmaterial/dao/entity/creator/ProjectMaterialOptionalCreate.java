package com.epm.gestepm.model.projectmaterial.dao.entity.creator;

import com.epm.gestepm.lib.audit.AuditCreate;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.projectmaterial.dao.constants.ProjectMaterialOptionalAttributes.ATTR_PMO_INSPECTION_ID;
import static com.epm.gestepm.model.projectmaterial.dao.constants.ProjectMaterialOptionalAttributes.ATTR_PMO_PROJECT_MATERIAL_ID;

@Data
public class ProjectMaterialOptionalCreate implements AuditCreate, CollectableAttributes {

    @NotNull
    private Integer projectMaterialId;

    @NotNull
    private Integer inspectionId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PMO_PROJECT_MATERIAL_ID, this.projectMaterialId);
        map.put(ATTR_PMO_INSPECTION_ID, this.inspectionId);

        return map;
    }

}
