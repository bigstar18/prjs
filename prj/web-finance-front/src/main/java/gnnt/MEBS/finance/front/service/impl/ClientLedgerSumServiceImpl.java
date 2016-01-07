package gnnt.MEBS.finance.front.service.impl;

import gnnt.MEBS.finance.front.service.ClientLedgerSumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("clientLedgerSumService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ClientLedgerSumServiceImpl implements ClientLedgerSumService {
}