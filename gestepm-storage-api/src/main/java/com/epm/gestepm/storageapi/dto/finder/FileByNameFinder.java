package com.epm.gestepm.storageapi.dto.finder;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileByNameFinder {

    @NotNull
    private String name;

}
