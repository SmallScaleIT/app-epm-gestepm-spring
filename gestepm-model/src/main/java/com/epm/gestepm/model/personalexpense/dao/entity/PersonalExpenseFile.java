package com.epm.gestepm.model.personalexpense.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PersonalExpenseFile implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer personalExpenseId;

    @NotNull
    private String name;

    // FIXME: @NotNull
    private String storagePath;

    @NotNull
    private byte[] content;

    // FIXME: @NotNull
    private String url;

}
