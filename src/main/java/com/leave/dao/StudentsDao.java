package com.leave.dao;
import com.leave.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
public interface StudentsDao {
    /**
     * 通过学号获取学生信息
     * @param Num 学号
     * @return Students
     */
    Students getInfoByStudentNum(int Num);

    /**
     * 根据AdminInfo表更新密码
     * @param Num 学号
     * @return int
     */
    int updatePwdByAdminInfo(String Num) throws SQLException;

    /**
     *  更新学生电话
     * @param StuTel 电话
     * @param StuNum 学号
     * @return int
     */
    int updateStuTel(@Param("stuTel") String StuTel, @Param("stuNum")String StuNum);

    /**
     * 学生自查当天早出晚归情况
     * @param studentNum 学号
     * @return AdvanceDelay
     */
    AdvanceDelay selectAdvanceDelay(@Param("stuNum") String studentNum,@Param("datetime") String dateTimeNow);

    /**
     * 查询本周该学生是否未周六日请假
     * @param starttime 请假开始时间
     * @param endtime 请假结束时间
     * @return int
     */
    int checkWeekLeave(@Param("starttime") String starttime, @Param(("endtime")) String endtime, @Param("stuNum") String studentNum);

    /**
     * 插入周末请假
     * @param model WeekDays
     * @return int
     */
    int InsertWeekDays(WeekDays model);

    /**
     * 插入上课请假 , 早自习请假 , 不留宿请假
     * @param model LeaveRecord
     * @return int
     */
    int insertLeaveRecord(LeaveRecord model);

    /**
     * 插入早出晚归请假
     * @param model AdvanceDelay
     * @return int
     */
    int insertIntoAdvanceDelay(AdvanceDelay model);

    /**
     * 更新早出晚归记录
     * @param model AdvanceDelay
     * @return int
     */
    int updateAdvanceDelay(AdvanceDelay model);

    /**
     *  通过教师工号获取教师信息
     * @param teacherNum 教师工号
     * @return Teachers
     */
    Teachers selectTeacherByNum(String teacherNum);
}
