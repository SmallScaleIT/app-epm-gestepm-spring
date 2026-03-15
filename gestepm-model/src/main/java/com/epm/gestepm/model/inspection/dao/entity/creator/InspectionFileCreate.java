package com.epm.gestepm.model.inspection.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.inspection.dao.constants.InspectionFileAttributes.*;

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

        return map;
    }
}
