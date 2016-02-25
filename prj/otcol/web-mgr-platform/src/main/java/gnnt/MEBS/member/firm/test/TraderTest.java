package gnnt.MEBS.member.firm.test;

import gnnt.MEBS.member.firm.services.FirmService;
import gnnt.MEBS.member.firm.services.TraderService;
import gnnt.MEBS.member.firm.unit.Firm;
import gnnt.MEBS.member.firm.unit.FirmLog;
import gnnt.MEBS.member.firm.unit.FirmModule;
import gnnt.MEBS.member.firm.unit.Trader;
import gnnt.MEBS.member.firm.unit.TraderModule;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TraderTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("m_firmService")
  private FirmService firmService;
  @Autowired
  @Qualifier("m_traderService")
  private TraderService traderService;
  private static final FirmLog firmLog = new FirmLog("111", "222", "333");
  
  public Trader addTrader()
  {
    Trader localTrader = new Trader();
    localTrader.setTraderId("zhenghai");
    localTrader.setFirmId("aaaa");
    localTrader.setName("gaga");
    localTrader.setStatus("N");
    localTrader.setPassword("123456");
    localTrader.setType("N");
    localTrader.setEnableKey("N");
    TraderModule localTraderModule1 = new TraderModule();
    localTraderModule1.setEnabled("N");
    localTraderModule1.setTraderId("zhenghai");
    localTraderModule1.setModuleId("2");
    TraderModule localTraderModule2 = new TraderModule();
    localTraderModule2.setEnabled("N");
    localTraderModule2.setTraderId("zhenghai");
    localTraderModule2.setModuleId("3");
    TraderModule localTraderModule3 = new TraderModule();
    localTraderModule3.setEnabled("N");
    localTraderModule3.setTraderId("zhenghai");
    localTraderModule3.setModuleId("4");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(localTraderModule1);
    localArrayList.add(localTraderModule2);
    localArrayList.add(localTraderModule3);
    localTrader.addTraderModule(localArrayList);
    return localTrader;
  }
  
  public Firm addFirm()
  {
    Firm localFirm = new Firm();
    localFirm.setFirmId("aaaa");
    localFirm.setName("5");
    localFirm.setStatus("N");
    FirmModule localFirmModule1 = new FirmModule();
    localFirmModule1.setEnabled("Y");
    localFirmModule1.setFirmId("aaaa");
    localFirmModule1.setModuleId("2");
    FirmModule localFirmModule2 = new FirmModule();
    localFirmModule2.setEnabled("Y");
    localFirmModule2.setFirmId("aaaa");
    localFirmModule2.setModuleId("3");
    FirmModule localFirmModule3 = new FirmModule();
    localFirmModule3.setEnabled("Y");
    localFirmModule3.setFirmId("aaaa");
    localFirmModule3.setModuleId("4");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(localFirmModule1);
    localArrayList.add(localFirmModule2);
    localArrayList.add(localFirmModule3);
    localFirm.addFirmModule(localArrayList);
    return localFirm;
  }
  
  public void testAddTrader()
  {
    Firm localFirm = addFirm();
    this.firmService.createFirm(localFirm, firmLog);
    Trader localTrader1 = addTrader();
    this.traderService.createTrader(localTrader1);
    Trader localTrader2 = this.traderService.getTraderById("zhenghai");
    assertEquals(localTrader1.getName(), localTrader2.getName());
    List localList = localTrader2.getTraderModule();
    String str = ((TraderModule)localList.get(0)).getModuleId();
    assertEquals("2", str);
  }
  
  public void testDelTrader()
  {
    Trader localTrader = addTrader();
    Firm localFirm = addFirm();
    this.firmService.createFirm(localFirm, firmLog);
    this.traderService.createTrader(localTrader);
    this.traderService.deleteTrader(localTrader.getTraderId(), firmLog);
    assertEquals(null, this.traderService.getTraderById(localTrader.getTraderId()));
  }
  
  public void testDelTraderSameByFirmId()
  {
    Firm localFirm = addFirm();
    this.firmService.createFirm(localFirm, firmLog);
    this.traderService.deleteTrader(localFirm.getFirmId(), firmLog);
    Trader localTrader = this.traderService.getTraderById(localFirm.getFirmId());
    int i = 0;
    if (localTrader != null) {
      i = 1;
    }
    assertEquals(1, i);
  }
  
  public void testUpdateTraderModule()
  {
    Firm localFirm = addFirm();
    this.firmService.createFirm(localFirm, firmLog);
    Trader localTrader1 = addTrader();
    this.traderService.createTrader(localTrader1);
    TraderModule localTraderModule1 = new TraderModule();
    localTraderModule1.setEnabled("N");
    localTraderModule1.setTraderId("zhenghai");
    localTraderModule1.setModuleId("2");
    TraderModule localTraderModule2 = new TraderModule();
    localTraderModule2.setEnabled("N");
    localTraderModule2.setTraderId("zhenghai");
    localTraderModule2.setModuleId("3");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(localTraderModule1);
    localArrayList.add(localTraderModule2);
    localTrader1.addTraderModule(localArrayList);
    this.traderService.updateTrader(localTrader1);
    Trader localTrader2 = this.traderService.getTraderById(localTrader1.getTraderId());
    int i = localTrader2.getTraderModule().size();
    assertEquals(2, i);
  }
  
  public void testUpdateTraderStatus()
  {
    Firm localFirm = addFirm();
    this.firmService.createFirm(localFirm, firmLog);
    Trader localTrader1 = addTrader();
    this.traderService.createTrader(localTrader1);
    Trader localTrader2 = this.traderService.getTraderById(localTrader1.getTraderId());
    localTrader2.setStatus("D");
    int i = this.traderService.setStatusTrader(localTrader2, firmLog);
    assertEquals(1, i);
    Trader localTrader3 = this.traderService.getTraderById(localTrader2.getTraderId());
    String str = localTrader3.getStatus();
    assertEquals("D", str);
  }
  
  public void testUpdateTraderPassword()
  {
    Firm localFirm = addFirm();
    this.firmService.createFirm(localFirm, firmLog);
    Trader localTrader1 = addTrader();
    this.traderService.createTrader(localTrader1);
    this.traderService.changePwdTrader(localTrader1.getTraderId(), "abcdefg");
    Trader localTrader2 = this.traderService.getTraderById(localTrader1.getTraderId());
    String str = localTrader2.getPassword();
    assertEquals("abcdefg", str);
  }
}
