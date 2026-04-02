package com.epm.gestepm.rest.material.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreateMaterialV1200Response;
import com.epm.gestepm.restapi.openapi.model.Material;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForMaterial {

  default ResponseEntity<CreateMaterialV1200Response> toResMaterialResponse(Material data) {

    final CreateMaterialV1200Response response = new CreateMaterialV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateMaterialV1200Response> toResMaterialResponse(APIMetadata metadata, Material data) {

    if (metadata == null) {
      return toResMaterialResponse(data);
    }

    final CreateMaterialV1200Response response = new CreateMaterialV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateMaterialV1200Response> toResMaterialResponse(APIMetadata metadata, Material data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResMaterialResponse(metadata, data);
    }

    final CreateMaterialV1200Response response = new CreateMaterialV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
