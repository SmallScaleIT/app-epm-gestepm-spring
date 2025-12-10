package com.epm.gestepm.model.shares.programmed.dao.mappers;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShareFile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.nullableString;

public class ProgrammedShareFileRowMapper implements RowMapper<ProgrammedShareFile> {

  public static final String COL_PSF_ID = "programmed_share_file_id";

  public static final String COL_PSF_SHARE_ID = "programmed_share_id";

  public static final String COL_PSF_NAME = "name";

  public static final String COL_PSF_STORAGE_PATH = "storage_path";

  public static final String COL_PSF_CONTENT = "content";

  @Override
  public ProgrammedShareFile mapRow(ResultSet rs, int i) throws SQLException {

    final ProgrammedShareFile programmedShareFile = new ProgrammedShareFile();
    final byte[] bytes = FileUtils.decompressBytes(rs.getBytes(COL_PSF_CONTENT));

    programmedShareFile.setId(rs.getInt(COL_PSF_ID));
    programmedShareFile.setShareId(rs.getInt(COL_PSF_SHARE_ID));
    programmedShareFile.setName(rs.getString(COL_PSF_NAME));
    programmedShareFile.setContent(bytes != null ? bytes : rs.getBytes(COL_PSF_CONTENT));
    programmedShareFile.setStoragePath(nullableString(rs, COL_PSF_STORAGE_PATH)); // FIXME: to remove

    return programmedShareFile;
  }
}
