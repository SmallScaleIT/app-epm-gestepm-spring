package com.epm.gestepm.model.shares.construction.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileAttributes.*;

@Data
public class ConstructionShareFileUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_CSF_ID, this.id);
        map.put(ATTR_CSF_NAME, this.name);
        map.put(ATTR_CSF_STORAGE_PATH, this.storagePath);

        return map;
    }
}
