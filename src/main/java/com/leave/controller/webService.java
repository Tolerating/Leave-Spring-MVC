package com.leave.controller;

import com.leave.model.*;
import com.leave.service.AdminInfoService;

import com.leave.service.ClassService;
import com.leave.service.StudentsService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/leave")
public class webService {


    private AdminInfoService lgService;

    @Autowired
    public void setLgService(AdminInfoService lgService) {
        this.lgService = lgService;
    }

    @Autowired
    private StudentsService stuService;
    @Autowired
    private ClassService classService;

//    {
//        ApplicationContext atx = new ClassPathXmlApplicationContext("spring.xml");
//        this.lgService = (AdminInfoService)atx.getBean("adminInfoServiceImpl");
//        this.stuService = (StudentsService)atx.getBean("studentsServiceImpl");
//        this.classService = (ClassService)atx.getBean("classServiceImpl");
//    }

    @RequestMapping(value = "/loginLeave",method = RequestMethod.POST)
    @ResponseBody
    public String loginLeave(@RequestParam(name = "Name")String Name, @RequestParam(name = "Pwd")String Pwd,HttpServletRequest req){
        System.out.println(Name);
        System.out.println(Pwd);
        String result = "";
        AdminInfo model = lgService.loginLeave(Name,Pwd);
        JSONObject obj = JSONObject.fromObject(model);
        System.out.println(obj.toString());
        if (model.getAdminID() == 0)
        {
            result = "-1";
        }
        else
        {
            result = "1";
            HttpSession session = req.getSession(true);
            session.setAttribute("AdminInfo",model);
            AdminInfo ad = (AdminInfo)session.getAttribute("AdminInfo");
            System.out.println(ad.getAdminName());
        }
        return result;
    }

    /**
     *  忘记密码
     * @param studentNum 学号
     * @param req HttpServletRequest
     * @return String
     * @throws IOException
     */
    @RequestMapping(value = "/forgetPwd",method = RequestMethod.POST)
    @ResponseBody
    public String forgetPwd(@RequestParam(name = "studentNum")String studentNum,HttpServletRequest req) throws IOException{
        int code = (int)((Math.random()*9+1)*1000);
        System.out.println(code);
        HttpSession session = req.getSession(true);
        session.setAttribute("code",code);
        session.setAttribute("studentNum",studentNum);
        String msg = String.format("【温州科技职业学院】 您的验证码为:%d,有效时间为: 10分钟,请不要把验证码泄露给其他人。如非本人操作，可不用理会！",code);
        Students stu = stuService.getInfoByStudentNum(Integer.parseInt(studentNum));
        String phone = stu.getStudentTel();
        String result = "";
        if (!phone.equals("")){
            result = "1";

        }else{
            result = "-7";
        }
       return result;
    }

    /**
     * 检查验证码是否正确
     * @param Code 验证码
     * @param studentNum 学号
     * @param req HttpServletRequest
     * @return String
     * @throws IOException
     */
    @RequestMapping(value = "/checkCode",method = RequestMethod.POST)
    @ResponseBody
    public String checkCode(@RequestParam(name = "Code")String Code,@RequestParam(name = "studentNum")String studentNum,HttpServletRequest req) throws IOException{
        String reqCode = req.getParameter("Code");
        HttpSession session = req.getSession(true);
        String code = session.getAttribute("code").toString();
        String num = session.getAttribute("studentNum").toString();
        String result = "";
        if (!reqCode.equals(code))
        {
            result = "-1";
        }else if(!studentNum.equals(num)){
            result = "-2";
        }else{
            result = "1";
        }
        session.removeAttribute("code");
        session.removeAttribute("studentNum");
        return  result;
    }
    /**
     * 更新密码
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
    @ResponseBody
    public String updatePwd(HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
        String studentNum = req.getParameter("studentNum");
        String pwd = req.getParameter("passnew");
        String result = "-1";
        if (lgService.updatePassword(studentNum,pwd) > 0){
            if (stuService.updatePwdByAdminInfo(studentNum) > 0){
                result = "1";
            }
        }
        return result;
    }

    /**
     * 获取学生信息
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     */
    @RequestMapping(value = "/getStudentInfo",method = RequestMethod.GET)
    @ResponseBody
    public String getStudentInfo(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        AdminInfo admin = (AdminInfo)session.getAttribute("AdminInfo");
        System.out.println(admin.getAdminName());
        Students stu = stuService.getInfoByStudentNum(admin.getAdminLoginID());
        JSONObject obj = JSONObject.fromObject(stu);
        return obj.toString();
    }

