package com.example.interceptor;

import com.example.common.annotation.Auth;
import com.example.sysuser.bean.SysAuth;
import com.example.sysuser.bean.SysUser;
import com.example.sysuser.constant.SessionConstant;
import com.example.utils.UserUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志拦截器
 */
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //静态资源放行
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        response.setCharacterEncoding("utf-8");
        // 查看该方法是否可以直接放行
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        SysUser user = UserUtils.getUser(request);
        if (user != null) {
            HttpSession session = request.getSession();

            String uri = request.getRequestURI();
            uri = uri.replace(request.getContextPath(), "");
            // 去除根路径
            if (uri.startsWith("/")) {
                uri = uri.substring(1);
            }

            Map<String, SysAuth> href = (Map<String, SysAuth>) session.getAttribute(SessionConstant.authAllByHref);
            Map<Integer, SysAuth> sysAuthMap = (Map<Integer, SysAuth>) session.getAttribute(SessionConstant.authAllById);

            //查找是否有权限
            SysAuth sysAuth = href.get(uri);
            Auth auth = method.getAnnotation(Auth.class);
            if (sysAuth == null && auth != null && auth.value().length > 0) {
                for (String s : auth.value()) {
                    sysAuth = href.get(s);
                    if (sysAuth != null) {
                        break;
                    }
                }

            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
