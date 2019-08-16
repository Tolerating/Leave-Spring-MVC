package com.leave.service.impl;
import com.leave.dao.AdminInfoDao;
import com.leave.model.AdminInfo;
import com.leave.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("adminInfoServiceImpl")
public class AdminInfoServiceImpl implements AdminInfoService {
    /**
     * 声明全局的 loginDaoImpl对象
     */
    private AdminInfoDao adminInfoDaoImpl;
    @Autowired
   public void adminInfoDaoImpl(AdminInfoDao adminInfoDao){
        this.adminInfoDaoImpl = adminInfoDao;
    }

    /**
     * 用户登录
     * @param name 账户
     * @param pwd 密码
     * @return AdminInfo
     */
    @Override
    public AdminInfo loginLeave(String name, String pwd) {
        return adminInfoDaoImpl.loginLeave(name,pwd);
    }

    /**
     * 更新密码
     * @param name 账户
     * @param pwd 密码
     * @return int
     */
    @Override
    public int updatePassword(String name, String pwd) throws SQLException { return adminInfoDaoImpl.updatePassword(name,pwd); }
}
