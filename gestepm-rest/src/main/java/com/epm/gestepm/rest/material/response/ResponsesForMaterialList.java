package com.epm.gestepm.rest.material.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ListMaterialsV1200Response;
import com.epm.gestepm.restapi.openapi.model.Material;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForMaterialList {

  default ResponseEntity<ListMaterialsV1200Response> toListMaterialsV1200Response(List<Material> data) {

    final ListMaterialsV1200Response response = new ListMaterialsV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListMaterialsV1200Response> toListMaterialsV1200Response(APIMetadata metadata, List<Material> data) {

    if (metadata == null) {
      return toListMaterialsV1200Response(data);
    }

    final ListMaterialsV1200Response response = new ListMaterialsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListMaterialsV1200Response> toListMaterialsV1200Response(APIMetadata metadata, List<Material> data,
      Object etag) {

    if (etag == null) {
      return toListMaterialsV1200Response(metadata, data);
    }

    final ListMaterialsV1200Response response = new ListMaterialsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
