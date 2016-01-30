package gnnt.MEBS.common.mgr.action;

import gnnt.MEBS.common.mgr.common.Condition;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.AbstractService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ecside.core.ECSideContext;
import org.ecside.table.limit.Filter;
import org.ecside.table.limit.FilterSet;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.RequestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Ecside使用的action
 * 
 * @author liuzx
 */
@Controller("com_ecsideAction")
@Scope("request")
public class EcsideAction extends StandardAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 通过获取Ecside的参数在数据库进行分页和排序；返回当前页的结果集
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String listByLimit() throws Exception {
		logger.debug("enter listByLimit");
		// 通过父类中方法取得form产生的查询条件和排序方案
		PageRequest<QueryConditions> pageRequest = super
				.getPageRequest(request);
		listByLimit(pageRequest);

		return SUCCESS;
	}
	
	/**
	 * 通过获取Ecside的参数在数据库进行分页和排序；返回当前页的结果集
	 * 
	 * @return String
	 * @throws Exception
	 */
	public void listByLimit(PageRequest<QueryConditions> pageRequest) throws Exception {
		logger.debug("enter listByLimit");
		// ecside查询条件
		List<Condition> ecsideConditions = getECSideQueryConditions();
		if (ecsideConditions != null && ecsideConditions.size() > 0) {
			// 如果查询条件不为空则将ECSide查询条件追加到查询条件
			if (pageRequest.getFilters().getConditionList() != null) {
				pageRequest.getFilters().getConditionList().addAll(
						ecsideConditions);
			} else {// 如果查询条件为空则将查询条件设置为ecside的查询条件
				pageRequest.getFilters().setConditionList(ecsideConditions);
			}
		}
		// ecside排序列
		String ecisdeSortColumns = getEcisdeSortColumns();
		if (ecisdeSortColumns != null && ecisdeSortColumns.length() > 0) {
			// 排序列
			String sortColumn = pageRequest.getSortColumns();
			if (sortColumn != null && sortColumn.trim().length() > 0) {
				sortColumn = " order by " + ecisdeSortColumns + ","
						+ (sortColumn.replace("order by", ""));
			} else {
				sortColumn = " order by " + ecisdeSortColumns;
			}
			// 重新设置排序列
			pageRequest.setSortColumns(sortColumn);
		}

		// 最大导出数量
		int maxExportedCount = 10000;
		// 如果不是导出
		if (!isExported()) {
			// 设置查询页码和分页大小
			pageRequest.setPageNumber(getEcsidePageNumber());
			pageRequest.setPageSize(getEcsidePageSize());
		} else {
			// 设置查询页码和分页大小
			pageRequest.setPageNumber(1);
			pageRequest.setPageSize(maxExportedCount);
		}

		Page<?> page = getPage(pageRequest);

		if (isExported() && page.getTotalCount() > maxExportedCount) {
			// TODO 不使用ecside的导出；自己实现导出功能
		} else {
			request.setAttribute(PAGEINFO, page);

			// 将旧的变量值填到Attribute中以方便将值反填到界面的查询条件内
			request.setAttribute(OLDPARAMETER, getParametersStartingWith(
					request, PARAMETERPREFIX));

			// 将总记录数传递给 ecside
			setECSideRowAttributes(page.getTotalCount());
		}
	}

	/**
	 * 
	 * 执行查询方法，用于查询 Page 信息<br/>
	 * 如果有比较复杂的查询，则子类可以重写本方法，然后在 调用 
	 * listByLimit(PageRequest<QueryConditions> pageRequest) 
	 * 方法时，会调用重写后的 本方法进行查询
	 * <br/><br/>
	 * @param pageRequest
	 * @return
	 */
	public Page<?> getPage(PageRequest<QueryConditions> pageRequest){
		// 根据传入的参数获取service
		AbstractService service = pageRequest.isQueryDB() ? getQueryService()
				: getService();
		Page<StandardModel> page = service.getPage(pageRequest, entity);
		return page;
	}

	/**
	 * 判断是否为导出
	 * 
	 * @return boolean (true：导出,false：查询)
	 */
	private boolean isExported() {
		boolean isExported = false;
		Limit limit = RequestUtils.getLimit(request);

		if (limit.isExported()) {
			isExported = true;
		}
		return isExported;
	}

	/**
	 * 获取Ecside页码
	 * 
	 * @return
	 */
	private int getEcsidePageNumber() {
		// 获取页面编号
		int pageNumber = RequestUtils.getPageNo(request);
		if (pageNumber <= 0) {
			pageNumber = 1;
		}
		return pageNumber;
	}

	/**
	 * 获取Ecside分页大小
	 * 
	 * @return
	 */
	private int getEcsidePageSize() {
		// 获取页面大小
		int pageSize = RequestUtils.getCurrentRowsDisplayed(request);
		if (pageSize <= 0) {
			pageSize = ECSideContext.DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	/**
	 * 将按照条件查询出来的数据总条数传递给 ecside
	 * 
	 * @param rowCount
	 *            按当前条件能够查询出的数据总条数
	 */
	private void setECSideRowAttributes(int rowCount) {
		Limit limit = RequestUtils.getLimit(request);
		limit.setRowAttributes(rowCount, ECSideContext.DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取ecside的查询条件集合
	 * 
	 * @param request
	 * @return List<Condition>
	 * @throws Exception
	 */
	private List<Condition> getECSideQueryConditions() throws Exception {
		logger.debug("通过 Ecside 获取查询条件");
		List<Condition> conditionList = new ArrayList<Condition>();
		Limit limit = RequestUtils.getLimit(request);
		FilterSet filterSet = limit.getFilterSet();
		Filter[] filters = filterSet.getFilters();// 查询条件集合体
		if (filters != null && filters.length > 0) {
			// 具有操作符和类型的的查询条件 [=][date]
			Pattern pOperatorAndType = Pattern.compile("\\[(.+)]\\[(.+)]");
			// 具有操作符的查询条件 [=]
			Pattern pOperator = Pattern.compile("\\[(.+)]");
			for (Filter filter : filters) {
				// 字段名
				String filed = filter.getProperty();
				// 字段值
				Object value = filter.getValue();
				// 比较方式默认为 =
				String operator = "=";
				// 比较操作符或者比较操作符与字段类型组合；如[like] 或者[>=][date]; 存放在没有使用的属性Alias中
				String alias = filter.getAlias();
				if (alias != null && alias.length() > 0) {
					String filedType = null;// 字段类型
					Matcher m = pOperatorAndType.matcher(alias);
					if (m.matches()) {// 如果匹配（"\\[(.+)]\\[(.+)]"）正则表达式，则第一位为比较方式，第二位为字段类型
						operator = m.group(1);
						filedType = m.group(2);
					} else {
						m = pOperator.matcher(alias);
						if (m.matches()) {// 如果匹配（"\\[(.+)]"）正则表达式，则第一位为比较方式
							operator = m.group(1);
						}
					}
					// 如果字段类型不为空；则将value按照指定的字段类型转换
					if (filedType != null && filedType.length() > 0) {
						value = Tools.convert(value, filedType);
					}
				}
				conditionList.add(new Condition(filed, operator, value));
			}
		}
		return conditionList;
	}

	/**
	 * 获取ecside排序列
	 * 
	 * @param request
	 * @return String 排序列
	 */
	private String getEcisdeSortColumns() {
		logger.debug("通过 Ecside 获取排序方案");
		Limit limit = RequestUtils.getLimit(request);
		Sort sort = limit.getSort();
		String sortName = sort.getProperty();// Ecside 传递来的排序字段名
		String sortOrder = sort.getSortOrder();// Ecside 传递来的排序方案
		if (sortName == null || sortName.trim().length() <= 0) {
			return null;
		}
		if (sortOrder == null || sortOrder.trim().length() <= 0) {
			return null;
		}
		if (!"desc".equalsIgnoreCase(sortOrder)) {
			sortOrder = "";
		}
		return sortName + " " + sortOrder;
	}
}
