package com.zcurd.common;

import java.util.Date;

import com.zcurd.model.SysUser;

public class ZcurdConst {
	
	/** 后台session user key **/
	public final static String ADMIN_SESSIOIN_USER_KEY = "sysUser";
	
	/**
	 * 系统约定字段-增加
	 */
	public static Object[][] getSystemDefField4Add(SysUser user) {
		return new Object[][] {
				{"sys_create_user_id", user.getId()}, 
				{"sys_create_user", user.getUserName()}, 
				{"sys_create_time", new Date()},
				
				{"sys_update_user_id", user.getId()}, 
				{"sys_update_user", user.getUserName()}, 
				{"sys_update_user_number", user.getUserName()}, 
				{"sys_update_time", new Date()}
		};
	}
	
	/**
	 * 系统约定字段-修改
	 */
	public static Object[][] getSystemDefField4Update(SysUser user) {
		return new Object[][] {
				{"sys_update_user_id", user.getId()}, 
				{"sys_update_user_number", user.getUserName()}, 
				{"sys_update_user", user.getUserName()}, 
				{"sys_update_time", new Date()}
		};
	}

}
