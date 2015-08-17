/**
 * 画highchart的daily sales图
 * @param chartDiv
 * @param reportVO
 */
function drawHighChartDailySales(chartDiv, reportVO){
	$('#' + chartDiv).highcharts({
		backgroundColor:"",//
        title: {
            text: reportVO.chartName
        },
        subtitle: {
            text: '单位 千元 (k)'
        },
        xAxis: {
            categories: reportVO.xcategory,
            tickInterval:reportVO.ticketInterval
        },
        yAxis: {
            title: {
                text: reportVO.yserisName
            },
            lineWidth : 1,//Y轴的宽度
            gridLineColor: "gray",          
            gridLineWidth: 2
        },
        tooltip: {
            enabled: true,
            formatter: function() {
                return 			toolTips = '<b>'+ this.series.name +'</b><br/>'+
                this.x +': '+ this.y +'元';
            }
        },
        series: [{
            name: reportVO.seriesList[0].name,
            data: reportVO.seriesList[0].value
        }, {
            name: reportVO.seriesList[1].name,
            data: reportVO.seriesList[1].value
        }, {
            name: reportVO.seriesList[2].name,
            data: reportVO.seriesList[2].value
        }]
    });
}