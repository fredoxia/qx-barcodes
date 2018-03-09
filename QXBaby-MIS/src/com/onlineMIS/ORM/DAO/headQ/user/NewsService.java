package com.onlineMIS.ORM.DAO.headQ.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineMIS.ORM.DAO.Response;
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
		criteria.addOrder(Order.desc("id"));
		
		return newsDaoImpl.getByCritera(criteria, true);
	}

	/**
	 * 获取所有的News
	 * @return
	 */
	public Response getAllNews() {
		Response response = new Response();
		Map dataMap = new HashMap<String, Object>();
		
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(News.class);
			criteria.addOrder(Order.desc("id"));
			List<News> news = newsDaoImpl.getByCritera(criteria, true);
			dataMap.put("rows", news);
			response.setReturnValue(dataMap);
			
			return response;
		} catch (Exception e){
			response.setFail(e.getMessage());
			return response;
		}
	}

	public Response saveNews(News news) {
		Response response = new Response();
		
		int id = news.getId();
		
		if (id == 0){
			DetachedCriteria criteria = DetachedCriteria.forClass(News.class);
			List<News> allNews = newsDaoImpl.getByCritera(criteria, true);
			
			if (allNews.size() >= 4)
				response.setFail("当前已经达到最多的4条新闻了,无法继续添加");
			else {
				newsDaoImpl.save(news, true);
			}
		} else {
		    newsDaoImpl.update(news, true);
		}
		
		return response;
	}

	public Response deleteNews(News news) {
		Response response = new Response();
		
		int id = news.getId();
		news = newsDaoImpl.get(id, true);
		if (news == null)
			response.setFail("消息不存在");
		else {
			newsDaoImpl.delete(news, true);
		}
		return response;
	}

}
