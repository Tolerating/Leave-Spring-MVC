package com.leave.dao;
import com.leave.model.Class;

import java.sql.SQLException;

public interface classDao {

    /**
     *  根据班级ID获取班级信息
     * @param classID 班级ID
     * @return Class
     */
    Class getClassInfoByClassID(String classID) throws SQLException;
}
