package com.epm.gestepm.modelapi.material.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MaterialNotFoundException extends RuntimeException {

  private final Integer id;

}
