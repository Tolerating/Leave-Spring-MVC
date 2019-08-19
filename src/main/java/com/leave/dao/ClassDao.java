package com.leave.dao;
import com.leave.model.Class;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
public interface ClassDao {

    /**
     *  根据班级ID获取班级信息
     * @param classID 班级ID
     * @return Class
     */
    Class getClassInfoByClassID(@Param("classID") String classID) throws SQLException;
}
