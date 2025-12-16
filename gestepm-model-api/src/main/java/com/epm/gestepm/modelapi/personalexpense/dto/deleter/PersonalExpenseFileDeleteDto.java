package com.epm.gestepm.modelapi.personalexpense.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonalExpenseFileDeleteDto {

    @NotNull
    private Integer id;

}
