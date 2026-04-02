package com.epm.gestepm.model.material.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.material.dao.entity.creator.MaterialInspectionCreate;
import com.epm.gestepm.model.material.dao.entity.deleter.MaterialInspectionDelete;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.material.dao.constants.MaterialInspectionQueries.QRY_CREATE_MATI;
import static com.epm.gestepm.model.material.dao.constants.MaterialInspectionQueries.QRY_DELETE_MATI;

@Component("materialInspectionDao")
@EnableExecutionLog(layerMarker = DAO)
public class MaterialInspectionDaoImpl implements MaterialInspectionDao {

    private final SQLDatasource sqlDatasource;

    public MaterialInspectionDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new project material optional",
            msgOut = "New project material optional persisted OK",
            errorMsg = "Failed to persist new project material optional")
    public void create(final MaterialInspectionCreate create) {

        final AttributeMap params = create.collectAttributes();

        final SQLQuery sqlInsert = new SQLQuery()
                .useQuery(QRY_CREATE_MATI)
                .withParams(params);

        this.sqlDatasource.execute(sqlInsert);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for project material optional",
            msgOut = "Delete for project material optional persisted OK",
            errorMsg = "Failed to persist delete for project material optional")
    public void delete(MaterialInspectionDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_MATI)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }
}
