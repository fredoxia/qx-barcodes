package com.onlineMIS.action.chainS.chainOtherMgmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.springframework.beans.BeanUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainPriceIncrement;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.user.News;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;


public class ChainOtherMgmtJSONAction extends ChainOtherMgmtAction {
	private int id;
	private String title;
	private String content;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1795628921324212466L;
	
	private Map<String,Object> jsonMap = new HashMap<String, Object>();
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private String message;
	
	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 获取所有的news, 准备修改编辑
	 * @return
	 */
	public String getAllNews(){
		Response response = new Response();
		try {
		    response = newsService.getAllNews();
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		jsonMap = (Map)response.getReturnValue();
		try {
				jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		
		return SUCCESS;
	}
	
	public String deleteNews(){
		Response response = new Response();
		try {
			News news = new News();
			BeanUtils.copyProperties(this, news);
		    response = newsService.deleteNews(news);

		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		try {
				jsonObject = JSONObject.fromObject(response);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		
		return SUCCESS;
	}
	
	public String updateNews(){
		Response response = new Response();
		try {
			News news = new News();
			BeanUtils.copyProperties(this, news);
		    response = newsService.saveNews(news);
		    
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setReturnCode(Response.FAIL);
		}
		
		try {
				jsonObject = JSONObject.fromObject(response);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}

		
		return SUCCESS;
	}
}
