package com.onlineMIS.ORM.DAO.headQ.SQLServer;


import java.util.List;

import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAOMS;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ColorGroups;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.SizeGroups;

@Repository
public class SizeMSGroupsDAOImpl extends BaseDAOMS<SizeGroups> {
	
	public List<SizeGroups> getBySizeGroupId(int sizeGroupId){
		Object[] values = new Object[]{sizeGroupId};
		
		String hql = "from SizeGroups where sizeGroupId = ?";
		
		List<SizeGroups> sizeGroups = getByHQL(hql, values, true);
		
		return sizeGroups;
	}

}
