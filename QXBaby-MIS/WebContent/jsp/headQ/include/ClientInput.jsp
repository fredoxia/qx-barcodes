<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
<!--
#ClientDiv {
	position:absolute;
	width:200px;
	height:100px;
	z-index:99;
	left: 280px;
	top: 50px;
	overflow: visible;
	display:none;

}
-->
</style>
<script>

function searchCustomer(){
	var clientName = $("#clientName").val();
	var clientArray = clientName.split(",");	
	clientName = clientArray[0];  
	
	if (clientName ==""){
		alert("请输入客户名字然后查询！");
	} else {
        var params= "clientName=" +clientName; 
        $.post("<%=request.getContextPath()%>/action/basicInforJSON!searchClients",params, backProcessSearchClient,"json");
	}
}
function backProcessSearchClient(data){
	var clients = data.clients;
	
    $('#clientTablebody tr').each(function () {                
        $(this).remove();
    });

    if (clients.length != 0){
	    for (var i = 0; i < clients.length; i++){
	    	var bg = "";
	    	if ((i % 2) == 0)
	    		bg = " style='background-color: rgb(255, 250, 208);'";
	        if (clients[i] != "")  
		          $("<tr align='center'  height='10' " + bg +"><td>"+clients[i].name+"</td><td>"+clients[i].region.name+"</td><td>"+clients[i].client_id+"</td><td><a href='#' onclick='selectClient("+clients[i].client_id+",\""+clients[i].name+"," +clients[i].region.name +"\")'><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td></tr>").appendTo("#clientTablebody");
	    }
    }else {
    	$("<tr class='InnerTableContent' height='10' style='background-color: rgb(255, 250, 208);' align='center'><td colspan=4><font color='red'>对应信息没有查询信息</font> </td></tr>").appendTo("#clientTablebody");
    }  
    
    $("#ClientDiv").show();	
}
function selectClient(clientId, clientName){
	$("#clientID").attr("value", clientId);
	$("#clientName").attr("value", clientName);
	$("#ClientDiv").hide();
}
function hideClientDiv(){
	$("#ClientDiv").hide();
}
</script>
<s:hidden id="clientID" name="formBean.order.client_id"/>
<s:textfield id="clientName" name="formBean.order.client_name" size="20"/> <input type="button" value="查询" onClick="searchCustomer();"/>
                          
 <div id="ClientDiv">
   <table width="400" align="center"  class="OuterTable" bgcolor="#FFFFFF" >
    <tr><td>
	    <table width="100%" border="0">
	       <tr class="InnerTableContent" style="background-color: #CCCCCC">
	          <th width="40%" height="10">客户名字</th>
	          <th width="40%">客户地区</th>
	          <th width="20%">精算账号</th>
	          <th width="15%"></th>
	       </tr>
		   <tbody id="clientTablebody">
		   </tbody>
	    </table>
	    </td>
	</tr>
	<tr>
	    <td align="center"><input type="button" value="关闭窗口" onclick="hideClientDiv()"/></td>
	</tr>
  </table>
</div>