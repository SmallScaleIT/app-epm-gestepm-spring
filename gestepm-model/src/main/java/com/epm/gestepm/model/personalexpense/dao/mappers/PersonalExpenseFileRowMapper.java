package com.epm.gestepm.model.personalexpense.dao.mappers;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpenseFile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.nullableString;

public class PersonalExpenseFileRowMapper implements RowMapper<PersonalExpenseFile> {

    public static final String COL_PEF_ID = "personal_expense_file_id";

    public static final String COL_PEF_SHARE_ID = "personal_expense_id";

    public static final String COL_PEF_NAME = "name";

    public static final String COL_PEF_STORAGE_PATH = "storage_path";

    public static final String COL_PEF_CONTENT = "content";

    @Override
    public PersonalExpenseFile mapRow(ResultSet rs, int i) throws SQLException {

        final PersonalExpenseFile personalExpenseFile = new PersonalExpenseFile();
        final byte[] bytes = FileUtils.decompressBytes(rs.getBytes(COL_PEF_CONTENT));

        personalExpenseFile.setId(rs.getInt(COL_PEF_ID));
        personalExpenseFile.setPersonalExpenseId(rs.getInt(COL_PEF_SHARE_ID));
        personalExpenseFile.setName(rs.getString(COL_PEF_NAME));
        personalExpenseFile.setContent(bytes != null ? bytes : rs.getBytes(COL_PEF_CONTENT));
        personalExpenseFile.setStoragePath(nullableString(rs, COL_PEF_STORAGE_PATH)); // FIXME: to remove

        return personalExpenseFile;
    }
}
