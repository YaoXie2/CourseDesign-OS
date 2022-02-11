package edu.scau.device;

/***
 * @author *00
 * @time 21.09.24
 * 10.09修改
 * 根据传过来的路径(文件)读取内容，返回队列
 */

import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/***
 * 1.查找是否有空设备，使用函数run(char c):boolean
 * 2.时间片自减函数
 * 3.队列自动补缺，修改所需时间片
 * 4.
 */
public class Test {
    public static void main(String[] args)
    {
            Scanner scanner=new Scanner(System.in);
            DeviceManagement deviceManagement=new DeviceManagement();

            char c=scanner.next().charAt(0);
            int time=scanner.nextInt();
            int PID=scanner.nextInt();
            while(true)
            {
                RequestOfDevice requestOfDevice=new RequestOfDevice(PID,c,time);
                deviceManagement.run(requestOfDevice);
                List pid=deviceManagement.dectime();
//            deviceManagement.introduction();
              /*  int[] a = deviceManagement.Adevice();
                System.out.print("设备a的状态为：");

                System.out.println("\n设备a的等待队列为：");
                deviceManagement.getA_waiting();*/
                System.out.println("已经运行完的pod有：");
                Iterator it=pid.iterator();
                while(it.hasNext())
                    System.out.println(it.next());
                System.out.println("请继续输入");
                c=scanner.next().charAt(0);
                time=scanner.nextInt();
                PID=scanner.nextInt();
            }
    }

}
