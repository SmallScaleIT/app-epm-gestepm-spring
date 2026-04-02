package com.epm.gestepm.rest.activitycenter.mappers;

import com.epm.gestepm.masterdata.api.activitycenter.dto.updater.ActivityCenterUpdateDto;
import com.epm.gestepm.restapi.openapi.model.CreateActivityCenterV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapACToActivityCenterUpdateDto {

  ActivityCenterUpdateDto from(CreateActivityCenterV1Request req);

}
