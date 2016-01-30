package gnnt.MEBS.common.mgr.model;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

import java.util.Date;
/**
 * 操作日志对象公共部分
 * @author liuzx
 */
public class OperateLogBase extends StandardModel {
	private static final long serialVersionUID = 2313125464095942060L;

	/**主键*/
	@ClassDiscription(name="主键",description="")
	private Long id;

	/**操作人*/
	@ClassDiscription(name="操作人",description="")
	private String operator;

	/**操作时间*/
	@ClassDiscription(name="操作时间",description="")
	private Date operateTime;

	/**操作人IP*/
	@ClassDiscription(name="操作人IP",description="")
	private String operateIp;

	/**操作人类型*/
	@ClassDiscription(name="操作人类型",description="")
	private String operatorType;

	/**备注*/
	@ClassDiscription(name="备注",description="")
	private String mark;

	/**操作内容*/
	@ClassDiscription(name="操作内容",description="")
	private String operateContent;

	/**当前值*/
	@ClassDiscription(name="当前值",description="")
	private String currentValue;

	/** 编号 */
	@ClassDiscription(name="仓库编号",description="")
	private String wareHouseID;

	/**
	 * 操作结果 
	 * 1:成功 0：失败
	 */
	@ClassDiscription(name="操作结果",description="操作结果 1:成功 0：失败")
	private Integer operateResult;
	
	/**对应的日志类别*/
	@ClassDiscription(name="对应的日志类别",description="")
	private LogCatalog logCatalog;

	/**
	 * 日志类型<br/>
	 * 0 其他、1 后台、2 前台、3 核心
	 */
	@ClassDiscription(name="日志类型",description="日志类型 0 其他、1 后台、2 前台、3 核心")
	private Integer logType;

	/**
	 * 日志记录号
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 日志记录号
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 操作人
	 * 
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 操作人
	 * 
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 操作时间
	 * 
	 * @return
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * 操作时间
	 * 
	 * @param operateTime
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * 操作人IP
	 * 
	 * @return
	 */
	public String getOperateIp() {
		return operateIp;
	}

	/**
	 * 操作人IP
	 * 
	 * @param operateIp
	 */
	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}

	/**
	 * 操作人类型
	 * 
	 * @return
	 */
	public String getOperatorType() {
		return operatorType;
	}

	/**
	 * 操作人类型
	 * 
	 * @param operatorType
	 */
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getMark() {
		return mark;
	}

	/**
	 * 备注
	 * 
	 * @param mark
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}

	/**
	 * 操作内容
	 * 
	 * @return
	 */
	public String getOperateContent() {
		return operateContent;
	}

	/**
	 * 操作内容
	 * 
	 * @param operateContent
	 */
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	/**
	 * 当前对象的本地序列化
	 * 
	 * @return
	 */
	public String getCurrentValue() {
		return currentValue;
	}

	/**
	 * 当前对象的序列化
	 * 
	 * @param currentValue
	 */
	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	/**
	 * 操作结果 1：成功 0：失败
	 * 
	 * @return
	 */
	public Integer getOperateResult() {
		return operateResult;
	}

	/**
	 * 操作结果 1：成功 0：失败
	 * 
	 * @param operateResult
	 */
	public void setOperateResult(Integer operateResult) {
		this.operateResult = operateResult;
	}

	/**
	 * 日志对应的分类
	 * 
	 * @return
	 */
	public LogCatalog getLogCatalog() {
		return logCatalog;
	}

	/**
	 * 日志对应的分类
	 * 
	 * @param logCatalog
	 */
	public void setLogCatalog(LogCatalog logCatalog) {
		this.logCatalog = logCatalog;
	}

	/**
	 * 日志类型<br/>
	 * 0 其他、1 后台、2 前台、3 核心
	 * @return Integer
	 */
	public Integer getLogType() {
		return logType;
	}

	/**
	 * 日志类型<br/>
	 * 0 其他、1 后台、2 前台、3 核心
	 * @param logType
	 */
	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	/**
	 * @return 获取  编号
	 */
	public String getWareHouseID() {
		return wareHouseID;
	}

	/**
	 * @param 设置 编号 
	 */
	public void setWareHouseID(String wareHouseID) {
		this.wareHouseID = wareHouseID;
	}
	
	@Override
	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("id", this.id);
	}
}
