package com.epm.gestepm.model.material.dao.entity.updater;

import com.epm.gestepm.lib.audit.AuditApprovePaidDischarge;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.material.dao.constants.MaterialAttributes.*;
import static com.epm.gestepm.model.material.dao.constants.MaterialAttributes.ATTR_MAT_REQUIRED;

@Data
public class MaterialUpdate implements AuditApprovePaidDischarge, CollectableAttributes {

    @NotNull
    private Integer id;

    @NotNull
    private String nameEs;

    @NotNull
    private String nameFr;

    @NotNull
    private Boolean required;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_MAT_ID, this.id);
        map.put(ATTR_MAT_NAME_ES, this.nameEs);
        map.put(ATTR_MAT_NAME_FR, this.nameFr);
        map.put(ATTR_MAT_REQUIRED, this.required);

        return map;
    }

}
