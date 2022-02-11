package edu.scau.main;

import edu.scau.memory.Hole;
import edu.scau.memory.Memory;
import edu.scau.process.BlockedQueue;
import edu.scau.process.PCB;
import edu.scau.process.PCBManager;
import edu.scau.process.ReadyQueue;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

import static edu.scau.main.Manager.*;

class MemoryUnit {
    public int num = 0;
    public boolean used = false;

    public MemoryUnit(int num, boolean used) {
        this.num = num;
        this.used = used;
    }
}

class ProcessInfo {
    public PCB[] pcbs;
    public List<Integer> readyQueue;
    public List<Integer> blockedQueue;
    public int[] deviceAQueue;
    public int[] deviceBQueue;
    public int[] deviceCQueue;
    //    public List<RequestOfDevice> deviceWaitingQueue;
    public int occupyMemory;        //所有进程占用的总内存
    public List<Hole> holeList;	    //所有进程的内存集合
    public List<MemoryUnit> mem;
}

public class CpuHandler {
    // 消息类，用于返回错误信息
    private static class Msg {
        public String text;
    }

    private static final Msg msg = new Msg();
    private static final ProcessInfo processInfo = new ProcessInfo();

    //    static {
//        processInfo.mem = new ArrayList<>(512);
//        for (int i=0; i<512; i++) {
//            processInfo.mem.add(new MemoryUnit(0, false));
//        }
//    }
    private static ProcessInfo updateProcessInfo() {
        processInfo.pcbs = PCBManager.getPcbs();
        processInfo.readyQueue = ReadyQueue.getReadyQueue();
        processInfo.blockedQueue = BlockedQueue.getBlockedQueue();

        processInfo.deviceAQueue = dm.Adevice();
        processInfo.deviceBQueue = dm.Bdevice();
        processInfo.deviceCQueue = dm.Cdevice();

//        processInfo.deviceWaitingQueue = dm.getA_waiting();

//        MemoryManagement management=MemoryManagement.getInstance();
//        Automgr automgr=management.getMgr();
//        Memory memory=automgr.getMemory();
        Memory memory = mgr.getMemory();
        List<Hole> holes = memory.getHoles();
//        holes.removeIf(Hole::isFree);
//        processInfo.holeList = holes.stream().filter(hole -> !hole.isFree()).collect(Collectors.toList());
        processInfo.holeList = holes;

        processInfo.mem = new ArrayList<>(512);
        for (int i = 0; i < 512; i++)
            processInfo.mem.add(new MemoryUnit(10, false));
        for (int i = 0; i < holes.size(); i++) {
            if (!holes.get(i).isFree()) {
                for (int j = 0; j < holes.get(i).getSize(); j++) {
                    processInfo.mem.get(holes.get(i).getHead() + j).num = i;
                    processInfo.mem.get(holes.get(i).getHead() + j).used = true;
                }
            }
        }


        processInfo.occupyMemory = 0;
        for (Hole hole : holes) {
            if (!hole.isFree())
                processInfo.occupyMemory += hole.getSize();
        }
        return processInfo;
    }

    public static void execute(Context ctx) {
        String content = ctx.formParam("content").trim();
        if (!content.endsWith("end")) {
            content = content + "\nend";
        }
        String[] instructions = content.split("\\r?\\n");
        try {
            if (pm.create(instructions) == 0) {
                ctx.json(updateProcessInfo());
            } else {
                msg.text = "创建失败，请勿创建超过10个进程！";
                ctx.json(msg);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void rr(Context ctx) {
        try {
            pm.RR();
            ctx.json(updateProcessInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getinfo(Context ctx) {
        try {
            ctx.json(updateProcessInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
