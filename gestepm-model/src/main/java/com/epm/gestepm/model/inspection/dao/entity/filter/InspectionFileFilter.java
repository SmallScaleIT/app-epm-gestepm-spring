package com.epm.gestepm.model.inspection.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import java.util.List;

import static com.epm.gestepm.model.inspection.dao.constants.InspectionFileAttributes.*;

@Data
public class InspectionFileFilter implements CollectableAttributes {

    private List<Integer> ids;

    private Integer inspectionId;

    private Boolean needMigration;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_IF_IDS, this.ids);
        map.put(ATTR_IF_INSPECTION_ID, this.inspectionId);
        map.putBooleanAsInt(ATTR_IF_NEED_MIGRATION, this.needMigration);

        return map;
    }
}
