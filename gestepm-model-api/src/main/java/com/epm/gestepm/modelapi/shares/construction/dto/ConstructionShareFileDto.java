package com.epm.gestepm.modelapi.shares.construction.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ConstructionShareFileDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    // FIXME: @NotNull
    private String storagePath;

    @NotNull
    private byte[] content;

    // FIXME: @NotNull
    private String url;

}
