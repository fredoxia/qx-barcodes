package com.onlineMIS.ORM.DAO.headQ.inventory;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineMIS.ORM.DAO.headQ.SQLServer.ClientDAOImpl;
import com.onlineMIS.ORM.entity.headQ.SQLServer.ClientsMS;

@Service
public class BasicInforService {
	/**
	 * client information in SQL server
	 */
	@Autowired
	private ClientDAOImpl clientImpl;
	
	/**
	 * to load clients information from db
	 * as the sql server allow the 0 foreign key this is a problem in creent system
	 * @return
	 */
	public List<ClientsMS> getClientsByNameLike(String name){
		DetachedCriteria criteria = DetachedCriteria.forClass(ClientsMS.class,"client");
		criteria.add(Restrictions.like("client.name", name,MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("client.deleted", false));
		List<ClientsMS> clients = clientImpl.getClientsByCriteria(criteria);
		return clients;
		
	}

	/**
	 * get the client information by id
	 * @param clientId
	 * @return
	 */
	public ClientsMS getClientById(int clientId) {
		return clientImpl.get(clientId, true);
	}
}
