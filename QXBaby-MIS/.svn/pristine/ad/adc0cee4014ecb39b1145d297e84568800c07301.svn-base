package com.onlineMIS.ORM.DAO.headQ.SQLServer;


import java.util.List;

import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAOMS;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ColorGroups;

@Repository
public class ColorMSGroupsDAOImpl extends BaseDAOMS<ColorGroups> {
	
	public List<ColorGroups> getByColorGroupId(int colorGroupId){
		Object[] values = new Object[]{colorGroupId};
		
		String hql = "select colorGroups from ColorGroups colorGroups where colorGroupId = ? ";
		
		List<ColorGroups> colorGroups = null;

		colorGroups = getByHQL(hql, values, true);

		
		return colorGroups;
	}

}
