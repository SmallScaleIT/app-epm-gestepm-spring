package com.epm.gestepm.model.personalexpense.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchPage;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpenseFile;
import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseFileCreate;
import com.epm.gestepm.model.personalexpense.dao.entity.deleter.PersonalExpenseFileDelete;
import com.epm.gestepm.model.personalexpense.dao.entity.filter.PersonalExpenseFileFilter;
import com.epm.gestepm.model.personalexpense.dao.entity.finder.PersonalExpenseFileByIdFinder;
import com.epm.gestepm.model.personalexpense.dao.entity.updater.PersonalExpenseFileUpdate;
import com.epm.gestepm.model.personalexpense.dao.mappers.PersonalExpenseFileRowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseFileQueries.*;

@Component("personalExpenseFileDao")
@EnableExecutionLog(layerMarker = DAO)
public class PersonalExpenseFileDaoImpl implements PersonalExpenseFileDao {

    private final SQLDatasource sqlDatasource;

    public PersonalExpenseFileDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of personal expense files",
            msgOut = "Querying list of personal expense files OK",
            errorMsg = "Failed to query list of personal expense files")
    public List<PersonalExpenseFile> list(PersonalExpenseFileFilter filter) {

        final SQLQueryFetchMany<PersonalExpenseFile> sqlQuery = new SQLQueryFetchMany<PersonalExpenseFile>()
                .useRowMapper(new PersonalExpenseFileRowMapper())
                .useQuery(QRY_LIST_OF_PEF)
                .useFilter(FILTER_PEF_BY_PARAMS)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying page of personal expense files",
            msgOut = "Querying page of personal expense files OK",
            errorMsg = "Failed to query page of personal expense files")
    public Page<PersonalExpenseFile> list(PersonalExpenseFileFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<PersonalExpenseFile> sqlQuery = new SQLQueryFetchPage<PersonalExpenseFile>()
                .useRowMapper(new PersonalExpenseFileRowMapper())
                .useQuery(QRY_PAGE_OF_PEF)
                .useCountQuery(QRY_COUNT_OF_PEF)
                .useFilter(FILTER_PEF_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }
    
    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find personal expense file by ID",
            msgOut = "Querying to find personal expense file by ID OK",
            errorMsg = "Failed query to find personal expense file by ID")
    public Optional<PersonalExpenseFile> find(PersonalExpenseFileByIdFinder finder) {

        final SQLQueryFetchOne<PersonalExpenseFile> sqlQuery = new SQLQueryFetchOne<PersonalExpenseFile>()
                .useRowMapper(new PersonalExpenseFileRowMapper())
                .useQuery(QRY_LIST_OF_PEF)
                .useFilter(FILTER_PEF_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new personal expense file",
            msgOut = "New personal expense file persisted OK",
            errorMsg = "Failed to persist new personal expense file")
    public PersonalExpenseFile create(PersonalExpenseFileCreate create) {

        final AttributeMap params = create.collectAttributes();

        final PersonalExpenseFileByIdFinder finder = new PersonalExpenseFileByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_PEF)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting personal expense file",
            msgOut = "Personal expense file persisted OK",
            errorMsg = "Failed to persist personal expense file")
    public PersonalExpenseFile update(PersonalExpenseFileUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final PersonalExpenseFileByIdFinder finder = new PersonalExpenseFileByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_PEF)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for personal expense file",
            msgOut = "Delete for personal expense file persisted OK",
            errorMsg = "Failed to persist delete for personal expense file")
    public void delete(PersonalExpenseFileDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PEF)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }
}
