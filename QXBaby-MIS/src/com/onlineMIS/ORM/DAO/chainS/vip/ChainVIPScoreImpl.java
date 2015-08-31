package com.onlineMIS.ORM.DAO.chainS.vip;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPPrepaidFlow;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPScore;
import com.onlineMIS.common.Common_util;

@Repository
public class ChainVIPScoreImpl extends BaseDAO<ChainVIPScore> {
	
		public double getVIPScoreSum(int vipCardId){
			Object[] values = new Object[]{vipCardId};
			String sql = "select sum(coupon) from ChainVIPScore where vipCardId = ?";
			List<Object> results = this.executeHQLSelect(sql, values,null, true);
			if (results == null || results.size() == 0)
				return 0;
			else 
				return Common_util.getDouble(results.get(0));
		}
		
		/**
		 * Map key : card number
		 * Map value: List<Accumulated value, consumed value>
		 * @param vipCardNos
		 * @return
		 */
		public Map<Integer, List<Double>> getVIPCardScore(List<ChainVIPCard> vipCardNos){
			Map<Integer, List<Double>> vipCardMap = new HashMap<Integer, List<Double>>();
			Map<Integer, Double> vipCardMap_accu = new HashMap<Integer, Double>();
			Map<Integer, Double> vipCardMap_consu = new HashMap<Integer, Double>();
			
			if (vipCardNos != null && vipCardNos.size() >0){
				String vipCardList = "(";
				for (ChainVIPCard vipCardNo : vipCardNos)
					vipCardList += "'" + vipCardNo.getId() + "',";
				vipCardList += "'" + vipCardNos.get(0).getId() + "')";
				
				String sql_accu = "select vipCardId, sum(coupon) from ChainVIPScore where vipCardId in "+ vipCardList +" and coupon > 0 group by vipCardId";
				String sql_consum = "select vipCardId, sum(coupon) from ChainVIPScore where vipCardId in "+ vipCardList +" and coupon < 0 group by vipCardId";
				
				List<Object> results_accu = this.executeHQLSelect(sql_accu, new Object[]{}, null,true);
				List<Object> results_consum = this.executeHQLSelect(sql_consum, new Object[]{},null, true);
				
				if (results_accu != null){
				    for (Object object : results_accu){
				    	Object[] oneRecord = (Object[])object;
				    	int vipCardId = (Integer)oneRecord[0];
				    	double coupons = (Double)oneRecord[1];
				    	vipCardMap_accu.put(vipCardId, coupons);
				    }
				} 
				if (results_consum != null){
				    for (Object object : results_consum){
				    	Object[] oneRecord = (Object[])object;
				    	Integer vipCardId = (Integer)oneRecord[0];
				    	double coupons = (Double)oneRecord[1];
				    	vipCardMap_consu.put(vipCardId, coupons);
				    }
				} 
				for (ChainVIPCard vipCard : vipCardNos){
					int vipCardId = vipCard.getId();
					Double accu = vipCardMap_accu.get(vipCardId);
					Double consu = vipCardMap_consu.get(vipCardId);
					List<Double> values = new ArrayList<Double>();
					if (accu != null)
					   values.add(accu);
					else 
						values.add(new Double(0));
					
					if (consu != null)
						values.add(Math.abs(consu));
					else 
					    values.add(new Double(0));					
					vipCardMap.put(vipCardId, values);
				}
				
				//System.out.println(vipCardMap);
			}
			
			return vipCardMap;
		}

		/**
		 * 获取vip卡的最后消费日期
		 * @param vipCardNos
		 * @return
		 */
		public Map<Integer, Date> getVIPCardLastConsump(
				List<ChainVIPCard> vipCardNos) {
			Map<Integer, Date> vipCardMap = new HashMap<Integer, Date>();

			if (vipCardNos != null && vipCardNos.size() >0){
				String vipCardList = "(";
				for (ChainVIPCard vipCardNo : vipCardNos)
					vipCardList += "'" + vipCardNo.getId() + "',";
				vipCardList += "'" + vipCardNos.get(0).getId() + "')";
				
				String sql = "select vipCardId, max(date) from ChainVIPScore where vipCardId in "+ vipCardList +" group by vipCardId";
				
				List<Object> results_accu = this.executeHQLSelect(sql, new Object[]{}, null,true);
				
				if (results_accu != null){
				    for (Object object : results_accu){
				    	Object[] oneRecord = (Object[])object;
				    	int vipCardId = (Integer)oneRecord[0];
				    	Date consumpDate = (Date)oneRecord[1];
				    	vipCardMap.put(vipCardId, consumpDate);
				    }
				} 

			}
			
			return vipCardMap;
		}

		/**
		 * 如果是预存就要记录
		 * @param vipPrepaid
		 */
		public void saveCascadingVIPPrepaid(ChainVIPPrepaidFlow vipPrepaid) {
			ChainVIPScore vipScore = new ChainVIPScore();
			vipScore.setChainId(vipPrepaid.getChainStore().getChain_id());
			String additionalComment = "";
			if (vipPrepaid.getStatus() == ChainVIPPrepaidFlow.STATUS_CANCEL)
				additionalComment = "红冲";
			
			if (vipPrepaid.getDepositType().equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_CARD))
				additionalComment += "预存刷卡 ";
			else if (vipPrepaid.getDepositType().equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_CASH))
				additionalComment += "预存现金 ";
			vipScore.setComment(additionalComment + vipPrepaid.getComment());
			vipScore.setDate(vipPrepaid.getCreateDate());
			vipScore.setOrderId(vipPrepaid.getId());
			vipScore.setSalesValue(vipPrepaid.getAmount());
			vipScore.setVipCardId(vipPrepaid.getVipCard().getId());
			vipScore.setVipCardNo(vipPrepaid.getVipCard().getVipCardNo());
			vipScore.setType(ChainVIPScore.TYPE_DEPOSIT);
			save(vipScore, true);
			
		}
}
