package com.zcurd.common.interceptor;

import java.util.List;

import com.jfinal.aop.Duang;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.zcurd.model.SysUser;
import com.zcurd.service.LoginService;

/**
 * 用户权限数据拦截器，用于加载菜单权限。
 */
public class MenuAuthInterceptor implements Interceptor {
	@Override
	public void intercept(Invocation inv) {
		inv.invoke();
		Controller controller = inv.getController();
		Boolean authLoad = getAuthLoad(controller.getSessionAttr("authLoad"));
		SysUser sysUser = controller.getSessionAttr("sysUser");
		// 加载权限条件，标识符为true，有登录用户
		if (authLoad && null != sysUser) {
			LoginService loginService = Duang.duang(LoginService.class);
			// 用户菜单
			controller.setSessionAttr("menuList", loginService.getUserMenu(sysUser));
			// 页面权限
			List<String> noAuthUrl = loginService.getNoAuthUrl();
			controller.setSessionAttr("noAuthUrl", noAuthUrl);
			// 按钮权限
			controller.setSessionAttr("noAuthBtnUrl", loginService.getNoAuthBtnUrl(sysUser));
			// 数据权限
			controller.setSessionAttr("noAuthDatarule", loginService.getNoAuthDatarule(sysUser));
			if("admin".equals(sysUser.get("user_name"))) {
				controller.setSessionAttr("noAuthUrl", null);
				controller.setSessionAttr("noAuthBtnUrl", null);
				controller.setSessionAttr("pageBtnMap", null);
				controller.setSessionAttr("noAuthDatarule", null);
			}
			// 加载完毕，修改标识符
			controller.setSessionAttr("authLoad", false);
		}
	}
	
	/**
	 * 没有authLoad，说明是首次调用设置成true
	 * @param sessionAttr
	 * @return
	 */
	private Boolean getAuthLoad(Object sessionAttr) {
		return sessionAttr == null ? true : Boolean.parseBoolean(sessionAttr.toString());
	}

}
