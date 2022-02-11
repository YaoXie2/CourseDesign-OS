package edu.scau.process;

import java.util.Scanner;

public class TestBlockedQueue {
    public static void main(String[] args)
    {
        Scanner scanner=new Scanner(System.in);
        for(int i=0;i<5;i++)
        {
            int a=scanner.nextInt();
            BlockedQueue.add(a);

        }
        BlockedQueue.remove(5);
        BlockedQueue.print();
    }
}
