package com.zcurd.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.zcurd.common.interceptor.MenuAuthInterceptor;
import com.zcurd.common.util.PasswordUtil;
import com.zcurd.model.Menu;
import com.zcurd.model.SysUser;

/**
 * 登陆
 * @author 钟世云 2016.2.5
 */
public class LoginController extends BaseController {
	
	public void index() {
		render("login.html");
	}
	
	public void getMenu() {
		//admin用户拥有所有页面
		if("admin".equals(getSessionUser().get("user_name"))) {
			renderJson(Menu.me.findAll());
		}else {
			Object menuList = getSessionAttr("menuList");
			renderJson(menuList);
		}
	}
	
	@Before(MenuAuthInterceptor.class)
	public void login() {
		List<SysUser> list = SysUser.me.findByMultiProperties(new String[]{"user_name", "password"}, 
				new Object[]{getPara("user_name"), PasswordUtil.encodePassword(getPara("password"))});
		if(list.size() > 0) {
			setSessionAttr("sysUser", list.get(0));
			addOpLog("登陆系统");
			renderSuccess();
		}else {
			renderFailed("用户名或密码错误！");
		}
	}
	
	public void logout() {
		addOpLog("退出系统");
		removeSessionAttr("sysUser");
		redirect("/login");
	}
}
