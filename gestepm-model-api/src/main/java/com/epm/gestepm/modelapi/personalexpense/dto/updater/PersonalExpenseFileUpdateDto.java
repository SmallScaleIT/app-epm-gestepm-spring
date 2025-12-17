package com.epm.gestepm.modelapi.personalexpense.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonalExpenseFileUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String storagePath;

}
