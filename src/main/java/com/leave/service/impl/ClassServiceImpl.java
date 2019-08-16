package com.leave.service.impl;
import com.leave.dao.ClassDao;
import com.leave.service.ClassService;
import com.leave.model.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("classServiceImpl")
public class ClassServiceImpl implements ClassService {

    private ClassDao classDao;

    @Autowired
    public void setClassDao(ClassDao classDao) {
        this.classDao = classDao;
    }

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
