package com.epm.gestepm.model.inspection.dao.mappers;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.inspection.dao.entity.InspectionFile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.nullableString;

public class InspectionFileRowMapper implements RowMapper<InspectionFile> {

    public static final String COL_IF_ID = "inspection_file_id";

    public static final String COL_IF_SHARE_ID = "inspection_id";

    public static final String COL_IF_NAME = "name";

    public static final String COL_IF_STORAGE_PATH = "storage_path";

    public static final String COL_IF_CONTENT = "content";

    @Override
    public InspectionFile mapRow(ResultSet rs, int i) throws SQLException {

        final InspectionFile inspectionFile = new InspectionFile();
        final byte[] bytes = FileUtils.decompressBytes(rs.getBytes(COL_IF_CONTENT));

        inspectionFile.setId(rs.getInt(COL_IF_ID));
        inspectionFile.setInspectionId(rs.getInt(COL_IF_SHARE_ID));
        inspectionFile.setName(rs.getString(COL_IF_NAME));
        inspectionFile.setContent(bytes != null ? bytes : rs.getBytes(COL_IF_CONTENT));
        inspectionFile.setStoragePath(nullableString(rs, COL_IF_STORAGE_PATH)); // FIXME: to remove

        return inspectionFile;
    }
}
