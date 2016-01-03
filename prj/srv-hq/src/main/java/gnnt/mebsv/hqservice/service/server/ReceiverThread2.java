/**
 * 
 */
package gnnt.mebsv.hqservice.service.server;

import gnnt.mebsv.hqservice.service.HQService2;

/**
 * @author hxx
 *
 */
public class ReceiverThread2 extends ReceiverThread {
	@Override
	public void initResponder() {
		super.initResponder();
		hqService = new HQService2();
		hqService.init();
		pushServer = new PushServer(hqService, null);
	}

}
