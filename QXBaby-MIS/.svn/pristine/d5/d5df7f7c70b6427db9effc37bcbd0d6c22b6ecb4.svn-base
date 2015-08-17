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
	 $.ajaxFileUpload
     (
         {
             url:'inventoryOrderUpload!uploadFile',
             secureuri:false,
             fileElementId:'orderExcel',
             dataType: 'json',
             success: function (data, status){
                 var obj = JSON.parse(data.message);

                 var error = obj.error;

                 if (error != undefined)
                     alert(error);
                 else {
                		var products = obj.products;
                	    window.opener.retrieveProductByExcel(products);
                 }
                 
             },
             error: function (data, status, e)
             {
                 alert(e);
             }

         }
     );
	
	/*var exist = window.opener.validateRowInputFromChild(barcode);
	if (exist){
       var index_trigger = $("#index").val();
       window.opener.retrieveProductByBarcode(index_trigger,barcode);  
       window.close();
	}*/
}
function parseProductValue(data){

	var products = data.products;
    window.opener.retrieveProductByExcel(products);
}
</script>
</head>
<body>
   <s:form action="/action/inventoryOrderJSON!uploadFile" enctype="multipart/form-data" method="POST" name="inventoryOrderForm" id="inventoryOrderForm" theme="simple">
   <table width="100%"  align="left" class="OuterTable" id="org_table">
	  <tr class="PBAInnerTableTitale" align='left'>
		    <th width="19%" height="35">上传文件</th>
	  </tr>
	  <tr class="InnerTableContent">
	        <td height="40">
                <input type="file" name="formBean.orderExcel" id="orderExcel"/>
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