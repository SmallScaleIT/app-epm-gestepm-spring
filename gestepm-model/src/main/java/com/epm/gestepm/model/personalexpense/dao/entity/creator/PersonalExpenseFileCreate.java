package com.epm.gestepm.model.personalexpense.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.file.FileUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Base64;

import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseFileAttributes.*;
import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareFileAttributes.ATTR_PSF_CONTENT;
import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareFileAttributes.ATTR_PSF_STORAGE_PATH;

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
        map.put(ATTR_PEF_CONTENT, FileUtils.compressBytes("to-remove".getBytes())); // FIXME: remove

        return map;
    }
}
