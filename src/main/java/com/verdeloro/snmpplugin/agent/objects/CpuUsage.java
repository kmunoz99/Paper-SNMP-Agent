package com.verdeloro.snmpplugin.agent.objects;


import org.snmp4j.agent.MOAccess;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;


public class CpuUsage<V extends Variable> extends CustomManagedObject {

    public CpuUsage(OID oid, MOAccess access) {
        super(oid, access, null);
    }

    @Override
    public V getValue() {

        // Get the operating system MXBean
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        // Get the process CPU load
        double processCpuLoad = osBean.getProcessCpuLoad();

        // Convert it to a percentage
        int result =  (int)processCpuLoad * 100;

        return (V) (new Integer32(result));

    }

}

