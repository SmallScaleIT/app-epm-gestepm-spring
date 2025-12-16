package com.epm.gestepm.modelapi.personalexpense.dto.creator;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotNull;

@Data
public class PersonalExpenseFileCreateDto {

    @NotNull
    private Integer personalExpenseId;

    @NotNull
    private String name;

}
