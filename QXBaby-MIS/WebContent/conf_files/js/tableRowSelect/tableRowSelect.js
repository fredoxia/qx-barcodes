var trSize = 0;
/**
 * for this js, 必须实现
 * tableRowSelectEnter();
 * clickRight();
 * clickLeft();
 * clickEsc();
 * @param e
 */
function BSkeyDown(e){

	 var ieKey = event.keyCode;

	 if (ieKey==13){//enter
		tableRowSelectEnter();
	    event.returnValue=false; 
	 }  else if (ieKey==38){//up
		 var prevTrIndex = parseInt($("#prevTrIndex").val());
		if (prevTrIndex == -1 || prevTrIndex == 0) {
			clickTr(trSize - 1);
		} else if (prevTrIndex > 0) {
			clickTr(prevTrIndex - 1);
		}
		event.returnValue = false;
	}  else if (ieKey == 40) {// down
		var prevTrIndex = parseInt($("#prevTrIndex").val());
		if (prevTrIndex == -1 || prevTrIndex == (trSize - 1)) {
			clickTr(0);
		} else if (prevTrIndex < (trSize - 1)) {
			clickTr(prevTrIndex + 1);
		} 
	    event.returnValue = false;
	 } else if (ieKey == 39){ //right
		 clickRight();
	 } else if (ieKey == 37){ //left
		 clickLeft();
	 } else if (ieKey == 27){ //esc
		 clickEsc();
	 }
} 

document.onkeydown = BSkeyDown; 

function initialize(){
	 $("#prevTrIndex").val("-1");// 默认-1
	 
	 trSize= $(".dataTablegrid tr").size();// dataTablegrid中tr的数量
	 //alert(trSize);
	 
	 $(".dataTablegrid tr").mouseover(function() {// 鼠标滑过
			$(this).addClass("over");
		}).mouseout(function() { // 鼠标滑出
			$(this).removeClass("over");
		}).each(function(i) { // 初始化 id 和 index 属性
			$(this).attr("id", "tr_" + i).attr("index", i);
		});

		$(".dataTablegrid tr:even").addClass("alt"); // 偶行变色
     clickTr(0);
}



function clickTr(currTrIndex) {
	var prevTrIndex = $("#prevTrIndex").val();
	if (currTrIndex > -1) {
		$("#tr_" + currTrIndex).addClass("over");
	}
	$("#tr_" + prevTrIndex).removeClass("over");
	$("#prevTrIndex").val(currTrIndex);
}

