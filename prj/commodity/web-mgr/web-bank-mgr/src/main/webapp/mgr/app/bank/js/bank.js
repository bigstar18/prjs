/**
 * 判断传入的银行编号集合中是否包含当个银行编号
 * @param bankIDs 银行编号集合，每两个银行编号之间要以逗号分割
 * @param bankID 判断的银行编号
 * @return boolean true 包含,false 不包含
 */
function isContentsBank(bankIDs,bankID){
	/* 
	 * 如果银行编号集合为空，或者银行编号为空
	 * 或者银行编号集合的长度小于银行编号的长度
	 * 则认为银行编号集合不可能包含银行编号，返回失败
	 */
	if(!bankIDs || !bankID || bankIDs.length<bankID.length){
		return false;
	}
	//如果银行编号集合等于银行编号，则认为配置中只包含本银行编号，返回成功
	if(bankIDs == bankID){
		return true;
	}
	/* 
	 * 如果银行编号集合以银行编号加英文逗号开头，则认为包含本银行编号，返回成功
	 * 如果只是判断是否以银行编号开头，可能会导致其他银行编号前几位与当个判断银行编号一致
	 * 导致判断错误
	 */
	if(bankIDs.indexOf(bankID+",")==0){
		return true;
	}
	bankIDs += ",";
	/**
	 * 如果银行编号集合末尾加上逗号后，包含银行编号两边加逗号，认为包含本隐含编号，返回成功
	 */
	if(bankIDs.indexOf(","+bankID+",")>=0){
		return true;
	}
	//不满足以上情况，则说明不包含本银行编号
	return false;
}