package com.epm.gestepm.model.shares.construction.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ConstructionShareFile {

    @NotNull
    private Integer id;

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    // FIXME: @NotNull
    private String storagePath;

    @NotNull
    private byte[] content;

    // FIXME: @NotNull
    private String url;

}
