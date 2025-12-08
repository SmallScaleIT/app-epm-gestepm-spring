package com.epm.gestepm.model.shares.work.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchPage;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareFileCreate;
import com.epm.gestepm.model.shares.work.dao.entity.WorkShare;
import com.epm.gestepm.model.shares.work.dao.entity.creator.WorkShareCreate;
import com.epm.gestepm.model.shares.work.dao.entity.creator.WorkShareFileCreate;
import com.epm.gestepm.model.shares.work.dao.entity.deleter.WorkShareDelete;
import com.epm.gestepm.model.shares.work.dao.entity.filter.WorkShareFilter;
import com.epm.gestepm.model.shares.work.dao.entity.finder.WorkShareByIdFinder;
import com.epm.gestepm.model.shares.work.dao.entity.updater.WorkShareUpdate;
import com.epm.gestepm.model.shares.work.dao.mappers.WorkShareRowMapper;
import com.epm.gestepm.storageapi.dto.FileResponse;
import com.epm.gestepm.storageapi.dto.creator.FileCreate;
import com.epm.gestepm.storageapi.service.GoogleCloudStorageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.shares.work.dao.constants.WorkShareQueries.*;
import static com.epm.gestepm.model.shares.work.dao.mappers.WorkShareRowMapper.*;

@RequiredArgsConstructor
@Component("workShareDao")
@EnableExecutionLog(layerMarker = DAO)
public class WorkShareDaoImpl implements WorkShareDao {

    private static final String PATH_FOLDER = "work-shares";
    
    private final GoogleCloudStorageService googleCloudStorageService;

    private final SQLDatasource sqlDatasource;
    
    private final WorkShareFileDao workShareFileDao;

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of work shares",
            msgOut = "Querying list of work shares OK",
            errorMsg = "Failed to query list of work shares")
    public List<WorkShare> list(WorkShareFilter filter) {

        final SQLQueryFetchMany<WorkShare> sqlQuery = new SQLQueryFetchMany<WorkShare>()
                .useRowMapper(new WorkShareRowMapper())
                .useQuery(QRY_LIST_OF_WS)
                .useFilter(FILTER_WS_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of work shares",
            msgOut = "Querying list of work shares OK",
            errorMsg = "Failed to query list of work shares")
    public Page<WorkShare> list(WorkShareFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<WorkShare> sqlQuery = new SQLQueryFetchPage<WorkShare>()
                .useRowMapper(new WorkShareRowMapper())
                .useQuery(QRY_PAGE_OF_WS)
                .useCountQuery(QRY_COUNT_OF_WS)
                .useFilter(FILTER_WS_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find work share by ID",
            msgOut = "Querying to find work share by ID OK",
            errorMsg = "Failed query to find work share by ID")
    public Optional<WorkShare> find(WorkShareByIdFinder finder) {

        final SQLQueryFetchOne<WorkShare> sqlQuery = new SQLQueryFetchOne<WorkShare>()
                .useRowMapper(new WorkShareRowMapper())
                .useQuery(QRY_LIST_OF_WS)
                .useFilter(FILTER_WS_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new work share",
            msgOut = "New work share persisted OK",
            errorMsg = "Failed to persist new work share")
    public WorkShare create(WorkShareCreate create) {

        final AttributeMap params = create.collectAttributes();

        final WorkShareByIdFinder finder = new WorkShareByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_WS)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for work share",
            msgOut = "Update for work share persisted OK",
            errorMsg = "Failed to persist update for work share")
    public WorkShare update(WorkShareUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final WorkShareByIdFinder finder = new WorkShareByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_WS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        if (CollectionUtils.isNotEmpty(update.getFiles())) {
            update.getFiles().forEach(file -> this.insertFiles(file, id));
        }

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for work share",
            msgOut = "Delete for work share persisted OK",
            errorMsg = "Failed to persist delete for work share")
    public void delete(WorkShareDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_WS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<WorkShare> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_WS_ID;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.DESC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("user.name".equals(orderBy)) {
            return COL_WS_U_USERNAME;
        } else if ("project.name".equals(orderBy)) {
            return COL_WS_P_NAME;
        } else if ("startDate".equals(orderBy)) {
            return COL_WS_START_DATE;
        } else if ("endDate".equals(orderBy)) {
            return COL_WS_END_DATE;
        }
        return orderBy;
    }

    private void insertFiles(final MultipartFile file, final Integer id) {
        final UUID storageUUID = UUID.randomUUID();

        final FileCreate fileCreate = new FileCreate();
        fileCreate.setName(PATH_FOLDER + "/" + storageUUID);
        fileCreate.setFile(file);

        final FileResponse fileResponse = this.googleCloudStorageService.uploadFile(fileCreate);

        final WorkShareFileCreate wsFileCreate = new WorkShareFileCreate();
        wsFileCreate.setShareId(id);
        wsFileCreate.setName(file.getOriginalFilename());
        wsFileCreate.setStoragePath(fileResponse.getFileName());

        this.workShareFileDao.create(wsFileCreate);
    }
}
