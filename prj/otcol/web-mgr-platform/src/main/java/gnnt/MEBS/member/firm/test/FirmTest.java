package gnnt.MEBS.member.firm.test;

import gnnt.MEBS.member.firm.services.FirmService;
import gnnt.MEBS.member.firm.services.TraderService;
import gnnt.MEBS.member.firm.unit.Firm;
import gnnt.MEBS.member.firm.unit.FirmLog;
import gnnt.MEBS.member.firm.unit.FirmModule;
import gnnt.MEBS.member.firm.unit.Trader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

public class FirmTest
  extends AbstractTest
{
  @Autowired
  @Qualifier("m_firmService")
  private FirmService firmService;
  @Autowired
  @Qualifier("m_traderService")
  private TraderService traderService;
  private static final FirmLog firmLog = new FirmLog("111", "222", "333");
  
  public void testAddFirm()
  {
    Firm localFirm = new Firm();
    localFirm.setFirmId("001");
    localFirm.setName("5");
    localFirm.setStatus("N");
    FirmModule localFirmModule1 = new FirmModule();
    localFirmModule1.setEnabled("N");
    localFirmModule1.setFirmId("001");
    localFirmModule1.setModuleId("2");
    FirmModule localFirmModule2 = new FirmModule();
    localFirmModule2.setEnabled("N");
    localFirmModule2.setFirmId("001");
    localFirmModule2.setModuleId("3");
    FirmModule localFirmModule3 = new FirmModule();
    localFirmModule3.setEnabled("N");
    localFirmModule3.setFirmId("001");
    localFirmModule3.setModuleId("4");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(localFirmModule1);
    localArrayList.add(localFirmModule2);
    localArrayList.add(localFirmModule3);
    localFirm.addFirmModule(localArrayList);
    int i = this.firmService.createFirm(localFirm, firmLog);
    assertEquals(-2, i);
  }
  
  public void testAddFirm2()
  {
    Firm localFirm1 = addFirm();
    this.firmService.createFirm(localFirm1, firmLog);
    Firm localFirm2 = this.firmService.getFirmById("005");
    assertEquals(localFirm1.getName(), localFirm2.getName());
    List localList = localFirm2.getFirmModule();
    String str = ((FirmModule)localList.get(0)).getModuleId();
    assertEquals("2", str);
  }
  
  public void testUpdateFirmModule()
  {
    Firm localFirm = addFirm();
    this.firmService.createFirm(localFirm, firmLog);
    FirmModule localFirmModule1 = new FirmModule();
    localFirmModule1.setEnabled("N");
    localFirmModule1.setFirmId("005");
    localFirmModule1.setModuleId("2");
    FirmModule localFirmModule2 = new FirmModule();
    localFirmModule2.setEnabled("N");
    localFirmModule2.setFirmId("005");
    localFirmModule2.setModuleId("3");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(localFirmModule1);
    localArrayList.add(localFirmModule2);
    localFirm.addFirmModule(localArrayList);
    this.firmService.updateFirm(localFirm);
    Trader localTrader = this.traderService.getTraderById(localFirm.getFirmId());
    int i = localTrader.getTraderModule().size();
    assertEquals(2, i);
  }
  
  public void testUpdateFirmStatus()
  {
    Firm localFirm1 = addFirm();
    int i = this.firmService.createFirm(localFirm1, firmLog);
    assertEquals(1, i);
    this.firmService.createFirm(localFirm1, firmLog);
    Firm localFirm2 = this.firmService.getFirmById(localFirm1.getFirmId());
    assertEquals(localFirm1.getName(), localFirm2.getName());
    int j = this.jdbcTemplate.queryForInt("select count(*) from m_firm where firmid='" + localFirm1.getFirmId() + "'");
    assertEquals(1, j);
    Trader localTrader = this.traderService.getTraderById(localFirm1.getFirmId());
    String str = localTrader.getStatus();
    assertEquals("N", str);
    localFirm1.setStatus("E");
    int k = this.firmService.setStatusFirm(localFirm1, firmLog);
    assertEquals(1, k);
    localFirm1 = this.firmService.getFirmById(localFirm1.getFirmId());
    assertEquals("E", localFirm1.getStatus());
    localTrader = this.traderService.getTraderById(localFirm1.getFirmId());
    str = localTrader.getStatus();
    assertEquals("D", str);
  }
  
  public void testDelFirm()
  {
    Firm localFirm = addFirm();
    localFirm.setStatus("E");
    this.firmService.createFirm(localFirm, firmLog);
    this.firmService.deleteFirm(localFirm, firmLog);
    assertEquals(null, this.traderService.getTraderById(localFirm.getFirmId()));
    assertEquals(null, this.firmService.getFirmById(localFirm.getFirmId()));
  }
  
  public Firm addFirm()
  {
    Firm localFirm = new Firm();
    localFirm.setFirmId("005");
    localFirm.setName("5");
    localFirm.setStatus("N");
    FirmModule localFirmModule1 = new FirmModule();
    localFirmModule1.setEnabled("N");
    localFirmModule1.setFirmId("005");
    localFirmModule1.setModuleId("2");
    FirmModule localFirmModule2 = new FirmModule();
    localFirmModule2.setEnabled("N");
    localFirmModule2.setFirmId("005");
    localFirmModule2.setModuleId("3");
    FirmModule localFirmModule3 = new FirmModule();
    localFirmModule3.setEnabled("N");
    localFirmModule3.setFirmId("005");
    localFirmModule3.setModuleId("4");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(localFirmModule1);
    localArrayList.add(localFirmModule2);
    localArrayList.add(localFirmModule3);
    localFirm.addFirmModule(localArrayList);
    return localFirm;
  }
}
