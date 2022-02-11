package edu.scau;

import static org.junit.Assert.assertTrue;

import edu.scau.process.*;
import org.junit.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );

        String[] instructions={"","","",""};
        ProcessManager processManager=ProcessManager.getInstance();
        processManager.create(instructions);             //新建进程
        processManager.RR();                             //执行一次时间片

        List<Integer> list1=ReadyQueue.getReadyQueue();     //获取就绪队列
        List<Integer> list2= BlockedQueue.getBlockedQueue();        //获取阻塞队列

        //获取正在进行的进程信息
        Registers.getPc();
        Registers.getAx();
        Registers.getIr();

        //获取进程信息
        PCB[] pcbs=PCBManager.getPcbs();
        for(int i=0;i< pcbs.length;i++)
        {
            System.out.println(pcbs[i].getPid());
            System.out.println(pcbs[i].getProcessState());
        }
    }
}
