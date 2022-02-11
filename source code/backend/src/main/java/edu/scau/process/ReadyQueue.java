package edu.scau.process;
/**
 * 进程的就绪队列，数组里存的是pid，
* */

import java.util.Iterator;
import java.util.LinkedList;

public class ReadyQueue {
    public static LinkedList<Integer> readyQueue=new LinkedList<Integer>();

    //将进程加入到队列末尾
    public static void add(int pid){readyQueue.addLast(pid);}

    //将队列头移出队列
    public static Integer remove(){
        return readyQueue.removeFirst();
    }

    //返回队列头的进程的pid
    public static Integer get(){
        return readyQueue.getFirst();
    }

    //Test
    public static void print()
    {
        Iterator iterator=readyQueue.iterator();
        while(iterator.hasNext())
        {
            System.out.println(iterator.next());
        }
    }


    public static LinkedList<Integer> getReadyQueue() {
        return readyQueue;
    }
}
