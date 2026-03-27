package com.epm.gestepm.model.personalexpense.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseFileAttributes.*;

@Data
public class PersonalExpenseFileCreate implements CollectableAttributes {

    @NotNull
    private Integer personalExpenseId;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PEF_PE_ID, this.personalExpenseId);
        map.put(ATTR_PEF_NAME, this.name);
        map.put(ATTR_PEF_STORAGE_PATH, this.storagePath);

        return map;
    }
}
