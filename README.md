# SNMP-Agent  

This plugins opens a simple SNMP v2c agent on paper server.


## Configuration.

```yml
port: 12345
community: public
host: 0.0.0.0
oid: 1.3.6.1.4.1.12345
```

## OIDS


| OID  | Value  | Unit  | 
| -------- | -------- | -------- | 
| .1.1.0     | JVM RAM Usage     |  Bytes | 
| .1.2.0     | Connected Players   | Players  | 
| .1.3.0     | JVM CPU Usage   |  % | 
| .1.4.0     | Total Worlds Size    |  MBs| 
| .1.5.0     | Overworld Size    | MBs | 
| .1.6.0     | Nether Size    |  MBs| 
| .1.7.0     | The End Size    | MBs  | 

## SNMPWalk example
![](https://i.ibb.co/W65ZC9t/snmpwalk.png)
