package com.onlineMIS.action.headQ.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.entity.headQ.SQLServer.ClientsMS;
import com.onlineMIS.common.Common_util;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;

@Controller
public class BasicInforJSONAction extends BasicInforAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9105960335932137110L;
	private String clientName;
	private int clientId;

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	private JSONObject jsonObject;

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}

	
	/**
	 * once user input the client name in the field, and click the search button
	 * @return
	 */
	public String searchClients(){
		List<ClientsMS> clients = basicInforService.getClientsByNameLike(clientName);
		Map<String,Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("clients", clients);
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				e.printStackTrace();
			}	
		
		System.out.println(jsonObject);
		
		return SUCCESS;		
		
	}
	
	/**
	 * once user input the client name in the field, and click the search button
	 * @return
	 */
	public String searchClient(){
		ClientsMS client = basicInforService.getClientById(clientId);
		Map<String,Object> jsonMap = new HashMap<String, Object>();

		if (client == null)
		    jsonMap.put("client", "");
		else {
			jsonMap.put("client", client);
		}
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				e.printStackTrace();
			}	
		
		System.out.println(jsonObject);
		
		return SUCCESS;		
		
	}

}
