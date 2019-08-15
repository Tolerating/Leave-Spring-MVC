package com.leave.dao.impl;
import com.leave.controller.tools;
import com.leave.dao.classDao;
import com.leave.model.Class;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository("classDaoImpl")
public class classDaoImpl implements classDao {

    public Class SelectBySql(String sql) throws SQLException {
        Statement smt = null;
        ResultSet rs = null;
        Class cla = new Class();
        Connection conn = tools.ConnSql();
        try {
            smt = conn.createStatement();
            rs = smt.executeQuery(sql);
            while(rs.next()){
                cla.setClassId(rs.getLong("ClassId"));
                cla.setClassNum(rs.getString("ClassNum"));
                cla.setClassName(rs.getString("ClassName"));
                cla.setClassHeadTeacherId(rs.getLong("ClassHeadTeacherId"));
                cla.setClassSpecialityId(rs.getLong("ClassSpecialityId"));
                cla.setClassSpecialityTeacherId(rs.getLong("ClassSpecialityTeacherId"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rs.close();
            smt.close();
            conn.close();
        }
        return cla;
    }

    /**
     * 根据班级ID获取班级信息
     * @param classID 班级ID
     * @return Class
     * @throws SQLException
     */
    @Override
    public Class getClassInfoByClassID(String classID) throws SQLException {
        String sql = String.format("select * from [Class] where ClassNum='%s'",classID);
        return SelectBySql(sql);
    }
}
