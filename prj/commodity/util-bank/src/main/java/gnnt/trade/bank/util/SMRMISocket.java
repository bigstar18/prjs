
package gnnt.trade.bank.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

/**
 * <P>类说明：定义 RMI 数据传输端口用到的 Socket 创建类
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2013-5-17下午02:50:16|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class SMRMISocket extends RMISocketFactory {
	private int port = 1099;

	public SMRMISocket(int port){
		if(port>0){
			this.port = port;
		}
	}
	

	@Override
	public ServerSocket createServerSocket(int port) throws IOException {
		if(port<=0){
			port = this.port;
		}
		return new ServerSocket(port);
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException {
		return new Socket(host, port);

	}

}

