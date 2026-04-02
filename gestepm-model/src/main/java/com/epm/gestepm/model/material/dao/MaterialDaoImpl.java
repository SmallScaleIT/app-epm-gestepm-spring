package com.epm.gestepm.model.material.dao;

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
import com.epm.gestepm.model.material.dao.entity.Material;
import com.epm.gestepm.model.material.dao.entity.creator.MaterialCreate;
import com.epm.gestepm.model.material.dao.entity.deleter.MaterialDelete;
import com.epm.gestepm.model.material.dao.entity.filter.MaterialFilter;
import com.epm.gestepm.model.material.dao.entity.finder.MaterialByIdFinder;
import com.epm.gestepm.model.material.dao.entity.updater.MaterialUpdate;
import com.epm.gestepm.model.material.dao.mappers.MaterialRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.material.dao.constants.MaterialQueries.*;
import static com.epm.gestepm.model.material.dao.mappers.MaterialRowMapper.*;

@Component("materialDao")
@EnableExecutionLog(layerMarker = DAO)
public class MaterialDaoImpl implements MaterialDao {

    private final SQLDatasource sqlDatasource;

    public MaterialDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of teleworking signings",
            msgOut = "Querying list of teleworking signings OK",
            errorMsg = "Failed to query list of teleworking signings")
    public List<Material> list(MaterialFilter filter) {

        final SQLQueryFetchMany<Material> sqlQuery = new SQLQueryFetchMany<Material>()
                .useRowMapper(new MaterialRowMapper())
                .useQuery(QRY_LIST_OF_MAT)
                .useFilter(FILTER_MAT_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying page of teleworking signings",
            msgOut = "Querying page of teleworking signings OK",
            errorMsg = "Failed to query page of teleworking signings")
    public Page<Material> list(MaterialFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<Material> sqlQuery = new SQLQueryFetchPage<Material>()
                .useRowMapper(new MaterialRowMapper())
                .useQuery(QRY_PAGE_OF_MAT)
                .useCountQuery(QRY_COUNT_OF_MAT)
                .useFilter(FILTER_MAT_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find teleworking signing by ID",
            msgOut = "Querying to find teleworking signing by ID OK",
            errorMsg = "Failed query to find teleworking signing by ID")
    public Optional<Material> find(MaterialByIdFinder finder) {

        final SQLQueryFetchOne<Material> sqlQuery = new SQLQueryFetchOne<Material>()
                .useRowMapper(new MaterialRowMapper())
                .useQuery(QRY_LIST_OF_MAT)
                .useFilter(FILTER_MAT_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new teleworking signing",
            msgOut = "New teleworking signing persisted OK",
            errorMsg = "Failed to persist new teleworking signing")
    public Material create(MaterialCreate create) {

        final AttributeMap params = create.collectAttributes();

        final MaterialByIdFinder finder = new MaterialByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_MAT)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for teleworking signing",
            msgOut = "Update for teleworking signing persisted OK",
            errorMsg = "Failed to persist update for teleworking signing")
    public Material update(MaterialUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final MaterialByIdFinder finder = new MaterialByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_MAT)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for teleworking signing",
            msgOut = "Delete for teleworking signing persisted OK",
            errorMsg = "Failed to persist delete for teleworking signing")
    public void delete(MaterialDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_MAT)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<Material> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_MAT_NAME_ES;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.ASC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("nameEs".equals(orderBy)) {
            return COL_MAT_NAME_ES;
        } else if ("nameFr".equals(orderBy)) {
            return COL_MAT_NAME_FR;
        }
        return orderBy;
    }
}
