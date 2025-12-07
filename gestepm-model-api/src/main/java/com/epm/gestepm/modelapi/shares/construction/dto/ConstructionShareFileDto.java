package com.epm.gestepm.modelapi.shares.construction.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class ConstructionShareFileDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private UUID storageUUID;

    @NotNull
    private byte[] content;

    // FIXME: @NotNull
    private String url;

}
