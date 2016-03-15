/**
 * 用户管理模块公共JS函数库
 * 
 * @author 卢一贺
 * @since 1.0 2012-11-07
 */


/**
 * 从double_select双选框列表中删除掉已选择的选项
 */
function removeSeletedItemInDoubleSelect($select1, $select2) {
	$select1.find("option").each(function() {
		if($select2.find("option[value='" + this.value + "']").length > 0) {
			$(this).remove();
		}
	});
}
