package com.example.shiro;
import com.example.domain.Permission;
import com.example.domain.Role;
import com.example.domain.User;
import com.example.service.UserService;
import com.example.util.EncodeSalt;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户名
        String username = ((UsernamePasswordToken)principals.getPrimaryPrincipal()).getUsername();
        //根据用户名查询用户,获取用户的角色,给角色加权限,把权限设置到授权对象
        User user = userService.getUserByUsername(username);
        //构建对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //给用户关联角色
        Set<Role> roles = user.getRoles();
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()){
            Role role = iterator.next();
            //设置到shiro的对象 SimpleAuthorizationInfo 里面
            simpleAuthorizationInfo.addRole(role.getName());
            //给角色关联权限
            Set<Permission> permissions = role.getPermission();
            Iterator<Permission> iterator1 = permissions.iterator();
            while (iterator1.hasNext()){
                Permission permission = iterator1.next();
                //设置到shiro的对象 SimpleAuthorizationInfo 里面
                simpleAuthorizationInfo.addStringPermission(permission.getName());
            }
        }
        System.out.print("程序到达授权-->");
        return simpleAuthorizationInfo;
    }

    //登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //把参数强转为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取用户名
        String username = usernamePasswordToken.getUsername();
        //根据用户名查询
        User userFromDB = null;
        try{
            //从后端获取的用户
            userFromDB = userService.getUserByUsername(username);
            //判断对象是否为空
            if (userFromDB!=null){
                //使用随机数作为盐
                //SimpleByteSource byteSource = new SimpleByteSource(EncodeSalt.getFinalSalt());
                ByteSource bytes = ByteSource.Util.bytes(username);
                System.out.print("数据库的密码是"+userFromDB.getPassword());
                System.out.println("盐是"+bytes.toString());
                //构建对象
                SimpleAuthenticationInfo simpleAuthenticationInfo
                        = new SimpleAuthenticationInfo(usernamePasswordToken,userFromDB.getPassword(),bytes,getName());
//                //获取当前用户
               Subject subject = SecurityUtils.getSubject();
//                //绑定域对象
               subject.getSession().setAttribute("currentUser",usernamePasswordToken);
                System.out.print("程序到达认证-->");
                return simpleAuthenticationInfo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
