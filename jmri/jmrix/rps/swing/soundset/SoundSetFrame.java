// SoundSetFrame.java
 
package jmri.jmrix.rps.swing.soundset;

import jmri.jmrix.rps.*;
import javax.swing.BoxLayout;

/**
 * Frame for controlling sound-speed calculation for RPS system.
 *
 * @author	   Bob Jacobsen   Copyright (C) 2008
 * @version   $Revision: 1.1 $
 */


public class SoundSetFrame extends jmri.util.JmriJFrame {

    public SoundSetFrame() {
        super();
        setTitle(title());
    }

    protected String title() { return "RPS Sound Speed Control"; }  // product name, not translated

    SoundSetPane pane;

    public void dispose() {
        if (pane!=null) pane.dispose();
        // and unwind swing
        super.dispose();
    }

    public void initComponents() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        // add pane
        pane = new SoundSetPane();
        pane.initComponents();
        getContentPane().add(pane);
         
        // add help
        addHelpMenu("package.jmri.jmrix.rps.swing.soundset.SoundSetFrame", true);

        // prepare for display
        pack();
    }
}
