package com.leave.dao;
import com.leave.model.AdminInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
public interface AdminInfoDao {
    /**
     * 用户登录
     * @param name 账户
     * @param pwd 密码
     * @return AdminInfo
     */
     AdminInfo loginLeave(@Param("name") String name, @Param("pwd")String pwd);

    /**
     * 更新密码
     * @param name 账户
     * @param pwd 密码
     * @return int
     */
     int updatePassword(@Param("name")String name, @Param("pwd")String pwd) throws SQLException;
}
