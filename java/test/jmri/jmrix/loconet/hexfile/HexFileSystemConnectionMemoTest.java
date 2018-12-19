package jmri.jmrix.loconet.hexfile;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Before;
import jmri.jmrix.loconet.LocoNetInterfaceScaffold;

/**
 *
 * @author Paul Bender Copyright (C) 2017	
 */
public class HexFileSystemConnectionMemoTest extends jmri.jmrix.SystemConnectionMemoTestBase {


    // The minimal setup for log4J
    @Override
    @Before
    public void setUp() {
       JUnitUtil.setUp();
       LocoNetInterfaceScaffold lnis = new LocoNetInterfaceScaffold();
       HexFileSystemConnectionMemo memo = new HexFileSystemConnectionMemo();
       memo.setLnTrafficController(lnis);
       memo.configureCommandStation(jmri.jmrix.loconet.LnCommandStationType.COMMAND_STATION_DCS100,false,false,false);
       memo.configureManagers();
       scm = memo;
    }
   
    @Override
    @After
    public void tearDown() {
        ((HexFileSystemConnectionMemo)scm).dispose();
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(LocoNetSystemConnectionMemoTest.class);

}
