package gnnt.trade.bank.fileouter;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import java.io.OutputStream;
/**
 * 信息导出父类
 */
class FileOutPut {
	/** 发起端 */
	protected String operator = "E";
	/** 数据库连接类 */
	protected BankDAO DAO = null;
	/** 文件输出类 */
	protected OutPuter puter = null;
	/** 输出流 */
	protected OutputStream os = null;
	protected FileOutPut(OutputStream os){
		try{
			DAO = BankDAOFactory.getDAO();
			puter = new OutPuter();
			this.os = os;
		}catch(Exception e){
			
		}
	}
	protected FileOutPut(OutputStream os,String operator){
		try{
			this.operator = operator;
			DAO = BankDAOFactory.getDAO();
			puter = new OutPuter();
			this.os = os;
		}catch(Exception e){
			
		}
	}
}
