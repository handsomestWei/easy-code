package org.wjy.easycode.demo.config;

/**
 * @author weijiayu
 * @date 2023/3/6 14:30
 */

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

/**
 * PostgreSQL JSON类型参数处理器。使用时在String对象上添加注解@TableField(typeHandler = MybatisJsonbTypeHandler.class)
 * 
 * @author weijiayu
 * @date 2023/3/6 14:50
 */
@MappedTypes({Object.class})
public class MybatisJsonbTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
        throws SQLException {
        if (ps != null) {
            PGobject jsonObj = new PGobject();
            jsonObj.setType("jsonb");
            jsonObj.setValue(parameter.toString());
            ps.setObject(i, jsonObj);
        }
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getObject(s);
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getObject(i);
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getObject(i);
    }
}
