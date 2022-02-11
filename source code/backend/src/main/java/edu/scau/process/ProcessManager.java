package edu.scau.process;

import edu.scau.device.DeviceManagement;
import edu.scau.memory.Automgr;
import edu.scau.memory.MemoryManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * 进程管理主要类，采用单例模式
 *
* */
public class ProcessManager {
    private static ProcessManager instance=null;
    private DeviceManagement deviceManagement;
    private ProcessManager(){
        PCBManager.initialize();
        this.deviceManagement=new DeviceManagement();
    }

    public static ProcessManager getInstance(){
        if(instance==null){instance=new ProcessManager();}
        return instance;
    }

    //创建进程，并将其加入就绪队列,返回-1即为创建失败，返回0为成功
    public int create(String[] instructions){
        PCB pcb;
        if((pcb=PCBManager.newPcb())==null){return -1;};//申请空白pcb
        Automgr mgr=MemoryManagement.getInstance().getMgr();//申请主存空间
        mgr.FirstFit1(mgr.getMemory(), instructions.length, pcb.getPid());
        if(!mgr.getMemory().isFlag()){return -1;}//内存是否够用
        pcb.initialize(instructions);//初始化进程控制块
        return 0;
    }

    //销毁进程
    public boolean destroy(int pid){
        Automgr mgr=MemoryManagement.getInstance().getMgr();
        mgr.releaseMemory1(pid);//回收进程所占内存
        PCBManager.withdrawPcb(pid);//回收进程控制块
        //在屏幕上显示进程执行结果，进程撤销
        return true;
    }

    //阻塞进程
    public void block(int pid){
        PCB pcb=PCBManager.getPcbs()[pid];
        pcb.setAx(Registers.getAx());    //保存现场
        pcb.setIr(Registers.getIr());
        pcb.setPc(Registers.getPc());
        pcb.setLife(0);
        pcb.setProcessState(3);//修改状态
        ReadyQueue.remove();//将进程加入阻塞队列，然后转向进程调度
        BlockedQueue.add(pid);
        //然后转向进程调度
    }

    //唤醒进程
    public boolean awake(int pid){
        /*进程唤醒的主要工作是将进程由阻塞队列中摘下，
        *修改进程状态为就绪，然后链入就绪队列
        */
        BlockedQueue.remove(pid);
        PCB pcb=PCBManager.getPcbs()[pid];
        pcb.setProcessState(1);
        ReadyQueue.add(pid);
        return true;
    }

    //轮转调度算法
    public void RR(){
        int pid;
        PCB pcb;

        //首先检查有无终止态的进程,有的话销毁
        for(int i=0;i<10;i++)
        {
            pcb=PCBManager.getPcbs()[i];
            if(pcb.getProcessState()==-1)
            {
                destroy(i);
                ReadyQueue.remove();

            }
        }

        List<Integer> deviceOverPid;//定义：设备使用完毕的pid列表
        deviceOverPid = deviceManagement.dectime();//设备里所有进程占用时间--

        //是否有设备使用完毕的的进程,有的话把他们从阻塞队列移动到就绪队列
        if(deviceOverPid!=null&&deviceOverPid.size()!=0)
        {
            for(int i=0;i<deviceOverPid.size();i++)
            {
                ReadyQueue.add(deviceOverPid.get(i));
                pcb=PCBManager.getPcbs()[deviceOverPid.get(i)];
                pcb.setProcessState(1);
                BlockedQueue.remove(deviceOverPid.get(i));
//                System.out.println("deviceOverPid.get(i):"+deviceOverPid.get(i));
            }
            System.out.println("--------------------");
            ReadyQueue.getReadyQueue().forEach(System.out::print);
            System.out.println();
            BlockedQueue.getBlockedQueue().forEach(System.out::print);
            System.out.println("--------------------");
        }



        //从就绪队列中选择一个进程，如果就绪队列为空，则RR结束
        if(ReadyQueue.getReadyQueue().isEmpty())
            return;
        pid=ReadyQueue.get();
        pcb=PCBManager.getPcbs()[pid];
        pcb.setProcessState(2);

        //恢复上下文
        Registers.setAx(pcb.getAx());
        Registers.setPc(pcb.getPc());
        Registers.setIr(pcb.getIr());
        pcb.setLife(1);//时间片




        //上cpu运行,flag=-1表示发生阻塞
        int flag=CPU.cpu(pid,deviceManagement);

        pcb.setAx(Registers.getAx());     //保存上下文
        pcb.setIr(Registers.getIr());
        pcb.setPc(Registers.getPc());


        if(flag==-1) {
            pcb.setProcessState(3);
            ReadyQueue.remove();
            BlockedQueue.add(pid);
        }else if(flag==-2)
        {
            pcb.setProcessState(-1);
        //    ReadyQueue.remove();
        }
        else{
            pcb.setProcessState(1);//就绪态，并加入就绪队尾
            ReadyQueue.remove();
            ReadyQueue.add(pid);
        }



    }

    //返回一个deviceManagement的实例对象
    public DeviceManagement getDeviceManagement() {
        return deviceManagement;
    }
}
