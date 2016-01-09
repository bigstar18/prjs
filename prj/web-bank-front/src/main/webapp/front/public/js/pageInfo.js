/**
 * getPages(pageCount,pageNum,bakFunction)����ȡҳ��չʾҳ������
 * getMaxPage(pageSize,rowCount)��ͨ��ҳ�����������������������ȡ���ҳ��
 */
/**
 * ��ҳ��Ϣ�õ���js
 */
var pageInfo={
	half:3,
	//               0                1              2              3
	errorNaN:["���������������֣�","���ҳ������֣�","��ǰҳ������֣�","ҳ�����������֣�"],
	/**
	 * ��ȡҳ��չʾҳ������
	 * @param pageCount ��ҳ��
	 * @patam pageNum ��ǰҳ��
	 * @patam bakFunction �ص�����(����������������һ������ʼҳ��һ���ǽ���ҳ)
	 */
	getPages:function (pageCount,pageNum,bakFunction){
		if(isNaN(pageCount)){
			throw new TypeError(this.errorNaN[1]);
		}
		if(isNaN(pageNum)){
			throw new TypeError(this.errorNaN[2]);
		}
		/*half:һ��ҳ��,from:��ʼҳ,to:����ҳ*/
		var from = 1,to = 1;
		pageCount = parseInt(pageCount);//������ҳ��
		pageNum = parseInt(pageNum);//��������ǰҳ��
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
	 * ͨ��ҳ�����������������������ȡ���ҳ��
	 * @param pageSize ҳ��������
	 * @param rowCount ����������
	 * @return maxPageNum ���ҳ��
	 */
	getMaxPage:function(pageSize,rowCount){
		if(isNaN(pageSize)){
			throw new TypeError(this.errorNaN[3]);
		}
		if(isNaN(rowCount)){
			throw new TypeError(this.errorNaN[0]);
		}
		pageSize = parseInt(pageSize);//������ҳ��������
		rowCount = parseInt(rowCount);//������������
		if(pageSize<1){
			return 1;
		}
		if(rowCount<=1){
			return 1;
		}
		if(rowCount%pageSize != 0){//�����������ÿҳ����ȡ������Ϊ0
			return parseInt(rowCount/pageSize)+1;
		}
		return parseInt(rowCount/pageSize);
	}
}