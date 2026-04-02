package com.epm.gestepm.rest.material.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.rest.material.request.MaterialFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.Material;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;

@Component("MaterialResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class MaterialResponseDecorator extends BaseResponseDataDecorator<Material> {
    
    public MaterialResponseDecorator(ApplicationContext applicationContext, UserServiceOld userServiceOld, ProjectService projectService) {  // , PersonalExpenseService personalExpenseService) {
        super(applicationContext);
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating project material response",
            msgOut = "Project material decorated OK",
            errorMsg = "Error decorating project material response")
    public void decorate(RestRequest request, Material data) {

        if (request.getLinks()) {

            final MaterialFindRestRequest selfReq = new MaterialFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }
    }
}
