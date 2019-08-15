package com.leave.service.impl;
import com.leave.dao.impl.classDaoImpl;
import com.leave.service.classService;
import com.leave.model.Class;
import java.sql.SQLException;

public class classServiceImpl implements classService {
    classDaoImpl classDao = new classDaoImpl();

    /**
     * 根据班级ID获取班级信息
     * @param classID 班级ID
     * @return Class
     */
    @Override
    public Class getClassInfoByClassID(String classID) throws SQLException {
        return classDao.getClassInfoByClassID(classID);
    }
}
