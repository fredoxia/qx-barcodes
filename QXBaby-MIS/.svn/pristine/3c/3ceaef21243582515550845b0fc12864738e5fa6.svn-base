package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;


public class ChainReport implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7625383974204226847L;
	public final static int TYPE_SALES_REPORT =1 ;
	public final static int TYPE_PURCHASE_REPORT =2 ;
	public final static int TYPE_FINANCE_REPORT =3 ;
	private static Map<Integer, String> typeMap = new LinkedHashMap<Integer, String>();
	
	static {
		typeMap.put(TYPE_SALES_REPORT, "销售报表");
		typeMap.put(TYPE_PURCHASE_REPORT, "采购报表");
		typeMap.put(TYPE_FINANCE_REPORT, "财务报表");
	}
	
	protected ChainStore chainStore = new ChainStore();

    /**
     * report type
     */
    protected int type;
    protected String typeS;

	public String getTypeS() {
		return typeMap.get(type);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	

	public ChainStore getChainStore() {
		return chainStore;
	}

	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}

	public static Map<Integer, String> getTypeMap() {
		return typeMap;
	}

	
}
