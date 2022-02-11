package edu.scau.process;

import edu.scau.memory.Pcb;

/**
 * 管理和维护pcb
 * 根据文档要求，最多有10个pcb，即最多有10个进程
 * pcb数组的下标即为pid
 */
public class PCBManager {

    private static PCB[] pcbs=new PCB[10];  //pcb数组，下标和pid对应起来
    private static int[] flags=new int[10]; //标志pcb是否被占用，0为未被占用，1为被占用

    //pcb数组的初始化
    public static void initialize(){
        for(int item:flags)
        {
            item=0;
        }
        for(int i=0;i<10;i++)
        {
            pcbs[i]=new PCB();
            pcbs[i].setPid(i);
        }
    }

    //申请空白pcb
    public static PCB newPcb(){
        for (int i=0;i<10;i++)
        {
            if(flags[i]==0)
            {
                flags[i]=1;
                return pcbs[i];
            }
        }
        return null;
    }

    //回收进程控制块
    public static void withdrawPcb(int pid){
        flags[pid]=0;
        pcbs[pid].setProcessState(0);
        pcbs[pid].setLife(0);
        pcbs[pid].setAx(0);
        pcbs[pid].setInstructions(null);
        pcbs[pid].setIr(null);
        pcbs[pid].setLife(0);
        pcbs[pid].setPc(0);
    }

    public static PCB[] getPcbs() {
        return pcbs;
    }

    public static void setPcbs(PCB[] pcbs) {
        PCBManager.pcbs = pcbs;
    }

    public static int[] getFlags() {
        return flags;
    }

    public static void setFlags(int[] flags) {
        PCBManager.flags = flags;
    }
}
