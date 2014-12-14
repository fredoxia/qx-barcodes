package com.onlineMIS.ORM.DAO.chainS.vip;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPPrepaidFlow;
import com.onlineMIS.common.Common_util;

@Repository
public class ChainVIPPrepaidImpl extends BaseDAO<ChainVIPPrepaidFlow> {

	public Map<Integer, Double> getVIPPrepaidAccumulateMap(
			List<ChainVIPCard> vipCardNos) {
		Map<Integer, Double> vipPrepaidMap = new HashMap<Integer, Double>();

		if (vipCardNos != null && vipCardNos.size() >0){
			String vipCardList = "(";
			for (ChainVIPCard vipCardNo : vipCardNos)
				vipCardList += "'" + vipCardNo.getId() + "',";
			vipCardList += "'" + vipCardNos.get(0).getId() + "')";
			
			String sql = "select vipCard.id, sum(calculatedAmt) from ChainVIPPrepaidFlow where status= " + ChainVIPPrepaidFlow.STATUS_SUCCESS + " AND vipCard.id in "+ vipCardList +" group by vipCard.id";
			
			List<Object> results_accu = this.executeHQLSelect(sql, new Object[]{}, null,true);
			
			if (results_accu != null){
			    for (Object object : results_accu){
			    	Object[] oneRecord = (Object[])object;
			    	int vipCardId = (Integer)oneRecord[0];
			    	Double amount = Common_util.getDouble(oneRecord[1]);
			    	vipPrepaidMap.put(vipCardId, amount);
			    }
			} 
		}
		
		return vipPrepaidMap;
	}

}
