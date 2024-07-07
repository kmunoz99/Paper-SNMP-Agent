package com.verdeloro.snmpplugin;

import com.verdeloro.snmpplugin.agent.SNMPAgent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class SNMPPlugin extends JavaPlugin implements Listener {
    private SNMPAgent snmpAgent;
    @Override
    public void onDisable()
    {
        this.snmpAgent.stop();

    }

    @Override
    public void onEnable() {

        saveDefaultConfig();
        FileConfiguration config = getConfig();

        int port = config.getInt("port");
        String community = config.getString("community");
        String host = config.getString("host");
        String baseOID = config.getString("oid");

        //Bukkit.getPluginManager().registerEvents(this, this);
        try {
            this.snmpAgent = new SNMPAgent(String.format("%s/%d",host, port), community, baseOID);
            snmpAgent.start();
            snmpAgent.registerCustomMIB();

        } catch (Exception e) {
            System.out.println("Failed to start SNMP agent : " + e.getMessage());
        }
    }

}