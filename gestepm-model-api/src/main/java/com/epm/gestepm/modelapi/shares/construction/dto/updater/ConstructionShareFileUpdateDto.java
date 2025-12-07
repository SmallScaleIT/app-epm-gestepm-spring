package com.epm.gestepm.modelapi.shares.construction.dto.updater;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ConstructionShareFileUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private UUID storageUUID;

}
