package com.onlineMIS.ORM.entity.chainS.vip;


import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.DAO.chainS.vip.ChainVIPCardImpl;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;

public class ChainVIPCard implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -9213843067874304964L;
	public static final int STATUS_GOOD = 1;
	public static final int STATUS_STOP = 2;
	public static final int STATUS_LOST = 3;
	public static final int STATUS_DELETE = 9;
	private int id;
	private String vipCardNo;
    private ChainVIPType vipType;
    private Date cardIssueDate;
    private Date cardExpireDate;
    /**
     * 初始
     */
    private double initialValue;
    /**
     * 初始积分
     */
    private double initialScore;
    private String customerName;
    private String idNum;
    private String telephone;
    private int gender;
    private Date customerBirthday;
    private String province;
    private String city;
    private String zone;
    private String street;
    private String comment;
    
    private int status;
    private ChainStore issueChainStore;
    private String pinyin;
    
    /**
     * the two are used for the web ui display
     */
    private String statusS;
    private String genderS;
    /**
     * the three are used for the the VIP Card
     * 1. accumulated score
     * 2. consumed score
     */
    private double accumulatedScore;
    private double consumedScore;
    private double accumulateVipPrepaid;
    
    /**
     * 生日day和month
     */
    private int birthDay;
    private int birthMonth;
    
    /**
     * 页面展示用处
     * status: 0 正常
     *         1 超过半年没有消费了
     * @return
     */
    private Date lastConsumpDate = null;
    public static final int STATUS_CONSUMP_MORE_180 = 1;
    private int statusConsump = 0;
    
 
	public int getStatusConsump() {
		return statusConsump;
	}
	public void setStatusConsump(int statusConsump) {
		this.statusConsump = statusConsump;
	}
	public Date getLastConsumpDate() {
		return lastConsumpDate;
	}
	public void setLastConsumpDate(Date lastConsumpDate) {
		this.lastConsumpDate = lastConsumpDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
	}
	public int getBirthMonth() {
		return birthMonth;
	}
	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public double getAccumulatedScore() {
		return accumulatedScore;
	}
	public void setAccumulatedScore(double accumulatedScore) {
		this.accumulatedScore = accumulatedScore;
	}
	public double getConsumedScore() {
		return consumedScore;
	}
	public void setConsumedScore(double consumedScore) {
		this.consumedScore = consumedScore;
	}
	public double getInitialScore() {
		return initialScore;
	}
	public void setInitialScore(double initialScore) {
		this.initialScore = initialScore;
	}
	public String getStatusS() {
		return ChainVIPCardImpl.getStatusMap().get(status);
	}
	public String getGenderS() {
		return Common_util.getGender().get(gender);
	}
	public String getVipCardNo() {
		return vipCardNo;
	}
	public void setVipCardNo(String vipCardNo) {
		this.vipCardNo = vipCardNo;
	}
	
	public ChainVIPType getVipType() {
		return vipType;
	}
	public void setVipType(ChainVIPType vipType) {
		this.vipType = vipType;
	}
	public Date getCardIssueDate() {
		return cardIssueDate;
	}
	public void setCardIssueDate(Date cardIssueDate) {
		this.cardIssueDate = cardIssueDate;
	}
	public Date getCardExpireDate() {
		return cardExpireDate;
	}
	public void setCardExpireDate(Date cardExpireDate) {
		this.cardExpireDate = cardExpireDate;
	}
	public double getInitialValue() {
		return initialValue;
	}
	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public Date getCustomerBirthday() {
		return customerBirthday;
	}
	public void setCustomerBirthday(Date customerBirthday) {
		this.customerBirthday = customerBirthday;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ChainStore getIssueChainStore() {
		return issueChainStore;
	}
	public void setIssueChainStore(ChainStore issueChainStore) {
		this.issueChainStore = issueChainStore;
	}
	public double getAccumulateVipPrepaid() {
		return accumulateVipPrepaid;
	}
	public void setAccumulateVipPrepaid(double accumulateVipPrepaid) {
		this.accumulateVipPrepaid = accumulateVipPrepaid;
	}
  
    
}
