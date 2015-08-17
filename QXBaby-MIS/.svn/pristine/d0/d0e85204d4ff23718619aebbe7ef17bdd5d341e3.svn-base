<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>


	<div id="version" style="position: absolute; left: 0px; bottom: 2px;" class="alert alert-info">
		<img src='<%=request.getContextPath()%>/conf_files/web-image/mis-logo.jpg' height='43' width='230' align="left">
    </div>
    <div id="version" style="position: absolute; left: 250px; bottom: 4px;" class="alert alert-info">
	     连锁店 V2.0
    </div>
    <div id="version" style="position: absolute; left: 380px; bottom: 4px;" class="alert alert-info">
	     <font style="color:red"><s:property value="uiBean.specialMsg"/></font>
    </div>    
	<div id="sessionInfoDiv" style="position: absolute; right: 3px; top: 3px;" class="alert alert-info">
	    欢迎 <s:property value="#session.LOGIN_CHAIN_USER.name"/>, <s:property value="#session.LOGIN_CHAIN_USER.myChainStore.chain_name"/>
    </div>
    <div style="position: absolute; right: 0px; bottom: 4px;">
        <!--  
	    <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'cog'">更换皮肤</a> 
	    <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'cog'">控制面板</a>-->
	    <a href="chainUserJSP!logoff" class="easyui-linkbutton">退出系统</a>
    </div>
