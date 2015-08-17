<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script>
function uploadFile(){
	window.opener.parent.$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
	
	 var chainId = $("#chainStore").val();
	 $.ajaxFileUpload(
         {
             url:'<s:property value="formBean.url"/>?formBean.chainId=' + chainId,
             secureuri:false,
             fileElementId:'inventory',
             dataType: 'json',
             success: function (data, status){

                 var obj = JSON.parse(data.message);

                 var error = obj.error;

                 if (error != undefined){
                     alert(error);
                     window.opener.parent.$.messager.progress('close'); 
                 } else {
                	 var products = obj.products;
                	 window.opener.retrieveProductByFile(products);
                 }
                 window.close();
                 
             },
             error: function (data, status, e){
                 alert(e);
                 window.opener.parent.$.messager.progress('close'); 
                 window.close();
             }
         }
     );
}

</script>
</head>
<body>

   <s:form action="/actionChain/inventoryFlowJSONAction!uploadFile" enctype="multipart/form-data" method="POST" name="orderForm" id="orderForm" theme="simple">
   <table width="100%"  align="left" class="OuterTable" id="org_table">
	  <tr class="PBAInnerTableTitale" align='left'>
		    <th width="19%" height="35">上传文件</th>
	  </tr>
	  <tr class="InnerTableContent">
	        <td height="40">
	            <s:hidden name="formBean.chainId" id="chainStore"/>
                <input type="file" name="formBean.inventory" id="inventory"/>
	        </td>
     </tr>
	 <tr class="InnerTableContent">	      
			<td height="41">
			      <input type="button" value="文件导入" onclick="uploadFile();" />
		    </td>
	 </tr> 
	</table>
	</s:form>
</body>
</html>