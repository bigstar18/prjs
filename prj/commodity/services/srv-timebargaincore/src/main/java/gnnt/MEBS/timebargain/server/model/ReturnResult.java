package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 返回结果对象
 * 
 * @author cxc
 */
public class ReturnResult implements Serializable {
	private static final long serialVersionUID = 3690197650654049817L;
	
	public int retCode; // 返回代码 
	
	public String errMsg = ""; // 出错时存放数字或错误描述
	
	public List vResult = new ArrayList(); //返回的成功结果集，默认的
	public List vResult2 = new ArrayList(); //返回的第二个成功结果集
	
	public static final int RETCODE_SUCCESS = 0;// 成功
	public static final int RETCODE_ILLEGAL = 1;// 身份不合法
	public static final int RETCODE_DATEERR = 2;// 日期格式错误
	
}
