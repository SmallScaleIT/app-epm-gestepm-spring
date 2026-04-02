package com.epm.gestepm.model.material.dao.entity.creator;

import com.epm.gestepm.lib.audit.AuditCreate;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.material.dao.constants.MaterialAttributes.*;

@Data
public class MaterialCreate implements AuditCreate, CollectableAttributes {

    @NotNull
    private String nameEs;

    @NotNull
    private String nameFr;

    @NotNull
    private Boolean required;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_MAT_NAME_ES, this.nameEs);
        map.put(ATTR_MAT_NAME_FR, this.nameFr);
        map.put(ATTR_MAT_REQUIRED, this.required);

        return map;
    }

}
