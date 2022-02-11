package edu.scau.process;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 进程的阻塞队列，数组里存的是pid，
 *
 * */
public class BlockedQueue {
        private static LinkedList<Integer> blockedQueue=new LinkedList<Integer>();

        //将进程添加到阻塞队列末尾
        public static void add(int pid){
            blockedQueue.addLast(pid);
        }

        //将阻塞队列第一个进程移除
        public static Integer remove(){
            return blockedQueue.removeFirst();
        }

        //将指定的阻塞进程移除队列
        public static Integer remove(int pid){
            Iterator<Integer> iterator=blockedQueue.iterator();
            while(iterator.hasNext())
            {
                if(iterator.next()==pid)
                    iterator.remove();
            }
            return pid;
        }

        //返回阻塞队列的第一个进程pid
        public static Integer get(){
        return blockedQueue.getFirst();
    }

        //Test
        public static void print()
        {
            Iterator iterator=blockedQueue.iterator();
                while(iterator.hasNext())
                {
                    System.out.println(iterator.next());
                }
        }

    public static LinkedList<Integer> getBlockedQueue() {
        return blockedQueue;
    }
}
