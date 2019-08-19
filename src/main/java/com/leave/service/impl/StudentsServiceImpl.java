package com.leave.service.impl;
import com.leave.dao.StudentsDao;
import com.leave.model.*;
import com.leave.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("studentsServiceImpl")
public class StudentsServiceImpl implements StudentsService {


    private StudentsDao stuDao;

    @Autowired
    public void setStuDao(StudentsDao stu) {
        this.stuDao = stu;
    }

    /**
     * 学生自查当天早出晚归情况
     * @param studentNum 学号
     * @return AdvanceDelay
     */
    @Override
    public AdvanceDelay selectAdvanceDelay(String studentNum,String dateTimeNow) {
        return stuDao.selectAdvanceDelay(studentNum,dateTimeNow);
    }

    /**
     * 查询本周该学生是否未周六日请假
     * @param starttime 请假开始时间
     * @param endtime 请假结束时间
     * @param studentNum 学号
     * @return int
     */
    @Override
    public int checkWeekLeave(String starttime, String endtime, String studentNum) {
        return stuDao.checkWeekLeave(starttime,endtime,studentNum);
    }

    /**
     * 插入周末请假
     * @param model WeekDays
     * @return int
     */
    @Override
    public int InsertWeekDays(WeekDays model) {
        return stuDao.InsertWeekDays(model);
    }

    /**
     * 插入上课请假 , 早自习请假 , 不留宿请假
     * @param model LeaveRecord
     * @return int
     */
    @Override
    public int insertLeaveRecord(LeaveRecord model) {
        return stuDao.insertLeaveRecord(model);
    }

    /**
     * 插入早出晚归请假
     * @param model AdvanceDelay
     * @return int
     */
    @Override
    public int insertIntoAdvanceDelay(AdvanceDelay model) {
        return stuDao.insertIntoAdvanceDelay(model);
    }

    /**
     * 更新早出晚归记录
     * @param model AdvanceDelay
     * @return int
     */
    @Override
    public int updateAdvanceDelay(AdvanceDelay model) {
        return stuDao.updateAdvanceDelay(model);
    }

    /**
     * 通过学号获取学生信息
     * @param Num 学号
     * @return
     */
    @Override
    public Students getInfoByStudentNum(int Num) {
        return stuDao.getInfoByStudentNum(Num);
    }

    /**
     * 通过学号获取学生信息
     * @param Num 学号
     * @return int
     */
    @Override
    public int updatePwdByAdminInfo(String Num) throws SQLException {return stuDao.updatePwdByAdminInfo(Num);}

    /**
     *  更新学生电话
     * @param StuTel 电话
     * @param StuNum 学号
     * @return int
     */
    @Override
    public int updateStuTel(String StuTel, String StuNum) {
        return stuDao.updateStuTel(StuTel,StuNum);
    }

    /**
     *  通过教师工号获取教师信息
     * @param teacherNum 教师工号
     * @return Teachers
     */
    @Override
    public Teachers selectTeacherByNum(String teacherNum) {
        return stuDao.selectTeacherByNum(teacherNum);
    }
}
