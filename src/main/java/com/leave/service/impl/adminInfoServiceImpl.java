package com.leave.service.impl;
import com.leave.dao.adminInfoDao;
import com.leave.dao.impl.adminInfoDaoImpl;
import com.leave.model.AdminInfo;
import com.leave.service.adminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("adminInfoServiceImpl")
public class adminInfoServiceImpl implements adminInfoService {
    /**
     * 声明全局的 loginDaoImpl对象
     */
    private adminInfoDao lgDao;
    @Autowired
   public void adminInfoDaoImpl(adminInfoDao adminInfoDao){
        this.lgDao = adminInfoDao;
    }
//    adminInfoDaoImpl lgDao = new adminInfoDaoImpl();

    /**
     * 用户登录
     * @param name 账户
     * @param pwd 密码
     * @return AdminInfo
     */
    @Override
    public AdminInfo loginLeave(String name, String pwd) {
        return lgDao.loginLeave(name,pwd);
    }

    /**
     * 更新密码
     * @param name 账户
     * @param pwd 密码
     * @return int
     */
    @Override
    public int updatePassword(String name, String pwd) throws SQLException { return lgDao.updatePassword(name,pwd); }
}
