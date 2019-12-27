package com.example.filter;
import com.example.util.MyJwtToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
@Component
public class JWTFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)){
            try {
                executeLogin(request,response);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.print("执行JWTFilter 的isAccessAllowed方法-->");
        return true;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        //优化返回方案
        //判断请求头是否携带token
        String token = req.getHeader("token");
        if (token==null||token.trim().equals("")){
            token = req.getParameter("token");
        }
        System.out.print("执行JWTFilter 的isLoginAttempt方法-->");
        return token!=null;
    }
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        //获取请求消息头信息或者路径信息    获取token
        String token = req.getHeader("token");
        if (token==null||token.trim()==""){
            token = req.getParameter("token");
        }
        //上述代码确保了token一定不为空
        /**
         * login 方法的参数是AuthenticationToken 对象
         * 两个办法:
         * 1)构建UsernamPasswordToken
         * 2)自定义一个token对象
         */
        MyJwtToken myJwtToken = new MyJwtToken();
        myJwtToken.setToken(token);
        getSubject(request, response).login(myJwtToken);
        System.out.print("执行JWTFilter 的executeLogin方法-->");
        return true;
    }

}
