package com.epm.gestepm.rest.material;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.material.dto.MaterialDto;
import com.epm.gestepm.modelapi.material.dto.creator.MaterialCreateDto;
import com.epm.gestepm.modelapi.material.dto.deleter.MaterialDeleteDto;
import com.epm.gestepm.modelapi.material.dto.filter.MaterialFilterDto;
import com.epm.gestepm.modelapi.material.dto.finder.MaterialByIdFinderDto;
import com.epm.gestepm.modelapi.material.dto.updater.MaterialUpdateDto;
import com.epm.gestepm.modelapi.material.service.MaterialService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.material.decorators.MaterialResponseDecorator;
import com.epm.gestepm.rest.material.mappers.*;
import com.epm.gestepm.rest.material.operations.FindMaterialV1Operation;
import com.epm.gestepm.rest.material.operations.ListMaterialV1Operation;
import com.epm.gestepm.rest.material.request.MaterialFindRestRequest;
import com.epm.gestepm.rest.material.request.MaterialListRestRequest;
import com.epm.gestepm.rest.material.response.ResponsesForMaterial;
import com.epm.gestepm.rest.material.response.ResponsesForMaterialList;
import com.epm.gestepm.restapi.openapi.api.MaterialV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.material.security.MaterialPermission.PRMT_EDIT_MAT;
import static com.epm.gestepm.modelapi.material.security.MaterialPermission.PRMT_READ_MAT;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class MaterialController extends BaseController implements MaterialV1Api,
        ResponsesForMaterial, ResponsesForMaterialList {

    private final MaterialService personalExpenseSheetService;

    public MaterialController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                              final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                              final MaterialService personalExpenseSheetService) {
        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);
        this.personalExpenseSheetService = personalExpenseSheetService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_MAT, action = "Get material list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListMaterialsV1200Response> listMaterialsV1(final List<String> meta,
                                                                                    final Boolean links, final Set<String> expand,
                                                                                    final Long offset, final Long limit, final String order,
                                                                                    final String orderBy, final List<Integer> ids,
                                                                                    final String nameContains, final Boolean required) {
        final MaterialListRestRequest req = new MaterialListRestRequest(ids, nameContains, required);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final MaterialFilterDto filterDto = getMapper(MapMATToMaterialFilterDto.class).from(req);
        final Page<MaterialDto> page = this.personalExpenseSheetService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListMaterialV1Operation());
        final List<Material> data = getMapper(MapMATToMaterialResponse.class).from(page);

        this.decorate(req, data, MaterialResponseDecorator.class);

        return toListMaterialsV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_MAT, action = "Find material")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateMaterialV1200Response> findMaterialByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand) {

        final MaterialFindRestRequest req = new MaterialFindRestRequest(id);

        this.setCommon(req, meta, links, expand);

        final MaterialByIdFinderDto finderDto = getMapper(MapMATToMaterialByIdFinderDto.class).from(req);
        final MaterialDto dto = this.personalExpenseSheetService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindMaterialV1Operation());
        final Material data = getMapper(MapMATToMaterialResponse.class).from(dto);

        this.decorate(req, data, MaterialResponseDecorator.class);

        return toResMaterialResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_MAT, action = "Create material")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateMaterialV1200Response> createMaterialV1(final CreateMaterialV1Request request) {

        final MaterialCreateDto createDto = getMapper(MapMATToMaterialCreateDto.class).from(request);

        final MaterialDto personalExpenseSheet = this.personalExpenseSheetService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final Material data = getMapper(MapMATToMaterialResponse.class).from(personalExpenseSheet);

        final CreateMaterialV1200Response response = new CreateMaterialV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_MAT, action = "Update material")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateMaterialV1200Response> updateMaterialV1(final Integer id, final UpdateMaterialV1Request request) {

        final MaterialUpdateDto updateDto = getMapper(MapMATToMaterialUpdateDto.class).from(request);
        updateDto.setId(id);

        final MaterialDto personalExpenseSheet = this.personalExpenseSheetService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final Material data = getMapper(MapMATToMaterialResponse.class).from(personalExpenseSheet);

        final CreateMaterialV1200Response response = new CreateMaterialV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_MAT, action = "Delete material")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteMaterialV1(final Integer id) {

        final MaterialDeleteDto deleteDto = new MaterialDeleteDto(id);

        this.personalExpenseSheetService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }

}

