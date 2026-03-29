package com.epm.gestepm.model.projectmaterial.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.projectmaterial.dao.entity.creator.ProjectMaterialOptionalCreate;
import com.epm.gestepm.model.projectmaterial.dao.entity.deleter.ProjectMaterialOptionalDelete;
import com.epm.gestepm.model.projectmaterial.dao.entity.finder.ProjectMaterialByIdFinder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.projectmaterial.dao.constants.ProjectMaterialOptionalQueries.QRY_CREATE_PMO;
import static com.epm.gestepm.model.projectmaterial.dao.constants.ProjectMaterialOptionalQueries.QRY_DELETE_PMO;

@Component("projectMaterialOptionalDao")
@EnableExecutionLog(layerMarker = DAO)
public class ProjectMaterialOptionalDaoImpl implements ProjectMaterialOptionalDao {

    private final SQLDatasource sqlDatasource;

    public ProjectMaterialOptionalDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new project material optional",
            msgOut = "New project material optional persisted OK",
            errorMsg = "Failed to persist new project material optional")
    public void create(final ProjectMaterialOptionalCreate create) {

        final AttributeMap params = create.collectAttributes();

        final SQLQuery sqlInsert = new SQLQuery()
                .useQuery(QRY_CREATE_PMO)
                .withParams(params);

        this.sqlDatasource.execute(sqlInsert);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for project material optional",
            msgOut = "Delete for project material optional persisted OK",
            errorMsg = "Failed to persist delete for project material optional")
    public void delete(ProjectMaterialOptionalDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PMO)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }
}
