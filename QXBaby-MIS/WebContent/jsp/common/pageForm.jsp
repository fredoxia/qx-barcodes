
<script>

function resetSearchForm(){
	$("#totalPage").attr("value", 0);
	$("#totalResult").attr("value", 0);
	$("#currentPage").attr("value", 0);
	$("#totalPageGroup").attr("value", 0);
	$("#currentPageGroup").attr("value", 0);
}
function renderPaginationBar(currentPage, totalPage){
	$("#currentPage").attr("value", currentPage);
	$("#totalPage").attr("value", totalPage);
	
	$("#pageNav").html(pageNav.nav(currentPage,totalPage));
    
}
</script>
<s:hidden id="totalPage" name="formBean.pager.totalPage"/>
<s:hidden id="totalResult" name="formBean.pager.totalResult"/>
<s:hidden id="currentPage" name="formBean.pager.currentPage"/>
