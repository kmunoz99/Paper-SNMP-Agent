package com.verdeloro.snmpplugin.agent.objects;


import org.bukkit.Bukkit;
import org.bukkit.World;
import org.snmp4j.agent.MOAccess;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;


public class OverWorldSize<V extends Variable> extends CustomManagedObject {
    private Utils utils;
    public OverWorldSize(OID oid, MOAccess access) {
        super(oid, access, null);
        this.utils = new Utils();
    }

    @Override
    public V getValue() {

        return (V) (new Integer32(this.utils.getWorldSize(World.Environment.NORMAL)));

    }





}

