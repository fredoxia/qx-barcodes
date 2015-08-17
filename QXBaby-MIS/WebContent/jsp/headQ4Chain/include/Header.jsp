<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
	<div id="version" style="position: absolute; left: 0px; bottom: 2px;" class="alert alert-info">
		<img src='<%=request.getContextPath()%>/conf_files/web-image/mis-logo.jpg' height='55' width='280' align="left">
    </div>
    <div id="version" style="position: absolute; left: 290px; bottom: 4px;" class="alert alert-info">
	     连锁店创建条码系统  V1.5
    </div>
	<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 3px;" class="alert alert-info">
	    欢迎 <s:property value="#session.LOGIN_CHAIN_USER.myChainStore.chain_name"/> <s:property value="#session.LOGIN_CHAIN_USER.name"/>
    </div>
    <div style="position: absolute; right: 0px; bottom: 4px;">
        <a href="<%=request.getContextPath()%>/action/userJSP!logoff4Chain" class="easyui-linkbutton">注销</a>
    </div>
