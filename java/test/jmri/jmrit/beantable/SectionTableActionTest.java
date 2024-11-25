package jmri.jmrit.beantable;

import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JTextField;

import jmri.InstanceManager;
import jmri.Section;
import jmri.util.JUnitUtil;
import jmri.util.swing.JemmyUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.netbeans.jemmy.operators.*;

/**
 *
 * @author Paul Bender Copyright (C) 2017
 */
public class SectionTableActionTest extends AbstractTableActionBase<Section> {

    @Test
    public void testCTor() {
        Assert.assertNotNull("exists", a);
    }

    @Override
    public String getTableFrameName() {
        return Bundle.getMessage("TitleSectionTable");
    }

    @Override
    @Test
    public void testGetClassDescription() {
        Assert.assertEquals("Section Table Action class description", "Section Table", a.getClassDescription());
    }

    /**
     * Check the return value of includeAddButton. The table generated by this
     * action includes an Add Button.
     */
    @Override
    @Test
    public void testIncludeAddButton() {
        Assert.assertTrue("Default include add button", a.includeAddButton());
    }

    @Override
    public String getAddFrameName() {
        return Bundle.getMessage("TitleAddSection");
    }

    @Test
    @Override
    @DisabledIfSystemProperty( named = "java.awt.headless", matches = "true" )
    public void testAddThroughDialog() {

        Assertions.assertTrue(a.includeAddButton());
        a.actionPerformed(null);
        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        JemmyUtil.pressButton(new JFrameOperator(f), Bundle.getMessage("ButtonAdd"));
        new org.netbeans.jemmy.QueueTool().waitEmpty();
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        JFrameOperator jf = new JFrameOperator(f1);
        //Enter 1 in the text field labeled "System Name:"
        JLabelOperator jlo = new JLabelOperator(jf, Bundle.getMessage("LabelSystemName"));
        ((JTextField) jlo.getLabelFor()).setText("1");
        //press the "Add Selected Block" button to add the only defined block
        JemmyUtil.pressButton(jf, ResourceBundle.getBundle("jmri.jmrit.beantable.SectionTransitTableBundle").getString("AddBlockButton"));
        //and press create
        JemmyUtil.pressButton(jf, Bundle.getMessage("ButtonCreate"));
        
        JUnitUtil.dispose(f1);
        JUnitUtil.dispose(f);
    }

    @Test
    @Override
    @DisabledIfSystemProperty( named = "java.awt.headless", matches = "true" )
    public void testEditButton() {

        Assertions.assertTrue(a.includeAddButton());
        a.actionPerformed(null);
        JFrame f = JFrameOperator.waitJFrame(getTableFrameName(), true, true);

        // find the "Add... " button and press it.
        JemmyUtil.pressButton(new JFrameOperator(f), Bundle.getMessage("ButtonAdd"));
        JFrame f1 = JFrameOperator.waitJFrame(getAddFrameName(), true, true);
        JFrameOperator jf = new JFrameOperator(f1);
        //Enter 1 in the text field labeled "System Name:"

        JLabelOperator jlo = new JLabelOperator(jf, Bundle.getMessage("LabelSystemName"));
        ((JTextField) jlo.getLabelFor()).setText("1");
        //press the "Add Selected Block" button to add the only defined block
        JemmyUtil.pressButton(jf, ResourceBundle.getBundle("jmri.jmrit.beantable.SectionTransitTableBundle").getString("AddBlockButton"));
        //and press create
        JemmyUtil.pressButton(jf, Bundle.getMessage("ButtonCreate"));

        JFrameOperator jfo = new JFrameOperator(f);

        JTableOperator tbl = new JTableOperator(jfo, 0);
        // find the "Edit" button and press it.  This is in the table body.
        tbl.clickOnCell(0, BeanTableDataModel.NUMCOLUMN + 2);
        JFrame f2 = JFrameOperator.waitJFrame(getEditFrameName(), true, true);
        JemmyUtil.pressButton(new JFrameOperator(f2), Bundle.getMessage("ButtonCancel"));
        JUnitUtil.dispose(f2);
        JUnitUtil.dispose(f1);
        JUnitUtil.dispose(f);
    }

    @Override
    public String getEditFrameName() {
        return "Edit Section";
    }

    @Override
    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        JUnitUtil.resetProfileManager();
        helpTarget = "package.jmri.jmrit.beantable.SectionTable";
        InstanceManager.setDefault(jmri.BlockManager.class, new jmri.BlockManager());
        a = new SectionTableAction();
        InstanceManager.getDefault(jmri.BlockManager.class).provideBlock("IB12")
            .setUserName("Block 12 userName");

    }

    @Override
    @AfterEach
    public void tearDown() {
        if ( a != null ){ 
            a.dispose();
            a = null;
        }
        JUnitUtil.deregisterBlockManagerShutdownTask();
        JUnitUtil.tearDown();
    }

    // private final static Logger log = LoggerFactory.getLogger(SectionTableActionTest.class);
}
