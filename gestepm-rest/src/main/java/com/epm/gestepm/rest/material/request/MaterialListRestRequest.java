package com.epm.gestepm.rest.material.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MaterialListRestRequest extends RestRequest {

    private List<Integer> ids;

    private String nameContains;

    private Boolean required;

}
