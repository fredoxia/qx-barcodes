<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function callP(){
	alert("this parent");
}
</script>
<div id="indexTab" class="easyui-tabs" fit="true" border="false">  
      <div title="首页">
		<table cellpadding='10px' cellspacing='10px'>
			<tr valign="top">
			   <td>
			    <div id="p" class="easyui-panel" title="消息栏"  
			 			style="width:450px;height:250px;padding:10px;background:#fafafa;"  
	        			data-options="collapsible:false">  
				        <table cellpadding="0" cellspacing="0" style="width: 95%" border="0">	 	
						 	<tr height="22">
						 		<td colspan="9">
						 		<p>公司竭力推动办公自动化，提高员工工作效率和工作准确度。但是在使用电脑时请注意以下事项:</p>
						 		1. 电脑请安装杀毒软件，比如360，金山毒霸之类，<p/>
						 		2. 对于从公司外带入的U盘，插入公司电脑，请一定小心，可能感染病毒；打开前请用杀毒软件扫描<p/>
						 		3. 网页浏览一定注意，不要浏览非正规网站（此类网站很多有钓鱼软件），<p/>
						 		4. 如果网页提醒安装插件，请三思而后行。如果你对这个网站不了解，请拒绝安装。很多中毒，都与安装网页插件有关<p/>
						 		5. 下载软件请从天空软件，等知名软件下载网站下载。很多非正规软件下载网站都安插病毒。<p/>
						 		6. 运行exe等安装程序，请先检查运行程序是否是木马，之类病毒，使用杀毒软件扫描。一般软件安装程序都是几十MB，<p/>
						 		   如果你需要安装的程序只有几百kb那么请注意很可能是木马病毒。<p/>
						 		7. 如果允许，请对自己电脑设置密码，会计电脑必须设置密码，密码可以是大家都知道的。设置密码不是防止公司其他人使用你的电脑而是为了防止互联网非法入侵<p/>
						 		</td>
						 	</tr>
						</table>
			    </div> 
			   </td>
			   <td>
			   		<div id="p2" class="easyui-panel" title="功能更新栏"  
			 			style="width:450px;height:250px;padding:10px;background:#fafafa;"  
	        			data-options="collapsible:false">  
				        <table style="width: 100%" border="0">	
				        	<tr class="InnerTableContent">
								<td><strong>系统支持</strong><br/>
								    - 本系统完全支持IE,为了最好的使用体验，请使用IE8,点击下面链接下载<p/>
								    - <a href="http://www.microsoft.com/zh-cn/download/internet-explorer-8-details.aspx" target="_blank">下载</a><p/>
								    - * 下载时请注意你的操作系统，请选 Windows Internet Explorer 8 for Windows XP <p/>
								    - 有任何问题请联系 夏林
								</td>
						 	</tr>
						 	<tr height="10">
								<td><hr/></td>
							</tr> 
						</table>
			       </div>
			   </td>
			</tr>
		</table>
	</div>
</div>
