package com.epm.gestepm.model.inspection.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.inspection.dao.constants.InspectionFileAttributes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Base64;

import static com.epm.gestepm.model.inspection.dao.constants.InspectionFileAttributes.*;
import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileAttributes.ATTR_CSF_CONTENT;
import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileAttributes.ATTR_CSF_STORAGE_PATH;

@Data
public class InspectionFileCreate implements CollectableAttributes {

    @NotNull
    private Integer inspectionId;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_IF_INSPECTION_ID, this.inspectionId);
        map.put(ATTR_IF_NAME, this.name);
        map.put(ATTR_IF_STORAGE_PATH, this.storagePath);
        map.put(ATTR_IF_CONTENT, FileUtils.compressBytes("to-remove".getBytes())); // FIXME: remove

        return map;
    }
}
