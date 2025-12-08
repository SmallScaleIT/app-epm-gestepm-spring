package com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileAttributes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Base64;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileAttributes.ATTR_CSF_CONTENT;
import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileAttributes.ATTR_CSF_STORAGE_PATH;
import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileAttributes.ATTR_NPSF_CONTENT;
import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileAttributes.ATTR_NPSF_STORAGE_PATH;

@Data
public class NoProgrammedShareFileCreate implements CollectableAttributes {

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(NoProgrammedShareFileAttributes.ATTR_NPSF_SHARE_ID, this.shareId);
        map.put(NoProgrammedShareFileAttributes.ATTR_NPSF_NAME, this.name);
        map.put(ATTR_NPSF_STORAGE_PATH, this.storagePath);
        map.put(ATTR_NPSF_CONTENT, FileUtils.compressBytes("to-remove".getBytes())); // FIXME: remove

        return map;
    }
}
