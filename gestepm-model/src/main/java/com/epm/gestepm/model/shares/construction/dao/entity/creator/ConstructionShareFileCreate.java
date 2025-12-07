package com.epm.gestepm.model.shares.construction.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.file.FileUtils;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileAttributes.*;

@Data
public class ConstructionShareFileCreate implements CollectableAttributes {

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_CSF_SHARE_ID, this.shareId);
        map.put(ATTR_CSF_NAME, this.name);
        map.put(ATTR_CSF_STORAGE_PATH, this.storagePath);
        map.put(ATTR_CSF_CONTENT, FileUtils.compressBytes("to-remove".getBytes())); // FIXME: remove

        return map;
    }
}