    /**
     * 获取班级信息
     * @param classID 班级ID
     * @param req HttpServletRequest
     * @return String
     * @throws IOException
     * @throws SQLException
     */
    @RequestMapping(value = "/getClassInfo",method = RequestMethod.GET)
    @ResponseBody
    public String getClassInfo(@RequestParam(name = "classID")String classID,HttpServletRequest req) throws IOException, SQLException {
        com.leave.model.Class cla = classService.getClassInfoByClassID(classID);
        JSONObject obj = JSONObject.fromObject(cla);
       return obj.toString();
    }

    /**
     * 更新学生号码
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     */
    @RequestMapping(value = "/updateStudentTel",method = RequestMethod.POST)
    @ResponseBody
    public String updateStudentTel(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String stuTel = req.getParameter("StuTel");
        String stuNum = req.getParameter("StuNum");
        int result = stuService.updateStuTel(stuTel,stuNum);
        System.out.println(result);
        System.out.println(Integer.toString(result));
       return Integer.toString(result);
    }

    /**
     *  学生自查当天早出晚归情况
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "/selectAdvanceDelay",method = RequestMethod.POST)
    @ResponseBody
    public String selectAdvanceDelay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=utf-8");
        HttpSession session = req.getSession(true);
        AdminInfo admin = (AdminInfo)session.getAttribute("AdminInfo");
        Date date = new Date();
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
        String result = "";
        AdvanceDelay ad = new AdvanceDelay();
        System.out.println(sft.format(date));
        ad = stuService.selectAdvanceDelay(Integer.toString(admin.getAdminLoginID()),sft.format(date));
        if (ad == null){
            result = "3";
        }else {
            System.out.println(ad.getDelayStudentT());
            if (ad.getZcwgid() != 0) {
                if (!"".equals(ad.getAdvanceReson()) && "".equals(ad.getDeatReson())) {
                    //早出请假
                    result = String.format("1&%s&%s", ad.getAdvanceStudentT(), ad.getAdvanceReson());
                } else if ("".equals(ad.getAdvanceReson()) && !"".equals(ad.getDeatReson())) {
                    //晚归请假
                    result = String.format("2&%s&%s", ad.getDelayStudentT(), ad.getDeatReson());
                } else {
                    result = String.format("0&%s&%s&%s&%s", ad.getAdvanceStudentT(), ad.getAdvanceReson(), ad.getDelayStudentT(), ad.getDeatReson());
                }
            } else {
                result = "3";
            }
        }
       return result;

    }

    /**
     *  插入上课请假 , 早自习请假 , 不留宿请假
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "/insertLeaveRecord",method = RequestMethod.POST)
    @ResponseBody
    public String insertLeaveRecord(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        String data = req.getParameter("Data");
        String studentName = req.getParameter("StudentName");
        String teacherId = req.getParameter("TeacherID");
        String result = "-1";
        JSONObject jsonObject = JSONObject.fromObject(data);
        LeaveRecord lea = (LeaveRecord)JSONObject.toBean(jsonObject,LeaveRecord.class);
        System.out.println(lea);
        if (stuService.insertLeaveRecord(lea) == 1){
            Teachers tea = stuService.selectTeacherByNum(teacherId);
            if ("1".equals(lea.getLeaveRecordCategory()) && tea.getTeacherId() != 0)
            {
                result = tools.compStuContent(lea.getLeaveRecordCategory(), tea.getTeacherTel(), tea.getTeacherName(), studentName, lea.getLeaveRecordStudentId(), lea.getLeaveRecordReason(), lea.getLeaveRecordSumLesson(), lea.getLeaveRecordStartTime().toString(), lea.getLeaveRecordStartLesson(), lea.getLeaveRecordEndtTime().toString(), lea.getLeaveRecordEndLesson(), lea.getLeaveRecordNumDays());
            }
            else if ("2".equals(lea.getLeaveRecordCategory()) && tea.getTeacherId() != 0)
            {
                result = tools.compStuContent(lea.getLeaveRecordCategory(), tea.getTeacherTel(), tea.getTeacherName(), studentName, lea.getLeaveRecordStudentId(), lea.getLeaveRecordReason(), 0, lea.getLeaveRecordStartTime().toString(), 0, lea.getLeaveRecordEndtTime().toString(), 0, lea.getLeaveRecordNumDays());
            }
            else if ("3".equals(lea.getLeaveRecordCategory()) && tea.getTeacherId() != 0)
            {
                result = tools.compStuContent(lea.getLeaveRecordCategory(), tea.getTeacherTel(), tea.getTeacherName(), studentName, lea.getLeaveRecordStudentId(), lea.getLeaveRecordReason(), 0, lea.getLeaveRecordStartTime().toString(), 0, lea.getLeaveRecordEndtTime().toString(), 0, lea.getLeaveRecordNumDays());
            }
            else if ("4".equals(lea.getLeaveRecordCategory()) && tea.getTeacherId() != 0)
            {
                result = tools.compStuContent(lea.getLeaveRecordCategory(), tea.getTeacherTel(), tea.getTeacherName(), studentName, lea.getLeaveRecordStudentId(), lea.getLeaveRecordReason(), 0, lea.getLeaveRecordStartTime().toString(), 0, lea.getLeaveRecordEndtTime().toString(), 0, lea.getLeaveRecordNumDays());
            }
        }else{
            result = "-1";
        }
       return result;
    }

    /**
     * 插入周末请假
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "/InsertWeekDays",method = RequestMethod.POST)
    @ResponseBody
    public String InsertWeekDays(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        String data = req.getParameter("Data");
        JSONObject jsonObject = JSONObject.fromObject(data);
        WeekDays week = (WeekDays)JSONObject.toBean(jsonObject,WeekDays.class);
        int result = stuService.InsertWeekDays(week);
        return Integer.toString(result);
    }

    /**
     * 检查本周末是否请假
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "/checkWeekLeave",method = RequestMethod.POST)
    @ResponseBody
    public String checkWeekLeave(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        HttpSession session = req.getSession(true);
        int studentNum = ((AdminInfo)session.getAttribute("AdminInfo")).getAdminLoginID();
        String startTime = req.getParameter("starttime");
        String endTime = req.getParameter("endtime");
        int result = stuService.checkWeekLeave(startTime,endTime,Integer.toString(studentNum));
        return Integer.toString(result);
    }

    /**
     * 插入 + 更新早出晚归请假
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "/insertIntoAdvanceDelay",method = RequestMethod.POST)
    @ResponseBody
    public String insertIntoAdvanceDelay(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        String studentNum = req.getParameter("studentNum");
        String advanceReson = req.getParameter("advanceReson");
        String deatReson = req.getParameter("deatReson");
        String advanceStudentT = req.getParameter("advanceStudentT");
        String delayStudentT = req.getParameter("delayStudentT");
        String classNum = req.getParameter("classNum");
        String arriveCategory = req.getParameter("arriveCategory");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
        int numof = 0;
        AdvanceDelay ad = new AdvanceDelay();
        ad = stuService.selectAdvanceDelay(studentNum,sft.format(date));
        if (ad != null){
            numof = ad.getZcwgid();
        }
        ad.setClassNum(classNum);
        ad.setStudentNum(studentNum);
        if (arriveCategory == "晚归")
        {
            ad.setDelayTime(sdf.format(date));
            ad.setDeatReson(deatReson);
            ad.setDelayStudentT(delayStudentT);
        }
        else if (arriveCategory == "早出")
        {
            ad.setAdvanceReson(advanceReson);
            ad.setAdvanceTime(sdf.format(date));
            ad.setAdvanceStudentT(advanceStudentT);
        }
        else
        {
            ad.setDelayTime(sdf.format(date));
            ad.setDeatReson(deatReson);
            ad.setDelayStudentT(delayStudentT);
            ad.setAdvanceReson(advanceReson);
            ad.setAdvanceTime(sdf.format(date));
            ad.setAdvanceStudentT(advanceStudentT);
        }
        int numInto = 0;
        if (numof == 0)
        {
            numInto = stuService.insertIntoAdvanceDelay(ad);
        }
        else
        {
            numInto = stuService.updateAdvanceDelay(ad);
        }
        return Integer.toString(numInto);
    }
}
