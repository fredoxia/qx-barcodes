package com.onlineMIS.action.headQ.user;


import com.onlineMIS.ORM.entity.headQ.user.UserInfor;

public class UserActionFormBean {
	protected UserInfor userInfor = new UserInfor();

	protected String password2;
	protected String password3;


	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword3() {
		return password3;
	}

	public void setPassword3(String password3) {
		this.password3 = password3;
	}

	public UserInfor getUserInfor() {
		return userInfor;
	}

	public void setUserInfor(UserInfor userInfor) {
		this.userInfor = userInfor;
	}
}
