package com.epm.gestepm.model.shares.construction.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ConstructionShareFile {

    @NotNull
    private Integer id;

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    @NotNull
    private UUID storageUUID;

    @NotNull
    private byte[] content;

    // FIXME: @NotNull
    private String url;

}
