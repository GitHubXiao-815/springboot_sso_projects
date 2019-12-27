package com.example.action;

import com.example.domain.ResponseObj;
import com.example.domain.User;
import com.example.service.UserService;
import com.example.util.TokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAction {
    @PostMapping ("/login")
    public ResponseObj login(@RequestBody User user) {
        System.out.print("程序到达action-->");
        //获取当前会话对象
        Subject subject = SecurityUtils.getSubject();
        //构建返回信息的对象(允许Spring管理)
        ResponseObj responseObj = new ResponseObj();
        //获取域对象的值currentUser
        Object currentUser = subject.getSession().getAttribute("currentUser");
        if (currentUser != null) {
            responseObj.setCode(200);
            responseObj.getData().put("usernamePasswordToken", currentUser);
            return responseObj;
        }
        //构建UsernamePasswordToken对象
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        System.out.println(usernamePasswordToken);
        //执行登录
        subject.login(usernamePasswordToken);
        //判断是否登录成功
        if (subject.isAuthenticated()) {
            System.out.println("认证成功");
            //将用户放入session域中
            subject.getSession().setAttribute("currentuser", usernamePasswordToken);
            String token = TokenUtil.createToken(user.getUsername(), user.getPassword());
            System.out.println("生成的token为"+token);
            responseObj.setMessage(token);
            responseObj.setCode(200);
            responseObj.getData().put("usernamePasswordToken", usernamePasswordToken);
            return responseObj;
        }
        return null;
    }



    //添加注解指定角色
//    @RequiresRoles({"管理员","超级管理员"})
//    //指定权限
//    @RequiresPermissions("更新")
//    @PostMapping("/changePassword")
//    public String changePassword(String oldPassword,String newPassword) throws Exception{
//        user.setId(id);
//        user.setPassword(password);
//        user.setUsername(username);
//        userService.addUser(user);
//        return "success";
//        return null;
//    }
}
