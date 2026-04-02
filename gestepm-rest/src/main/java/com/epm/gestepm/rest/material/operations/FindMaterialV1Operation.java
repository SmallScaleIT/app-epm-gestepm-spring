package com.epm.gestepm.rest.material.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.material.request.MaterialFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.MaterialV1Api;

public class FindMaterialV1Operation extends APIOperation<MaterialV1Api, MaterialFindRestRequest> {

    public FindMaterialV1Operation() {
        super("findMaterialV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findMaterialByIdV1(req.getId(), req.getMeta(), req.getLinks(), req.getExpand()));
    }

}
