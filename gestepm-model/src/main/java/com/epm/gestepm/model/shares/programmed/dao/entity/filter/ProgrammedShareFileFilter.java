package com.epm.gestepm.model.shares.programmed.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import java.util.List;

import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareFileAttributes.*;

@Data
public class ProgrammedShareFileFilter implements CollectableAttributes {

    private List<Integer> ids;

    private Integer shareId;

    private Boolean needMigration;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_PSF_IDS, this.ids);
        map.put(ATTR_PSF_SHARE_ID, this.shareId);
        map.putBooleanAsInt(ATTR_PSF_NEED_MIGRATION, this.needMigration);

        return map;
    }
}
