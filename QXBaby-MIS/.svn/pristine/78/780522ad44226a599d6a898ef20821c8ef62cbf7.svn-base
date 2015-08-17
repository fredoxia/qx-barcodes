package com.onlineMIS.ORM.DAO.chainS.user;


import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.common.Common_util;



@Repository
public class ChainUserInforDaoImpl extends BaseDAO<ChainUserInfor>{
	@Autowired
	private ChainRoleTypeDaoImpl chainRoleTypeDaoImpl;
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;

	/**
	 * get the active chain users in a chain store
	 * @param chainStoreId
	 * @return
	 */
	public List<ChainUserInfor> getActiveChainUsersByChainStore(int chainStoreId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainUserInfor.class);
		criteria.add(Restrictions.eq("myChainStore.chain_id", chainStoreId));
		criteria.add(Restrictions.eq("resign", ChainUserInfor.ACTIVE));
		
		return getByCritera(criteria, true);
	}
	
	public void initialize(ChainUserInfor chainUserInfor){
		chainRoleTypeDaoImpl.initialize(chainUserInfor.getRoleType());
		chainStoreDaoImpl.initialize(chainUserInfor.getMyChainStore());
	}
	
	public static ChainUserInfor getAllChainUserObject(){
		ChainUserInfor chainUserInfor = new ChainUserInfor();
		chainUserInfor.setUser_id(Common_util.ALL_RECORD);
		chainUserInfor.setName("所有人");
		chainUserInfor.setUser_name("所有人");
		return chainUserInfor;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Configuration configuration = new Configuration().configure();
//		
//		SessionFactory sFactory = configuration.buildSessionFactory();
//		
//		Session session = sFactory.openSession();
//		
//		Transaction transaction = session.beginTransaction();
//		
//		UserInfor userInfor = (UserInfor)session.get(UserInfor.class, 1);
//
////		Iterator<UserFunctionality> functions= userInfor.getUserFunction_Set().iterator();
////		
////		System.out.println(functions.hasNext());
//		
//		transaction.commit();
//		
//		session.close();
	}



}
