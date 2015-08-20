package com.onlineMIS.ORM.DAO.chainS.vip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.naming.java.javaURLContextFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainStoreConfDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainStoreGroupDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroup;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroupElement;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelTwoInventoryItem;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCardDownloadTemplate;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCardInforTemplate;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPPreaidFlow;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPScore;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPType;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.action.chainS.vip.ChainVIPActionFormBean;
import com.onlineMIS.action.chainS.vip.ChainVIPActionUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

@Service
public class ChainVIPService {
    @Autowired
	private ChainVIPCardImpl chainVIPCardImpl;
	@Autowired
	private ChainVIPTypeImpl chainVIPTypeImpl;
	@Autowired
	private ChainVIPScoreImpl chainVIPScoreImpl;
	
	@Autowired
	private ChainStoreService chainStoreService;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	@Autowired
	private ChainStoreGroupDaoImpl chainStoreGroupDaoImpl;
	@Autowired
	private ChainStoreConfDaoImpl chainStoreConfDaoImpl;
	@Autowired
	private ChainVIPPrepaidImpl chainVIPPrepaidImpl;
	
	/**
	 * function to get all chain vip types
	 * @return
	 */
	public List<ChainVIPType> getAllChainVIPTypes(){
		return chainVIPTypeImpl.getAll(true);
	}

	/**
	 * function to delete the vip type
	 * Response type:
	 * 1: in use
	 * 2: success
	 * 3: fail
	 * @param selectedVIPType
	 * @return
	 */
	public Response deleteVIPType(int vipTypeId) {
		Response response = new Response();
		
		//1. check whether the vip type is in use or not
		boolean inUse = checkVIPTypeInUse(vipTypeId);
		
		if (inUse){
			response.setReturnCode(Response.IN_USE);
		} else {
		    //2. it is ok to delete the vip card
			String hql = "delete from ChainVIPType where id =?";
			Object[] values = new Object[]{vipTypeId};
			
			int updateQuantity = chainVIPTypeImpl.executeHQLUpdateDelete(hql, values, true);
			if (updateQuantity == 1)
				response.setReturnCode(Response.SUCCESS);
			else
				response.setReturnCode(Response.FAIL);
		}
		
		return response;
	}

	/**
	 * to check whether the vip type is in use or not
	 * @param selectedVIPType
	 * @return
	 */
	private boolean checkVIPTypeInUse(int selectedVIPType) {
		String hql ="select count(vipCardNo) from ChainVIPCard where vipType.id =?";
		Object[] values = new Object[]{selectedVIPType};
		
		int count = chainVIPCardImpl.executeHQLCount(hql, values, false);
		
		if (count > 0)
		    return true;
		else 
			return false;
	}

	/**
	 * to get the vip type by Id
	 * @param id
	 * @return
	 */
	public ChainVIPType getVIPTypeById(int id) {
		
		return chainVIPTypeImpl.get(id, true);
	}

	/**
	 * update the ChainVIPType
	 * @param vipType
	 */
	public void saveOrupdateVIPType(ChainVIPType vipType) {
		chainVIPTypeImpl.saveOrUpdate(vipType, true);
		
	}
	
	/**
	 * update the chain vip card
	 * @param vipCard
	 */
	public void saveOrupdateVIPCard(ChainVIPCard vipCard) {
		double vipInitialSale = vipCard.getInitialValue();
		
		if (vipInitialSale != 0){
			ChainVIPType vipType = chainVIPTypeImpl.get(vipCard.getVipType().getId(), true);
			double coupon = vipInitialSale * vipType.getCouponRatio();
			vipCard.setInitialScore(coupon);
		}
		
		vipCard.setVipCardNo(vipCard.getVipCardNo().trim());
		
		String customerName = vipCard.getCustomerName();
		String pinyin = Common_util.getPinyinCode(customerName,false);
		vipCard.setPinyin(pinyin);

		chainVIPCardImpl.saveOrUpdate(vipCard, true);

	}

	/**
	 * to get the list of the vip cards
	 * @param loginUser
	 * @return
	 */
	public List<ChainVIPCard> getChainVIPCards(ChainUserInfor loginUser, Pager pager) {
		ChainVIPCard vipCard = new ChainVIPCard();
		
		vipCard.setStatus(Common_util.ALL_RECORD);
		
		if (!ChainUserInforService.isMgmtFromHQ(loginUser)){
			ChainStore chainStore = loginUser.getMyChainStore();

			vipCard.setIssueChainStore(chainStore);
		} else {
			ChainStore chainStore = new ChainStore();
			chainStore.setChain_id(Common_util.ALL_RECORD);
			vipCard.setIssueChainStore(chainStore);
		}
		
		return searchVIPCards(vipCard, pager);
	}

