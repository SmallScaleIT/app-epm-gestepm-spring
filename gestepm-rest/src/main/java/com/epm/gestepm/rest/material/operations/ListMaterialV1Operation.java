package com.epm.gestepm.rest.material.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.material.request.MaterialListRestRequest;
import com.epm.gestepm.restapi.openapi.api.MaterialV1Api;

public class ListMaterialV1Operation extends APIOperation<MaterialV1Api, MaterialListRestRequest> {

    public ListMaterialV1Operation() {
        super("listMaterialV1");

        this.generateLinksWith((apiClass, req) -> apiClass.listMaterialsV1(req.getMeta(),
                req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(), req.getOrderBy(), req.getIds(), req.getNameContains(), req.getRequired()));
    }

}
