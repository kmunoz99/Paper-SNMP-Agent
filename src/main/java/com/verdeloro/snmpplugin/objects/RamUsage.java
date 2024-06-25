package com.verdeloro.snmpplugin.objects;


import org.snmp4j.agent.MOAccess;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;




public class RamUsage<V extends Variable> extends CustomManagedObject {

    public RamUsage(OID oid, MOAccess access) {
        super(oid, access, null);
    }

    @Override
    public V getValue() {

        int mb = 1024 * 1024;
        Runtime instance = Runtime.getRuntime();
        int result = Math.toIntExact((instance.totalMemory() - instance.freeMemory()) / mb);

        return (V) (new Integer32(result));

    }

}
