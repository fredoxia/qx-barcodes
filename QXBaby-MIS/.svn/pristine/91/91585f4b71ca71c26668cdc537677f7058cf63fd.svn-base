<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<s:if test="formBean.pager.currentPageGroup > 1"><a href="#" onclick="pager(1);" title="首页 "><< 首页</a> 
     <a href="#" onclick="pageGroup(<s:property value="formBean.pager.currentPageGroup -1"/>);" title="前页">< 前页</a>
</s:if><s:else> 
     << 首页 < 前页 
</s:else>

<s:iterator value="formBean.pager.pagesInGroup" id="page">
    <s:if test="#page != formBean.pager.currentPage"><a href="#" onclick="pager(<s:property value="#page"/>);"><s:property value="#page"/></a></s:if><s:else>
          <s:property value="#page"/>
    </s:else>
</s:iterator>
	 	 
<s:if test="formBean.pager.currentPageGroup < formBean.pager.totalPageGroup">
     <a href="#" onclick="pageGroup(<s:property value="formBean.pager.currentPageGroup+1"/>);" title="下页 Group">下页 ></a> 
     <a href="#" onclick="pager(<s:property value="formBean.pager.totalPage"/>);" title="末页 ">末页 >></a> </s:if>
<s:else>下页 > 末页 >></s:else> 
	 总计 : <s:property value="formBean.pager.totalPage"/> 页,  <s:property value="formBean.pager.totalResult"/> 记录