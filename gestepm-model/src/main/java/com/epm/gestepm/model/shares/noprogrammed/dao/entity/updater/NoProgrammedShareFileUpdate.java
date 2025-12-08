package com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileAttributes.*;

@Data
public class NoProgrammedShareFileUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_NPSF_ID, this.id);
        map.put(ATTR_NPSF_NAME, this.name);
        map.put(ATTR_NPSF_STORAGE_PATH, this.storagePath);

        return map;
    }
}
