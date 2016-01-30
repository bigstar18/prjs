/**
 * getPages(pageCount,pageNum,bakFunction)：获取页面展示页码区间
 * getMaxPage(pageSize,rowCount)：通过页面承载数量和数据总条数获取最大页码
 */
/**
 * 分页信息用到的js
 */
var pageInfo={
	half:3,
	//               0                1              2              3
	errorNaN:["数据总条数非数字！","最大页码非数字！","当前页码非数字！","页承载量非数字！"],
	/**
	 * 获取页面展示页码区间
	 * @param pageCount 总页数
	 * @patam pageNum 当前页码
	 * @patam bakFunction 回掉函数(其中有两个参数，一个是起始页，一个是结束页)
	 */
	getPages:function (pageCount,pageNum,bakFunction){
		if(isNaN(pageCount)){
			throw new TypeError(this.errorNaN[1]);
		}
		if(isNaN(pageNum)){
			throw new TypeError(this.errorNaN[2]);
		}
		/*half:一半页数,from:起始页,to:结束页*/
		var from = 1,to = 1;
		pageCount = parseInt(pageCount);//整数化页数
		pageNum = parseInt(pageNum);//整数化当前页码
		if(pageNum>this.half){
			if(pageCount-pageNum>=this.half){
				to = pageNum + this.half;
				from = pageNum-this.half;
			}else{
				to = pageCount
				from = to-this.half*2;
			}
		}else{
			if(pageCount-from>=this.half*2){
				to = from + this.half*2;
			}else{
				to = pageCount;
			}
		}
		if(from<=0){
			from = 1;
		}
		if(to<from){
			to = from;
		}
		bakFunction(from,to);
	},
	/**
	 * 通过页面承载数量和数据总条数获取最大页码
	 * @param pageSize 页承载数量
	 * @param rowCount 数据总条数
	 * @return maxPageNum 最大页码
	 */
	getMaxPage:function(pageSize,rowCount){
		if(isNaN(pageSize)){
			throw new TypeError(this.errorNaN[3]);
		}
		if(isNaN(rowCount)){
			throw new TypeError(this.errorNaN[0]);
		}
		pageSize = parseInt(pageSize);//整数化页承载数量
		rowCount = parseInt(rowCount);//整数化总条数
		if(pageSize<1){
			return 1;
		}
		if(rowCount<=1){
			return 1;
		}
		if(rowCount%pageSize != 0){//如果总条数与每页条数取余数不为0
			return parseInt(rowCount/pageSize)+1;
		}
		return parseInt(rowCount/pageSize);
	}
}