package edu.scau.main;

import edu.scau.device.DeviceManagement;
import edu.scau.disk.FileManager;
import edu.scau.memory.Automgr;
import edu.scau.memory.MemoryManagement;
import edu.scau.process.ProcessManager;

public class Manager {
    public static FileManager fm = null;
    public static ProcessManager pm = null;
    public static MemoryManagement mm = null;
    public static Automgr mgr = null;
    public static DeviceManagement dm = null;

    public static void init() {
        fm = FileManager.getInstance();
        pm = ProcessManager.getInstance();
        mgr = MemoryManagement.getInstance().getMgr();//申请主存空间
        mgr.setMemory1(mgr.getMemory(), 512);
        dm = pm.getDeviceManagement();
        System.out.println("mgr"+mgr);
        System.out.println("mgr"+MemoryManagement.getInstance().getMgr().getMemory());
    }
}