	/**
	 * to get the vip card information by card number
	 * @param vipCardNo
	 * @return
	 */
	public ChainVIPCard getVIPCardByCardNo(String vipCardNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainVIPCard.class);
		criteria.add(Restrictions.eq("vipCardNo", vipCardNo));
		criteria.add(Restrictions.ne("status", ChainVIPCard.STATUS_DELETE));
		
		
		List<ChainVIPCard> cards = chainVIPCardImpl.getByCritera(criteria, true);
		if (cards != null && cards.size() > 0)
			return cards.get(0);
		else 
			return null;


	}
	
	public ChainVIPCard getVIPCardByCardId(int cardId) {
		if (cardId == 0)
			return null;
		return chainVIPCardImpl.get(cardId, true);
	}

	
	
	/**
	 * 
	 * @return
	 */
	public Response validateChainVIPType(ChainVIPType vipType){
		Response response = new Response();
		response.setReturnCode(Response.SUCCESS);
		return response;
	}

	@Transactional
	public Response validateChainVIPCard(ChainVIPCard vipCard, ChainUserInfor userInfor) {
		Response response = new Response();

		Object[] values = new Object[]{vipCard.getId(), vipCard.getVipCardNo().trim()};
		String sql = "SELECT COUNT(c.id) FROM ChainVIPCard c WHERE c.id <> ? and c.vipCardNo =?";
		int count = chainVIPCardImpl.executeHQLCount(sql, values, false);
		
		if (count > 0){
			response.setReturnCode(Response.FAIL);
			response.setMessage("卡号 " + vipCard.getVipCardNo() + " 已经被使用");
		} else if (hasRightToUpdateVIPCard(vipCard.getId(), userInfor))
		    response.setReturnCode(Response.SUCCESS);
		  else {
			response.setReturnCode(Response.NO_AUTHORITY);
			response.setMessage("你没有权限修改其他连锁店VIP卡");
		}
		return response;
	}

	/**
	 * prepare the dropdowns for the add/save/update VIP Card UI
	 * @param uiBean
	 */
	public void prepareEditVIPCardUI(ChainVIPActionUIBean uiBean, ChainUserInfor loginUserInfor) {
		//1. card type
		uiBean.setChainVIPTypes(chainVIPTypeImpl.getAll(true));
		
		//2. card chain store
		uiBean.setChainStores(chainStoreService.getChainStoreList(loginUserInfor));
		
	}
	
	/**
	 * prepare the dropdowns for the search cards ui
	 * @param uiBean
	 * @param userInfor
	 */
	@Transactional
	public void prepareSearchVIPCardUI(ChainVIPActionUIBean uiBean,
			ChainUserInfor userInfor) {
		//prepare the dummy all record chain store
		List<ChainStore> chainStores = new ArrayList<ChainStore>();

		if (ChainUserInforService.isMgmtFromHQ(userInfor)){
			ChainStore store_dummy = new ChainStore();
			store_dummy.setChain_id(Common_util.ALL_RECORD);
			store_dummy.setChain_name("--所有连锁店--");
			chainStores.add(0, store_dummy);
			List<ChainStore> otherStores = chainStoreDaoImpl.getAll(true);
			chainStores.addAll(otherStores);
		} else {
			int myChainId = userInfor.getMyChainStore().getChain_id();
			chainStores = chainStoreGroupDaoImpl.getChainGroupStoreList(myChainId, userInfor, Common_util.CHAIN_ACCESS_LEVEL_3);
		}
		uiBean.setChainStores(chainStores);		
		uiBean.good =9999;
		
		//准备vip 种类
		List<ChainVIPType> vipTypes = chainVIPTypeImpl.getAll(true);
		uiBean.setChainVIPTypes(vipTypes);
	}

	/**
	 * to delete the vip card information
	 * 4: 不是当前连锁店的vip card
	 * @param vipCardNo
	 * @return
	 */
	@Transactional
	public Response deleteVIPCard(int cardId, ChainUserInfor userInfor) {
		Response response = new Response();
		
		//1. check whether the vip type is in use or not
		ChainVIPCard card = chainVIPCardImpl.get(cardId, true);
		if (card != null)
			response.setMessage(card.getVipCardNo());
		
		//2. to check whether the user has the right to delete this vip card
		if (!hasRightToUpdateVIPCard(cardId, userInfor)){
			response.setReturnCode(Response.NO_AUTHORITY);
		} else {
		    //3. it is ok to delete the vip card
			card.setStatus(ChainVIPCard.STATUS_DELETE);
			chainVIPCardImpl.update(card, true);
		}
			
		return response;
	}

	/**
	 * to check the vip card no is in use or not
	 * @param vipCardNo
	 * @return
	 */
	private boolean checkVIPCardInUse(String vipCardNo) {
		String hql ="select count(id) from ChainStoreSalesOrder where vipCard.vipCardNo =?";
		Object[] values = new Object[]{vipCardNo};
		
		int count = chainVIPCardImpl.executeHQLCount(hql, values, false);
		
		if (count > 0)
		    return true;
		else 
			return false;
	}
	
	/**
	 * 用户只能更新属于自己连锁店group的card
	 *
	 * @param vipCardNo
	 * @param userInfor
	 * @return
	 */
	private boolean hasRightToUpdateVIPCard(int cardId, ChainUserInfor userInfor){
		if (ChainUserInforService.isMgmtFromHQ(userInfor))
			return true;
		else{
			ChainVIPCard thisCard = chainVIPCardImpl.get(cardId, true);
			if (thisCard == null)
				return true;
			else if (thisCard.getIssueChainStore().getChain_id() == userInfor.getMyChainStore().getChain_id())
				return true;
			else {
				int myChainId = userInfor.getMyChainStore().getChain_id();
				
				ChainStoreGroup chainGroup = chainStoreGroupDaoImpl.getChainStoreBelongGroup(myChainId);
				if (chainGroup != null){
					int cardChainId = thisCard.getIssueChainStore().getChain_id();
					Set<ChainStoreGroupElement> chainGroupElements = chainGroup.getChainStoreGroupElementSet();
					if (chainGroupElements != null){
						for (ChainStoreGroupElement ele : chainGroupElements){
							int chainId = ele.getChainId();
							if (cardChainId == chainId)
								return true;
						}
					}
				} 
			}
		}
		
		return false;
			
	}
	
	/**
	 * sum of the score + initial value
	 * @param vipCard
	 * @return
	 */
	@Transactional
	public List<Double> getVIPCardTotalScore(int vipCardId){
		ChainVIPCard vipCard = chainVIPCardImpl.get(vipCardId, true);
		List<Double> results = null;
		if (vipCard != null ){
			//1. 积分和现金
			double initialScore = vipCard.getInitialScore();
			double accumulateScore = chainVIPScoreImpl.getVIPScoreSum(vipCardId);
			
			ChainStoreConf chainStoreConf = chainStoreConfDaoImpl.get(vipCard.getIssueChainStore().getChain_id(), true);
			double vipScoreCashRatio = Common_util.VIP_CASH_RATIO;
			if (chainStoreConf != null && chainStoreConf.getVipScoreCashRatio() > 0)
				vipScoreCashRatio = chainStoreConf.getVipScoreCashRatio();
			
			double avaibleCash = (initialScore + accumulateScore) * vipScoreCashRatio;
			
			//2. 预付款
			double totalVipPrepaid = getAcumulateVipPrepaid(vipCard);
	
			results = new ArrayList<Double>();
			results.add(initialScore + accumulateScore);
			results.add(avaibleCash);
			results.add(totalVipPrepaid);
		} 
		
		return results;
	}

	public double getAcumulateVipPrepaid(ChainVIPCard vipCard) {
		double totalPrepaid = 0;
		String hql = "SELECT c.operationType, sum(amount) FROM ChainVIPPreaidFlow c WHERE c.vipCard.id = ?";
	    Object[] values = new Object[]{vipCard.getId()};
	    List<Object> prepaid = chainVIPPrepaidImpl.executeHQLSelect(hql, values,null, true);
	    
	    if (prepaid != null && prepaid.size() > 0)
		  for (Object object: prepaid){
			  Object[] object2 = (Object[])object;
			  String operationType = object2[0].toString();
			  double amount = Common_util.getDouble(object2[1]);

			  if (operationType.equalsIgnoreCase(ChainVIPPreaidFlow.OPERATION_TYPE_CONSUMP))
				  totalPrepaid -= amount;
			  else if (operationType.equalsIgnoreCase(ChainVIPPreaidFlow.OPERATION_TYPE_DEPOSIT))
				  totalPrepaid += amount;
		   }
	    
		return totalPrepaid;
	}

	/**
	 * to change the vip card status
	 * @param status
	 */
	@Transactional
	public Response changeVIPCardStatus(int status, int cardId, ChainUserInfor userInfor) {
		Response response = new Response();
		
		ChainVIPCard card = chainVIPCardImpl.get(cardId, true);
		if (card != null)
			response.setMessage(card.getVipCardNo());
		
		if (hasRightToUpdateVIPCard(cardId, userInfor)){
			String hql ="update ChainVIPCard set status=? where id =?";
			Object[] values = new Object[]{status, cardId};
			
			int count = chainVIPCardImpl.executeHQLUpdateDelete(hql, values, true);
			
			if (count > 0)
			    response.setReturnCode(Response.SUCCESS);
			else 
				response.setReturnCode(Response.FAIL);
		} else 
			response.setReturnCode(Response.NO_AUTHORITY);
		
		return response;
	}

	/**
	 * search the vip cards
	 * @param vipCard
	 * @return
	 */
	@Transactional
	public List<ChainVIPCard> searchVIPCards(ChainVIPCard vipCard, Pager pager) {
		boolean cache = false;
		
		List<ChainVIPCard> chainVIPCards = new ArrayList<ChainVIPCard>();

		//1. check the pager
		DetachedCriteria criteria = buildSearchCriteria(vipCard);
		criteria.setProjection(Projections.rowCount());
		int totalRecord = Common_util.getProjectionSingleValue(chainVIPCardImpl.getByCriteriaProjection(criteria, false));
		
		if (pager.getTotalResult() == 0){
			pager.initialize(totalRecord);
		} else {
//			pager.setTotalResult(totalRecord);
			pager.refresh(totalRecord);
			cache = true;
		}

		DetachedCriteria searchCriteria = buildSearchCriteria(vipCard);
		chainVIPCards = chainVIPCardImpl.getByCritera(searchCriteria, pager.getFirstResult(), pager.getRecordPerPage(), cache);
		
		decorateVIPCard(chainVIPCards);
		
		return chainVIPCards;
	}
	
	/**
	 * to decorate the vip card information such as 
	 * 1. 积分
	 * 2. 最后消费日期
	 * @param vipCards
	 */
	private void decorateVIPCard(List<ChainVIPCard> vipCards){
		Map<Integer, List<Double>> sumValueMap = chainVIPScoreImpl.getVIPCardScore(vipCards);
		Map<Integer, Date> vipLastConsumpMap = chainVIPScoreImpl.getVIPCardLastConsump(vipCards);
		Date today = Common_util.getToday();
		
		for (ChainVIPCard vipCard : vipCards){
			int vipCardId = vipCard.getId();
			List<Double> sumValues = sumValueMap.get(vipCardId);
			if (sumValues != null){
				vipCard.setAccumulatedScore(sumValues.get(0) + vipCard.getInitialScore());
				vipCard.setConsumedScore(sumValues.get(1));
			} else 
				vipCard.setAccumulatedScore(vipCard.getInitialScore());
			
			Date lastConsumpDate = vipLastConsumpMap.get(vipCardId);
			if (lastConsumpDate != null){
				vipCard.setLastConsumpDate(lastConsumpDate);
				if (Common_util.getDateInterval(today, lastConsumpDate) > 180 )
					vipCard.setStatusConsump(ChainVIPCard.STATUS_CONSUMP_MORE_180);
			}
		}

	}
	
	/**
	 * to build the search criteria
	 * @param vipCard
	 * @return
	 */
	private DetachedCriteria buildSearchCriteria(ChainVIPCard vipCard){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainVIPCard.class);
		String cardNo = vipCard.getVipCardNo();
		if (cardNo != null && !cardNo.trim().equals("")){
			criteria.add(Restrictions.eq("vipCardNo", cardNo.trim()));
			criteria.add(Restrictions.ne("status", ChainVIPCard.STATUS_DELETE));
		} else {
			boolean blink = false;
			
			if (vipCard.getStatus() != Common_util.ALL_RECORD){
				criteria.add(Restrictions.eq("status", vipCard.getStatus()));
			} else {
				criteria.add(Restrictions.ne("status", ChainVIPCard.STATUS_DELETE));
			}
			
			if (vipCard.getVipType() != null){
				int cardType = vipCard.getVipType().getId();
				if (cardType != Common_util.ALL_RECORD){
					criteria.add(Restrictions.eq("vipType.id", cardType));
				}
			}
			
			String pinyin = vipCard.getPinyin();
			if (pinyin != null && !pinyin.trim().equals("")){
				criteria.add(Restrictions.like("pinyin", pinyin.trim(), MatchMode.START));
				blink = true;
			}
			
			String telephone = vipCard.getTelephone();
			if (telephone != null && !telephone.trim().equals("")){
				criteria.add(Restrictions.eq("telephone", telephone.trim()));
				blink = true;
			}
			
			int chainId = vipCard.getIssueChainStore().getChain_id();
			if (chainId != Common_util.ALL_RECORD){
				if (blink == false) {
					criteria.add(Restrictions.eq("issueChainStore.chain_id", vipCard.getIssueChainStore().getChain_id()));
				} else {
					List<ChainStore> chainStores = chainStoreGroupDaoImpl.getChainGroupStoreList(chainId, null, Common_util.CHAIN_ACCESS_LEVEL_3);
					if (chainStores == null || chainStores.size() == 0 || chainStores.size() ==1)
					    criteria.add(Restrictions.eq("issueChainStore.chain_id", vipCard.getIssueChainStore().getChain_id()));
					else {
						List<Integer> chainStoreIds = new ArrayList<Integer>();
						for (ChainStore store: chainStores)
							chainStoreIds.add(store.getChain_id());
						criteria.add(Restrictions.in("issueChainStore.chain_id", chainStoreIds));
					}
				}
			}
		}
		
		return criteria;
	}

	/**
	 * 批量上传vip 卡信息
	 * @param vips
	 * @param vipsFileName
	 * @return
	 * @throws IOException 
	 */
	public Response uploadVIPs(File vips, String vipsFileName, int chainId, boolean overWrite) throws IOException {
		Response response = new Response();
		String error = "";
		
		ChainStore chainStore = chainStoreDaoImpl.get(chainId, true);
		if (chainStore == null){
			response.setQuickValue(Response.ERROR, "无法找到对应连锁店信息");
			return response;
		} else {
			if (vipsFileName == null || vips == null){
				response.setQuickValue(Response.ERROR, "没有上传文件");
			} else {
				ChainVIPCardInforTemplate cardInforTemplate = new ChainVIPCardInforTemplate(chainStore, vips);
				List<ChainVIPCard> vipCards = null;
				
				try {
					vipCards = cardInforTemplate.process();
				} catch (Exception e) {
					loggerLocal.error(e);
					response.setQuickValue(Response.ERROR, "处理文件错误");
					return response;
				}

					for (ChainVIPCard vipCard : vipCards){
						try {
							int vipTypeId = vipCard.getVipType().getId();
							ChainVIPType vipType = chainVIPTypeImpl.get(vipTypeId, true);
							if (vipType != null) {
								if (overWrite)
									chainVIPCardImpl.saveOrUpdate(vipCard, true);
								else {
									String vipCardNo = vipCard.getVipCardNo();
									ChainVIPCard vipCard2 = getVIPCardByCardNo(vipCardNo);
									if (vipCard2 == null)
										chainVIPCardImpl.save(vipCard, true);
								}
							} else 
								error += vipCard.getVipCardNo() + ",";
					    } catch (Exception e) {
					    	e.printStackTrace();
						   error += vipCard.getVipCardNo() + ",";
					    }
					}

			}
		}
		
		if (!error.equals(""))
			response.setQuickValue(Response.WARNING, error);
		else 
			response.setQuickValue(Response.SUCCESS, "成功上传");
		
		return response;
	}

	/**
	 * 下载vip card信息
	 * @param chain_id
	 * @param string
	 * @return
	 */
	public Map<String, Object> downloadVIPs(ChainVIPCard vipCard, String templatePath) {
		Map<String,Object> returnMap=new HashMap<String, Object>(); 
		ChainVIPCard vipCard2 = new ChainVIPCard();
		vipCard2.setIssueChainStore(vipCard.getIssueChainStore());
		vipCard2.setStatus(Common_util.ALL_RECORD);
		
		/**
		 * 1. 下载vip的信息
		 */
		DetachedCriteria searchCriteria = buildSearchCriteria(vipCard2);
		List<ChainVIPCard> vipCards = chainVIPCardImpl.getByCritera(searchCriteria, true);
		
		/**
		 * 2. 下载vip积分信息
		 */
		Map<Integer, List<Double>> vipScoreMap = new HashMap<Integer, List<Double>>();
		
		/**
		 * 3. 下载最后消费日期
		 */
		Map<Integer, Date> vipLastConsumpMap = new HashMap<Integer, Date>();
		
		if (vipCards.size() < 10000){
			vipScoreMap = chainVIPScoreImpl.getVIPCardScore(vipCards);
			vipLastConsumpMap = chainVIPScoreImpl.getVIPCardLastConsump(vipCards);
		}
		
		ByteArrayInputStream byteArrayInputStream;   
		try {     
			HSSFWorkbook wb = null;   
	        ChainVIPCardDownloadTemplate chainVIPCardDownloadTemplate = new ChainVIPCardDownloadTemplate(vipCards, vipScoreMap,vipLastConsumpMap,templatePath);
	        wb = chainVIPCardDownloadTemplate.process();
	
			ByteArrayOutputStream os = new ByteArrayOutputStream();   
			try {   
			    wb.write(os);   
			} catch (IOException e) {   
				loggerLocal.error(e);
		    }   
		    byte[] content = os.toByteArray();   
		    byteArrayInputStream = new ByteArrayInputStream(content);   
		    returnMap.put("stream", byteArrayInputStream);   
	     
		    return returnMap;   
		 } catch (Exception ex) {   
			 loggerLocal.error(ex);
		 }  
		
		return null;
	}

	/**
	 * 搜索vip cards information
	 * @param chain_id
	 * @param searchType
	 * @return
	 */
	public Response searchSpecialVIPs(int chain_id, int searchType, int page, int rows) {
		Response response = new Response();
		Map data = new HashMap<String, Object>();
		
		//1. 获取row count总数
		DetachedCriteria criteria = buildSearchSpecialVIPsCritiera(chain_id, searchType);
		criteria.setProjection(Projections.rowCount());
		int totalRecord = Common_util.getProjectionSingleValue(chainVIPCardImpl.getByCriteriaProjection(criteria, false));
		
		//2. 搜索record
		DetachedCriteria criteria2 = buildSearchSpecialVIPsCritiera(chain_id, searchType);
		List<ChainVIPCard> cards = new ArrayList<ChainVIPCard>();
		
		if (page == -1 && rows == -1){
			cards = chainVIPCardImpl.getByCritera(criteria2, true);
		} else {
			cards = chainVIPCardImpl.getByCritera(criteria2, Common_util.getFirstRecord(page, rows), rows, true);
		}
		
		data.put("rows", cards);
		data.put("total", totalRecord);
		response.setReturnValue(data);
		response.setReturnCode(Response.SUCCESS);
		
		return response;
	}
	
	private DetachedCriteria buildSearchSpecialVIPsCritiera(int chain_id, int searchType){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainVIPCard.class);
		if (chain_id != Common_util.ALL_RECORD)
			criteria.add(Restrictions.eq("issueChainStore.chain_id", chain_id));
		
		if (searchType == ChainVIPActionUIBean.SEARCH_TYPE_BIR_TODAY){
			Date today = Common_util.getToday();
			int month = today.getMonth() + 1;
			int day = today.getDate();

			criteria.add(Restrictions.and(Restrictions.eq("birthMonth", month), Restrictions.eq("birthDay", day)));
		} else if (searchType == ChainVIPActionUIBean.SEARCH_TYPE_BIR_WEEK){
			List<java.sql.Date> dates = Common_util.getWeekDays(Common_util.getToday());
			
			int month = dates.get(0).getMonth() + 1;
			int day = dates.get(0).getDate();
			LogicalExpression expression = Restrictions.and(Restrictions.eq("birthMonth", month), Restrictions.eq("birthDay", day));
			
			for (int i = 1; i < dates.size(); i++){
				java.sql.Date date2 = dates.get(i);
				month = date2.getMonth() + 1;
				day = date2.getDate();
				expression = Restrictions.or(expression,Restrictions.and(Restrictions.eq("birthMonth", month), Restrictions.eq("birthDay", day)));
			}
			criteria.add(expression);
		} else if (searchType == ChainVIPActionUIBean.SEARCH_TYPE_BIR_MONTH){
			java.util.Date today = Common_util.getToday();
	
			int month = today.getMonth() + 1;
			criteria.add(Restrictions.eq("birthMonth", month));
		} 
		
		return criteria;		
	}

	public Response getVIPConsumptionHis(int chain_id, ChainVIPCard vipCard,
			java.util.Date startDate, java.util.Date endDate, int page,
			int rows, boolean needPage) {
		Response response = new Response();
		Map<String, Object> saleReport = new HashMap<String, Object>();
		List<ChainVIPScore> chainVIPScores = new ArrayList<ChainVIPScore>();
		vipCard = chainVIPCardImpl.get(vipCard.getId(), true);
		if (needPage){
			DetachedCriteria criteria = buildVipConsumptionHisCriteria(chain_id, vipCard, startDate, endDate);
			criteria.setProjection(Projections.rowCount());
			int totalRecord = Common_util.getProjectionSingleValue(chainVIPScoreImpl.getByCriteriaProjection(criteria, true));
			
			DetachedCriteria criteria2 = buildVipConsumptionHisCriteria(chain_id, vipCard, startDate, endDate);
			
			chainVIPScores = chainVIPScoreImpl.getByCritera(criteria2, Common_util.getFirstRecord(page, rows), rows, true);
			
			//saleReport.put("footer", footer);
			for (ChainVIPScore score : chainVIPScores)
				score.setVipCardNo(vipCard.getVipCardNo());
			saleReport.put("rows", chainVIPScores);
			saleReport.put("total", totalRecord);
		} else {
			DetachedCriteria criteria = buildVipConsumptionHisCriteria(chain_id, vipCard, startDate, endDate);

			chainVIPScores = chainVIPScoreImpl.getByCritera(criteria, true);
			
			for (ChainVIPScore score : chainVIPScores)
				score.setVipCardNo(vipCard.getVipCardNo());
			
			//saleReport.put("footer", footer);
			saleReport.put("rows", chainVIPScores);
		}
		double totalSales = 0;
		double totalCoupon = 0;
		for (ChainVIPScore vipScore : chainVIPScores){
			totalSales += vipScore.getSalesValue();
			totalCoupon += vipScore.getCoupon();
		}
		ChainVIPScore vipScore = new ChainVIPScore();
		vipScore.setCoupon(Common_util.roundDouble(totalCoupon,0));
		vipScore.setSalesValue(Common_util.roundDouble(totalSales,2));
		List<ChainVIPScore> chainVIPScoresFoot = new ArrayList<ChainVIPScore>();
		chainVIPScoresFoot.add(vipScore);
		saleReport.put("footer", chainVIPScoresFoot);
		
		response.setReturnCode(Response.SUCCESS);
		response.setReturnValue(saleReport);
		
		return response;
	}
	
	private DetachedCriteria buildVipConsumptionHisCriteria(int chain_id, ChainVIPCard vipCard,
			java.util.Date startDate, java.util.Date endDate){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainVIPScore.class);
		if (chain_id != Common_util.ALL_RECORD)
		    criteria.add(Restrictions.eq("chainId", chain_id));
		criteria.add(Restrictions.between("date", startDate, endDate));
		if (!ChainVIPCardImpl.isEmptyVIPCard(vipCard))
			criteria.add(Restrictions.eq("vipCardId", vipCard.getId()));
		criteria.addOrder(Order.asc("date"));
		
		return criteria;
	}

	/**
	 * 准备VIP升级UI
	 * @param uiBean
	 */
	public void prepareUpgradeVipUI(ChainVIPActionUIBean uiBean, ChainVIPActionFormBean formBean) {
		List<ChainVIPType> vipTypes = chainVIPTypeImpl.getAll(true);
		int vipCardId = formBean.getVipCard().getId();
		ChainVIPCard vipCard = chainVIPCardImpl.get(vipCardId, true);
		formBean.setVipCard(vipCard);
		
		List<ChainVIPType> vipTypes2 = new ArrayList<ChainVIPType>();
		for (ChainVIPType vipType : vipTypes){
			if (vipType.getId() != vipCard.getVipType().getId())
				vipTypes2.add(vipType);
		}
		
		uiBean.setChainVIPTypes(vipTypes2);
	}

	/**
	 * 升级vip　ｃａｒｄ
	 * @param vipCard
	 * @param vipScore
	 * @return
	 */
	@Transactional
	public Response upgradeVipCard(ChainVIPCard vipCard, int vipScore) {
		Response response = new Response();
		
		ChainVIPCard vipCard2 = chainVIPCardImpl.get(vipCard.getId(), true);
		vipCard2.setVipType(vipCard.getVipType());
		
		//1. 更新vip card 信息
		chainVIPCardImpl.update(vipCard2, true);
		
		//2. 更新vip score信息
		ChainStore chainStore = vipCard2.getIssueChainStore();
		ChainVIPScore chainVIPScore = new ChainVIPScore(chainStore.getChain_id(),vipCard2.getId(), ChainVIPScore.TYPE_UPGRADE,  0, 0, vipScore * -1);
		chainVIPScoreImpl.save(chainVIPScore, true);
		
		response.setReturnCode(Response.SUCCESS);
		
		return response;
	}

	/**
	 * 准备vip积分调整的UI
	 * @param formBean
	 */
	public void prepareUpdateVIPScoreUI(ChainVIPActionFormBean formBean) {
		int vipCardId = formBean.getVipCard().getId();
		ChainVIPCard card = chainVIPCardImpl.get(vipCardId, true);
		
		formBean.setVipCard(card);
	}

	public Response updateVipScore(ChainVIPCard vipCard, int vipScore, String comment) {
		Response response = new Response();
		
		//1. 获取vip card
		int vipCardId = vipCard.getId();
		ChainVIPCard vipCard2 = chainVIPCardImpl.get(vipCardId, true);

		//2. 更新vip score信息
		ChainStore chainStore = vipCard2.getIssueChainStore();
		ChainVIPScore chainVIPScore = new ChainVIPScore(chainStore.getChain_id(),vipCard2.getId(), ChainVIPScore.TYPE_MANUAL_ADJUST,  0, 0, vipScore);
		chainVIPScore.setComment(comment);
		chainVIPScoreImpl.save(chainVIPScore, true);
		
		response.setReturnCode(Response.SUCCESS);
		
		return response;
	}

	public void prepareDepositVIPPrepaidUI(ChainVIPActionFormBean formBean, ChainVIPActionUIBean uiBean,
			ChainUserInfor userInfor) {
		formBean.getVipPrepaid().clearData();
		formBean.setVipCard(null);

		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			ChainStore chainStore = chainStoreDaoImpl.get(userInfor.getMyChainStore().getChain_id(), true);
			formBean.setChainStore(chainStore);
			ChainStoreConf chainStoreConf = chainStoreConfDaoImpl.getChainStoreConfByChainId(formBean.getChainStore().getChain_id());
			uiBean.setChainStoreConf(chainStoreConf);
		} else 
			formBean.setChainStore(null);

		
		//the particular parameter
		ChainRoleType roleType = userInfor.getRoleType();
		formBean.getVipPrepaid().setDate(Common_util.getToday());
		if (ChainUserInforService.isMgmtFromHQ(userInfor) || roleType.isOwner())
			formBean.setCanEditOrderDate(true);
		else {
			formBean.setCanEditOrderDate(false);
		}
		
	}

	/**
	 * 在vip预付款页面获取vip卡信息
	 * @param vipCardNo
	 * @param chainStore
	 * @return
	 */
	public Response getVIPCardVIPPrepaid(String vipCardNo, ChainStore chainStore) {
		Response response = new Response();
		
		ChainVIPCard vipCard = getVIPCardByCardNo(vipCardNo);
		if (vipCard == null){
			response.setFail("错误 : VIP卡  " + vipCardNo + " 不存在");
		} else if (vipCard.getStatus() != ChainVIPCard.STATUS_GOOD) {
			response.setFail("错误: 此vip卡已经处于停用/挂失状态，请修改vip卡状态之后,再充值");
		} else if (vipCard.getIssueChainStore().getChain_id() != chainStore.getChain_id()){
			response.setFail("错误: 此vip卡的发卡连锁店不是当前连锁店.充值仅限于当前连锁店的客户.");
		} else {
			response.setReturnValue(vipCard);
		}
		
		return response;
	}

	/**
	 * 保存vip预付款
	 * @param chainStore
	 * @param vipCard
	 * @param vipPrepaid
	 * @return
	 */
	public Response saveVIPPrepaidDeposit(ChainStore chainStore, ChainVIPCard vipCard,
			ChainVIPPreaidFlow vipPrepaid, ChainUserInfor operator) {
		Response response = new Response();
		
		vipCard = getVIPCardByCardId(vipCard.getId());
		double amount = vipPrepaid.getAmount();
		
		if (amount <= 0) {
			response.setFail("错误 : 充值金额必须大于0");
		} else if (vipCard == null){
			response.setFail("错误 : VIP卡  " + vipCard.getVipCardNo() + " 不存在");
		} else if (vipCard.getStatus() != ChainVIPCard.STATUS_GOOD) {
			response.setFail("错误: 此vip卡已经处于停用/挂失状态，请修改vip卡状态之后,再充值");
		} else if (vipCard.getIssueChainStore().getChain_id() != chainStore.getChain_id()){
			response.setFail("错误: 此vip卡的发卡连锁店不是当前连锁店.充值仅限于当前连锁店的客户.");
		} else {
			//1. 第一步保存 prepaid
			chainStore = chainStoreDaoImpl.get(chainStore.getChain_id(), true);
			vipPrepaid.setChainStore(chainStore);
			vipPrepaid.setCreateDate(new java.util.Date());
			vipPrepaid.setDate(Common_util.getToday());
			vipPrepaid.setOperationType(ChainVIPPreaidFlow.OPERATION_TYPE_DEPOSIT);
			vipPrepaid.setOperator(operator);
			vipPrepaid.setVipCard(vipCard);
			chainVIPPrepaidImpl.save(vipPrepaid, true);
			
			//2. 第二步保存到vip score
            chainVIPScoreImpl.saveCascadingVIPPrepaid(vipPrepaid);
            
            //3. 获取vip总的剩余预存款
            double accumulateVipPrepaid = getAcumulateVipPrepaid(vipCard);
            vipPrepaid.setAccumulateVipPrepaid(accumulateVipPrepaid);
            
            response.setReturnValue(vipPrepaid);
            String msg = "成功为VIP " + vipCard.getVipCardNo() + " 充值" + Common_util.roundDouble(vipPrepaid.getAmount(), 0) +"元 \n";
            msg += "剩余预存款 :" + accumulateVipPrepaid + "元";
            response.setMessage(msg);
            
		}
		
		
		return response;
	}




}
