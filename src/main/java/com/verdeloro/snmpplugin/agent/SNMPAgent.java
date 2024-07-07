package com.verdeloro.snmpplugin.agent;

import org.snmp4j.TransportMapping;
import org.snmp4j.agent.*;
import org.snmp4j.agent.mo.snmp.*;
import org.snmp4j.agent.security.MutableVACM;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.security.USM;
import org.snmp4j.smi.*;
import org.snmp4j.transport.TransportMappings;

import java.io.File;
import java.io.IOException;

public class SNMPAgent extends BaseAgent {

    private String address;
    private String community;
    private final String baseOID;

    public SNMPAgent(String address, String community, String baseOID) throws IOException {
        super(
                new File("conf.agent"),
                new File("bootCounter.agent"),
                new CommandProcessor(new OctetString(MPv3.createLocalEngineID()))
        );
        this.address = address;
        this.community = community;
        this.baseOID = baseOID;
    }


    @Override
    protected void addCommunities(SnmpCommunityMIB communityMIB) {

        Variable[] com2sec = new Variable[]{
                new OctetString(this.community),
                new OctetString("notConfigUser"), // security name
                getAgent().getContextEngineID(), // local engine ID
                new OctetString(), // default context name
                new OctetString(), // transport tag
                new Integer32(StorageType.nonVolatile), // storage type
                new Integer32(RowStatus.active) // row status
        };

        SnmpCommunityMIB.SnmpCommunityEntryRow row =
                communityMIB.getSnmpCommunityEntry().createRow(new OctetString("notConfigUser").toSubIndex(true), com2sec);

        communityMIB.getSnmpCommunityEntry().addRow(row);
    }


    @Override
    protected void addNotificationTargets(SnmpTargetMIB arg0, SnmpNotificationMIB arg1) {
    }


    @Override
    protected void addUsmUser(USM arg0) {
    }

    /**
     * Adds initial VACM configuration.
     */
    @Override
    protected void addViews(VacmMIB vacm) {

        vacm.addGroup(
                SecurityModel.SECURITY_MODEL_SNMPv1,
                new OctetString("notConfigUser"),
                new OctetString("notConfigGroup"),
                StorageType.nonVolatile
        );

        vacm.addGroup(
                SecurityModel.SECURITY_MODEL_SNMPv2c,
                new OctetString("notConfigUser"),
                new OctetString("notConfigGroup"),
                StorageType.nonVolatile
        );

        vacm.addAccess(
                new OctetString("notConfigGroup"),
                new OctetString(),
                SecurityModel.SECURITY_MODEL_ANY,
                SecurityLevel.NOAUTH_NOPRIV,
                MutableVACM.VACM_MATCH_EXACT,
                new OctetString("systemview"),
                new OctetString(),
                new OctetString(),
                StorageType.nonVolatile
        );

        vacm.addViewTreeFamily(
                new OctetString("systemview"),
                new OID(this.baseOID),
                new OctetString(),
                VacmMIB.vacmViewIncluded,
                StorageType.nonVolatile
        );
    }


    @Override
    protected void unregisterManagedObjects() {
    }


    @Override
    protected void registerManagedObjects() {
    }

    protected void initTransportMappings() throws IOException {

        transportMappings = new TransportMapping[1];
        transportMappings[0] = TransportMappings.getInstance().createTransportMapping(GenericAddress.parse(address));
    }


    public void start() throws IOException {
        init();
        addShutdownHook();
        getServer().addContext(new OctetString(this.community));
        finishInit();
        run();
        sendColdStartNotification();
    }

    /**
     * Clients can register the MO they need
     */
    public void registerManagedObject(ManagedObject mo) {
        try {
            server.register(mo, null);
        } catch (DuplicateRegistrationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void unregisterManagedObject(MOGroup moGroup) {
        moGroup.unregisterMOs(server, getContext(moGroup));
    }

    public void registerCustomMIB() {


        unregisterManagedObject(getSnmpv2MIB());
        String customMibOid = String.format(".%s", this.baseOID);

        registerManagedObject(ManagedObjectFactory.createReadOnly(customMibOid + ".1.1.0", "RamUsage"));
        registerManagedObject(ManagedObjectFactory.createReadOnly(customMibOid + ".1.2.0", "PlayerCount"));
        registerManagedObject(ManagedObjectFactory.createReadOnly(customMibOid + ".1.3.0", "CpuUsage"));
        registerManagedObject(ManagedObjectFactory.createReadOnly(customMibOid + ".1.4.0", "TotalWorldSize"));
        registerManagedObject(ManagedObjectFactory.createReadOnly(customMibOid + ".1.5.0", "OverWorldSize"));
        registerManagedObject(ManagedObjectFactory.createReadOnly(customMibOid + ".1.6.0", "NetherWorldSize"));
        registerManagedObject(ManagedObjectFactory.createReadOnly(customMibOid + ".1.7.0", "EndWorldSize"));


    }
}
