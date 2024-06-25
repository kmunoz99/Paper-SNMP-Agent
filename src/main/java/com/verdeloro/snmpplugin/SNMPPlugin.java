package com.verdeloro.snmpplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class SNMPPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(this, this);
        try {

            // create an agent to listen at localhost:12345
            SNMPAgent snmpAgent = new SNMPAgent("0.0.0.0/12345");

            // actually start listening
            snmpAgent.start();

            // register the custom mib information
            snmpAgent.registerCustomMIB();

            System.out.println("SNMP agent listening on port 12345");

            // just keep running the process
            // in a regular scenario the agent will be instantiated in a living process

        } catch (Exception e) {
            System.out.println("Failed to start SNMP agent on port 12345 : " + e.getMessage());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
    }

}