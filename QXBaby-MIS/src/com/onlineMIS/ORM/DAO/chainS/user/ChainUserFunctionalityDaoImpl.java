package com.onlineMIS.ORM.DAO.chainS.user;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserFunctionality;

@Repository
public class ChainUserFunctionalityDaoImpl extends  BaseDAO<ChainUserFunctionality>{
	public static final boolean cached = true;

	public void deleteFunctionsByRoleId(int roleTypeId) {
		String hql = "delete from ChainUserFunctionality u where u.chainRoleTypeId = ?";
		Object[] values = new Object[]{roleTypeId};
		
		this.executeHQLUpdateDelete(hql, values, true);
		
	}
	
}
