package com.epm.gestepm.model.material.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Material implements Serializable {

  @NotNull
  private Integer id;

  @NotNull
  private String nameEs;

  @NotNull
  private String nameFr;

  @NotNull
  private Boolean required;

}
