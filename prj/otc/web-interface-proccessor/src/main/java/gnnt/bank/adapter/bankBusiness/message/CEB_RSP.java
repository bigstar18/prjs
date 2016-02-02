package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;

public class CEB_RSP
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String flag = "1";
  public String errorInfo;
  public RSP_F601 rsp_F601 = new RSP_F601();
  public RSP_F603 rsp_F603 = new RSP_F603();
  public RSP_F604 rsp_F604 = new RSP_F604();
  public RSP_F608 rsp_F608 = new RSP_F608();
  public RSP_F609 rsp_F609 = new RSP_F609();
  public RSP_F610 rsp_F610 = new RSP_F610();
  public RSP_F612 rsp_F612 = new RSP_F612();
  public RSP_F618 rsp_F618 = new RSP_F618();
  public RSP_F619 rsp_F619 = new RSP_F619();
  public RSP_F622 rsp_F622 = new RSP_F622();
  public RSP_F625 rsp_F625 = new RSP_F625();
  public RSP_F626 rsp_F626 = new RSP_F626();
  public RSP_F623 rsp_F623 = new RSP_F623();
  public REQ_BODY_F612 req_body_F612 = new REQ_BODY_F612();
}
