var hold;
			$(document).ready(function() {
			 Highcharts.setOptions({
                    global: {
                      useUTC: false
                    }
                  });
		hold = new Highcharts.Chart({
            chart: {
				renderTo: 'container_hold',
                zoomType: 'x',
                spacingRight: 20
            },
			credits: {
				 enabled: true,
				 text:"",
				 href:"#"
			},
            title: {
                text: '持仓数量'
            },
            subtitle: {
               text:'单击并拖动在图形区放大' 
            },
            xAxis: {
                type: 'datetime',
				//tickInterval: 1 * 100 * 1000,
                //maxZoom: 1 * 1 * 3600000, // fourteen days
				//tickPixelInterval:60,//设置横坐标密度  
				
                  tickPixelInterval: 100,
                title: {
                    text: null
                },
				labels:{			
				enabled: false,
				//step:2,
				formatter: function() {  
					return Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.value);  
				}}
            },
           yAxis: {
				title: {
					text: ''
				},
				min: 1,
				startOnTick: false,
				showFirstLabel: false
			},
            tooltip: {
				 formatter: function() {
				return ''+
					Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x)+
					'，数量： '+ Highcharts.numberFormat(this.y, 0);
			},
			 headerFormat: '<b>{series.name}</b><br/>'
            },
            legend: {
                enabled: true
            },
            plotOptions: {
                area: {
                    fillOpacity: 0.5,
                    lineWidth: 1,
                    marker: {
                        enabled: false
                    },
                    shadow: false,
                    states: {
                        hover: {
                            lineWidth: 1
                        }
                    },
                    threshold: null
                },
                line: {
					marker: {
                		enabled: false
					},
					 lineWidth: 2,
					 fillOpacity:0.15
				}
            },
    
            series: null
        });
    });