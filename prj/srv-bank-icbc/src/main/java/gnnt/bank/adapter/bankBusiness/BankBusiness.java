package gnnt.bank.adapter.bankBusiness;

import gnnt.bank.adapter.BankAdapter;
import gnnt.bank.adapter.Factory;
import gnnt.bank.adapter.ICBCBankImpl;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import gnnt.bank.adapter.bankBusiness.enumElmt.BusinessCode;
import gnnt.bank.adapter.bankBusiness.enumElmt.ReturnCode;
import gnnt.bank.adapter.bankBusiness.exception.BankActionDoubtedException;
import gnnt.bank.adapter.bankBusiness.exception.BankCommException;
import gnnt.bank.adapter.bankBusiness.exception.BankException;
import gnnt.bank.adapter.bankBusiness.info.UnCertain;
import gnnt.bank.adapter.socket.SocketClient;
import gnnt.bank.adapter.util.Common;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class BankBusiness
{
  private static final String INFOPKGPATH = "gnnt.bank.adapter.bankBusiness.info.";
  private ICBCBankImpl adapter;

  public BankBusiness()
  {
    this.adapter = ((ICBCBankImpl)Factory.getInstance().getBankAdapter());
  }

  public Object getResponse(Object request)
    throws BankCommException, BankActionDoubtedException
  {
    String requestName = request.getClass().getSimpleName();

    String requestXml = null;
    JAXBContext context = null;
    try {
      context = JAXBContext.newInstance(new Class[] { request.getClass() });
      Marshaller ms = context.createMarshaller();
      ms.setProperty("jaxb.encoding", "GBK");
      Writer writer = new StringWriter();
      ms.marshal(request, writer);
      requestXml = writer.toString();
      requestXml = fmtMktXml(requestXml);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    BankAdapter.log("发送报文：" + requestXml);

    SocketClient socketClient = Factory.getInstance().getClient();
    String recInfo = null;
    int rst = socketClient.send(requestXml);
    BankAdapter.log("发送报文返回结果：" + rst);
    if (rst == 0) {
      recInfo = socketClient.receive();
    } else {
      BankAdapter.log("通讯失败");
      throw new BankCommException("通讯失败");
    }
    if (!checkBankInfo(recInfo)) {
      throw new BankActionDoubtedException("银行返回数据异常");
    }

    String responseName = requestName + "Response";
    recInfo = fmtBankXml(recInfo, Common.toLowerCaseStart(responseName));
    BankAdapter.log("银行返回结果信息：" + recInfo);
    Object response = null;
    try {
      response = Class.forName("gnnt.bank.adapter.bankBusiness.info." + responseName).newInstance();
      context = JAXBContext.newInstance(new Class[] { response.getClass() });
      InputStream is = new ByteArrayInputStream(recInfo.getBytes());
      Unmarshaller ums = context.createUnmarshaller();
      response = ums.unmarshal(is);
    } catch (JAXBException e) {
      throw new BankException("银行返回数据错误:" + e.getMessage());
    } catch (ClassNotFoundException e) {
      throw new BankException("银行返回数据错误，找不到对应的业务类:" + responseName + "\n" + e.getMessage());
    } catch (InstantiationException e) {
      throw new BankException("银行返回数据错误:" + e.getMessage());
    } catch (IllegalAccessException e) {
      throw new BankException("银行返回数据错误:" + e.getMessage());
    }
    return response;
  }

  public String getResponse(String requestXml)
    throws BankException
  {
    JAXBContext context = null;
    UnCertain unCertain = null;
    try {
      String requestBaseStr = fmtBankXml(requestXml, "unCertain");
      context = JAXBContext.newInstance(new Class[] { UnCertain.class });
      InputStream is = new ByteArrayInputStream(requestBaseStr.getBytes());
      Unmarshaller ums = context.createUnmarshaller();
      unCertain = (UnCertain)ums.unmarshal(is);
    } catch (JAXBException e) {
      throw new BankException("银行请求数据错误:" + e.getMessage());
    }
    String requestName = "UnCertain";
    String responseName = null;
    if ((unCertain.GrpHdr.BusCd == BusinessCode.CODE22001.getValue()) || (unCertain.GrpHdr.BusCd == BusinessCode.CODE22002.getValue()))
      requestName = "Transfer";
    else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE20003.getValue())
      requestName = "Handshake";
    else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE21001.getValue())
      requestName = "RgstAccount";
    else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE21002.getValue())
      requestName = "DelAccount";
    else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE21003.getValue())
      requestName = "ModAccount";
    else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE21004.getValue())
      requestName = "QryAccount";
    else if ((unCertain.GrpHdr.BusCd == BusinessCode.CODE23001.getValue()) || (unCertain.GrpHdr.BusCd == BusinessCode.CODE23003.getValue()))
      requestName = "DayDataReady";
    else if ((unCertain.GrpHdr.BusCd == BusinessCode.CODE22003.getValue()) || (unCertain.GrpHdr.BusCd == BusinessCode.CODE22004.getValue()))
      requestName = "Reversal";
    else {
      requestName = "UnCertain";
    }
    responseName = requestName + "Response";

    requestXml = fmtBankXml(requestXml, Common.toLowerCaseStart(requestName));
    Object request = null;
    try {
      context = JAXBContext.newInstance(new Class[] { Class.forName("gnnt.bank.adapter.bankBusiness.info." + requestName) });
      InputStream is = new ByteArrayInputStream(requestXml.getBytes());
      Unmarshaller ums = context.createUnmarshaller();
      request = ums.unmarshal(is);
    } catch (JAXBException e) {
      throw new BankException("银行请求数据错误:" + e.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    Object response = null;
    try
    {
      if (unCertain.GrpHdr.BusCd == BusinessCode.CODE22001.getValue())
        response = this.adapter.inMoneyBank(request);
      else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE22002.getValue())
        response = this.adapter.outMoneyBank(request);
      else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE20003.getValue())
        response = this.adapter.checkComm(request);
      else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE21001.getValue())
        response = this.adapter.rgstAccount(request);
      else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE21002.getValue())
        response = this.adapter.delAccount(request);
      else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE21003.getValue())
        response = this.adapter.modAccount(request);
      else if (unCertain.GrpHdr.BusCd == BusinessCode.CODE21004.getValue())
        response = this.adapter.accountQuery(request);
      else if ((unCertain.GrpHdr.BusCd == BusinessCode.CODE23001.getValue()) || (unCertain.GrpHdr.BusCd == BusinessCode.CODE23003.getValue()))
        response = this.adapter.dayDataReady(request);
      else if ((unCertain.GrpHdr.BusCd == BusinessCode.CODE22003.getValue()) || (unCertain.GrpHdr.BusCd == BusinessCode.CODE22004.getValue()))
        response = this.adapter.reversal(request);
      else
        response = this.adapter.unCertain(unCertain, ReturnCode.CODE2033.getValue());
    }
    catch (Exception e) {
      e.printStackTrace();
      responseName = "UnCertainResponse";
      response = this.adapter.unCertain(unCertain, ReturnCode.CODE2041.getValue());
    }

    String responseXml = null;
    try {
      context = JAXBContext.newInstance(new Class[] { Class.forName("gnnt.bank.adapter.bankBusiness.info." + responseName) });
      Marshaller ms = context.createMarshaller();
      ms.setProperty("jaxb.encoding", "GBK");
      Writer writer = new StringWriter();
      ms.marshal(response, writer);
      responseXml = writer.toString();
      responseXml = fmtMktXml(responseXml);
    } catch (JAXBException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return responseXml;
  }

  private static String fmtMktXml(String _xmlStr)
  {
    String xmlStr = _xmlStr.replaceAll("<handshake>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</handshake>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<handshakeResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</handshakeResponse>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<rgstAccountResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</rgstAccountResponse>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<delAccountResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</delAccountResponse>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<modAccountResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</modAccountResponse>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<qryAccount>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</qryAccount>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<qryAccountResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</qryAccountResponse>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<transfer>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</transfer>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<transferResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</transferResponse>", "</MsgText>");
    xmlStr = xmlStr.replaceAll("<bankTransferResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</bankTransferResponse>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<bankTransfer>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</bankTransfer>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<dayDataReady>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</dayDataReady>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<dayDataReadyResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</dayDataReadyResponse>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<unCertainResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</unCertainResponse>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<reversalResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</reversalResponse>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<checkAccount>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</checkAccount>", "</MsgText>");

    xmlStr = xmlStr.replaceAll("<checkAccountResponse>", "<MsgText>");
    xmlStr = xmlStr.replaceAll("</checkAccountResponse>", "</MsgText>");
    return xmlStr;
  }

  private String fmtBankXml(String _xmlStr, String tagName)
  {
    String xmlStr = null;
    xmlStr = _xmlStr.replaceAll("<MsgText>", "<" + tagName + ">");
    xmlStr = xmlStr.replaceAll("</MsgText>", "</" + tagName + ">");
    return xmlStr;
  }

  private boolean checkBankInfo(String bankInfo)
  {
    boolean rst = true;
    if ((bankInfo == null) || (bankInfo.equals(""))) {
      rst = false;
    }
    return rst;
  }
}