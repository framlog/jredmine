package com.unicorn.action;

import com.unicorn.Utils;
import com.unicorn.bean.UserInfo;
import com.unicorn.domain.User;
import com.unicorn.service.UserService;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;

/**
 * Created by Huxley on 6/29/15.
 */
@Namespace("/")
@Scope("prototype")
@ParentPackage("base")
public class UserAction extends BaseAction<User> {

    public static final String USER = "user";
    public static final String REDIRECTPATH = "redirectpath";

    public static final String LOGIN_FAILED_FLAG = "login_failed_flag";

    private User user;

    @Resource
    private UserService userService;

    public User getModel() {
        if (null == user) user = new User();
        return user;
    }

    @Action(value = "register", results = {
            @Result(name = SUCCESS, type = "redirect", location = "/index.jsp"),
            @Result(name = INPUT,  location = "/register.jsp")
    })
    public String register() {
        int result = userService.register(user);
        if (1 == result) return SUCCESS;
        else return INPUT;
    }

    @Action(value = "ajax_userinfo", results = @Result(type = "json"))
    public String ajaxUserInfo() throws Exception {
        JSONObject json = JSONObject.fromObject(null == Utils.getCurrentUser() ? new UserInfo() : new UserInfo(user));
        response.setContentType("text/json; charset=utf-8");
        response.getWriter().print(json);

        return null;
    }
}
