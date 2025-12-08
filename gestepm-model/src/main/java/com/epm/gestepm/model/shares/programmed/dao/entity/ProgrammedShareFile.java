package com.epm.gestepm.model.shares.programmed.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProgrammedShareFile {

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
