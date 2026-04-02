package com.epm.gestepm.model.material.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.material.dao.entity.Material;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialRowMapper extends CommonRowMapper implements RowMapper<Material> {

    public static final String COL_MAT_ID = "material_id";

    public static final String COL_MAT_NAME_ES = "name_es";

    public static final String COL_MAT_NAME_FR = "name_fr";

    public static final String COL_MAT_REQUIRED = "required";

    @Override
    public Material mapRow(ResultSet resultSet, int i) throws SQLException {

        final Material material = new Material();

        material.setId(resultSet.getInt(COL_MAT_ID));
        material.setNameEs(resultSet.getString(COL_MAT_NAME_ES));
        material.setNameFr(resultSet.getString(COL_MAT_NAME_FR));
        material.setRequired(resultSet.getBoolean(COL_MAT_REQUIRED));

        return material;
    }
}
