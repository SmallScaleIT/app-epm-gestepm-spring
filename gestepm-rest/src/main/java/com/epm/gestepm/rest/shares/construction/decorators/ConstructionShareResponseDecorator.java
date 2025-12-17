package com.epm.gestepm.rest.shares.construction.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.finder.ProjectByIdFinderDto;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByIdFinderDto;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.rest.project.mappers.MapPRToProjectResponse;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.ConstructionShare;
import com.epm.gestepm.restapi.openapi.model.Project;
import com.epm.gestepm.restapi.openapi.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static org.mapstruct.factory.Mappers.getMapper;

@Component("constructionShareResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class ConstructionShareResponseDecorator extends BaseResponseDataDecorator<ConstructionShare> {

    public static final String CS_U_EXPAND = "user";

    public static final String CS_P_EXPAND = "project";

    private final ProjectService projectService;

    private final UserService userService;

    public ConstructionShareResponseDecorator(ApplicationContext applicationContext, ProjectService projectService, UserService userService) {
        super(applicationContext);
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating construction share response",
            msgOut = "Construction share decorated OK",
            errorMsg = "Error decorating construction share response")
    public void decorate(RestRequest request, ConstructionShare data) {

        if (request.getLinks()) {

            final ConstructionShareFindRestRequest selfReq = new ConstructionShareFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(CS_U_EXPAND) && data.getUser() != null) {

            final User user = data.getUser();
            final Integer id = user.getId();

            final UserDto userDto = this.userService.findOrNotFound(new UserByIdFinderDto(id));
            final User response = new User().id(id).name(userDto.getFullName());

            data.setUser(response);
        }

        if (request.hasExpand(CS_P_EXPAND)) {

            final Project project = data.getProject();
            final Integer id = project.getId();

            final ProjectDto projectDto = this.projectService.findOrNotFound(new ProjectByIdFinderDto(id));
            final Project response = getMapper(MapPRToProjectResponse.class).from(projectDto);

            data.setProject(response);
        }
    }
}
