package jmri.jmrix.mqtt;

import jmri.Turnout;

/**
 * Implement turnout manager for MQTT systems
 * <P>
 * System names are "MTnnn", where nnn is the turnout number without padding.
 *
 * @author Lionel Jeanson Copyright: Copyright (c) 2017
 */
public class MqttTurnoutManager extends jmri.managers.AbstractTurnoutManager {
    private final MqttAdapter mqttAdapter;
    private final String systemPrefix;

    MqttTurnoutManager(MqttAdapter ma, String p) {
        super();
        mqttAdapter = ma;
        systemPrefix = p;        
    }

    @Override
    public String getSystemPrefix() {
        return systemPrefix;
    }

    @Override
    public Turnout createNewTurnout(String systemName, String userName) {
        Turnout t;
        int addr = Integer.parseInt(systemName.substring(systemPrefix.length() + 1));
        t = new MqttTurnout(mqttAdapter, addr);
        t.setUserName(userName);

        return t;
    }

    /** {@inheritDoc} */
    @Override
    public int getOutputInterval(String systemName) {
        if (mqttAdapter.getSystemConnectionMemo() != null) {
            return mqttAdapter.getSystemConnectionMemo().getOutputInterval();
        } else {
            return 0;
        }
    }

}
