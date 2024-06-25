package com.verdeloro.snmpplugin.objects;

import org.bukkit.Bukkit;
import org.snmp4j.agent.MOAccess;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

public class PlayerCount<V extends Variable> extends CustomManagedObject {

    public PlayerCount(OID oid, MOAccess access) {
        super(oid, access, null);
    }

    @Override
    public V getValue() {

        int result = Bukkit.getOnlinePlayers().size();
        return (V) (new Integer32(result));

    }

}
