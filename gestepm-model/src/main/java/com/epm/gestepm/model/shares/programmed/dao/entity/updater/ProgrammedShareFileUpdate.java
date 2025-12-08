package com.epm.gestepm.model.shares.programmed.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareFileAttributes.*;

@Data
public class ProgrammedShareFileUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PSF_ID, this.id);
        map.put(ATTR_PSF_NAME, this.name);
        map.put(ATTR_PSF_STORAGE_PATH, this.storagePath);

        return map;
    }
}
