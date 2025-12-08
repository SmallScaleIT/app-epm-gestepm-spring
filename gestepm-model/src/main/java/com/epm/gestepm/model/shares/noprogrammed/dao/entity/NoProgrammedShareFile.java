package com.epm.gestepm.model.shares.noprogrammed.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class NoProgrammedShareFile implements Serializable {

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
