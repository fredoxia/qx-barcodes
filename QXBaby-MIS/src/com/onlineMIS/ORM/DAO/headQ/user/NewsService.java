package com.onlineMIS.ORM.DAO.headQ.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onlineMIS.ORM.entity.headQ.user.News;

@Service
public class NewsService {
	
	@Autowired
	private NewsDaoImpl newsDaoImpl;
	
	/**
	 * to get the news by level
	 * @param newsLevel
	 * @return
	 */
	public List<News> getNews(int type){
		List<Integer> types = new ArrayList<Integer>();
		types.add(type);
		types.add(News.TYPE_ALL);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class);
		criteria.add(Restrictions.in("type", types));
		
		return newsDaoImpl.getByCritera(criteria, true);
	}

}
