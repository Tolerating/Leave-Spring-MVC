package com.leave.service.impl;
import com.leave.dao.impl.classDaoImpl;
import com.leave.service.classService;
import com.leave.model.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("classServiceImpl")
public class classServiceImpl implements classService {
    @Autowired
    private classDaoImpl classDao;

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
