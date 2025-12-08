package com.epm.gestepm.model.shares.construction.dao.mappers;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShareFile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.nullableString;

public class ConstructionShareFileRowMapper implements RowMapper<ConstructionShareFile> {

    public static final String COL_CSF_ID = "construction_share_file_id";

    public static final String COL_CSF_SHARE_ID = "construction_share_id";

    public static final String COL_CSF_NAME = "name";

    public static final String COL_CSF_STORAGE_PATH = "storage_path";

    public static final String COL_CSF_CONTENT = "content";

    @Override
    public ConstructionShareFile mapRow(ResultSet rs, int i) throws SQLException {

        final ConstructionShareFile constructionShareFile = new ConstructionShareFile();

        constructionShareFile.setId(rs.getInt(COL_CSF_ID));
        constructionShareFile.setShareId(rs.getInt(COL_CSF_SHARE_ID));
        constructionShareFile.setName(rs.getString(COL_CSF_NAME));
        constructionShareFile.setContent(FileUtils.decompressBytes(rs.getBytes(COL_CSF_CONTENT)));
        constructionShareFile.setStoragePath(nullableString(rs, COL_CSF_STORAGE_PATH)); // FIXME: to remove

        return constructionShareFile;
    }
}
