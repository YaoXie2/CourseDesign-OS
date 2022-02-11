package edu.scau.device;

import sun.misc.Queue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/***
 * @author *00
 * @time 21.09.25
 * 核心函数
 * run(char)用于查找是否有空闲设备，有的话直接占用并且return true
 * dectime用于对于运行队列的运行时间自减1
 */
public class DeviceManagement {
    Device[] A=new Device[2];
    Device[] B=new Device[3];
    Device[] C=new Device[3];
    private boolean result=false;//返回是否有设备可以占用的结果
    RequestOfDevice requestOfDevice;
    MyQueue<RequestOfDevice> A_waitqueue=new MyQueue<>(10);//设备A的等待队列
    MyQueue<RequestOfDevice> B_waitqueue=new MyQueue<>(10);//设备B的等待队列
    MyQueue<RequestOfDevice> C_waitqueue=new MyQueue<>(10);//设备C的等待队列

    public DeviceManagement()//初始化各个设备
    {//对三个设备类的初始化，上面只是声明为数组，指向其实为null
        A[0]=new Device('A');
        A[1]=new Device('A');
        B[0]=new Device('B');
        B[1]=new Device('B');
        B[2]=new Device('B');
        C[0]=new Device('C');
        C[1]=new Device('C');
        C[2]=new Device('C');
    }
    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
    //根据文档，A类设备2个，BC类设备各有3个
//    run函数是第一步返回是否有空闲设备
    public boolean run(RequestOfDevice requestOfDevice)//对设备设置true表示为已占用设备
    {
        char c=requestOfDevice.getDeviceRequest();
        this.requestOfDevice=requestOfDevice;
        if(c=='A'||c=='a')
        {
            for(int i=0;i<2;i++)
            {
                if(A[i].isFlag()==false)
                {
                    A[i].setFlag(true);//如果设备未被占用，直接占用
                    result=true;//有设备可以占用
                    A[i].setRequestOfDevice(requestOfDevice);
                    break;
                }
                else result=false;
            }
            if(!result)//遍历完后如果无设备可用，放进等待队列
                A_waitqueue.enqueue(requestOfDevice);
        }
        else if(c=='B'||c=='b')
        {
            for(int i=0;i<3;i++)
            {
                if(B[i].isFlag()==false)
                {
                    B[i].setFlag(true);//如果设备未被占用，直接占用
                    result=true;//有设备可以占用
                    B[i].setRequestOfDevice(requestOfDevice);
                    break;
                }
                else
                    result=false;
            }
            if(!result)//遍历完后如果没有设备可用
                B_waitqueue.enqueue(requestOfDevice);
        }
        else
        {
            for(int i=0;i<3;i++)
            {
                if(C[i].isFlag()==false)
                {
                    C[i].setFlag(true);//如果设备未被占用，直接占用
                    result=true;//有设备可以占用
                    C[i].setRequestOfDevice(requestOfDevice);
                    break;
                }
                else result=false;
            }
            if(!result)//遍历完后如果没有设备可用
                C_waitqueue.enqueue(requestOfDevice);
        }
        return result;
    }

    public List dectime()
    {
        List result=new ArrayList();
        for(int i=0;i< 2;i++)
        {
            if(A[i].getRequestOfDevice()==null)
                continue;
            requestOfDevice=A[i].getRequestOfDevice();
            requestOfDevice.setRequestTime(requestOfDevice.getRequestTime()-1);
            if(requestOfDevice.getRequestTime()==0&&A_waitqueue.size()!=0)//运行时间结束
            {
                result.add(requestOfDevice.getPID());
                requestOfDevice=A_waitqueue.dequeue();//得到下一个运行的线程
                A[i].setRequestOfDevice(requestOfDevice);
            }
            if(requestOfDevice.getRequestTime()==0&&A_waitqueue.size()==0)
            {
                result.add(requestOfDevice.getPID());
                A[i].setRequestOfDevice(null);
                A[i].setFlag(false);
            }

        }

        for(int i=0;i< 3;i++)
        {
            if(B[i].getRequestOfDevice()==null)
                continue;
            requestOfDevice=B[i].getRequestOfDevice();
            requestOfDevice.setRequestTime(requestOfDevice.getRequestTime()-1);
            if(requestOfDevice.getRequestTime()==0&&B_waitqueue.size()!=0)//运行时间结束
            {
                result.add(requestOfDevice.getPID());
                requestOfDevice=B_waitqueue.dequeue();//得到下一个运行的线程
                B[i].setRequestOfDevice(requestOfDevice);
            }
            if(requestOfDevice.getRequestTime()==0&&B_waitqueue.size()==0)
            {
                result.add(requestOfDevice.getPID());
                B[i].setRequestOfDevice(null);
                B[i].setFlag(false);
            }
        }
        for(int i=0;i< 3;i++)
        {
            if(C[i].getRequestOfDevice()==null)
                continue;
            requestOfDevice=C[i].getRequestOfDevice();
            requestOfDevice.setRequestTime(requestOfDevice.getRequestTime()-1);
            if(requestOfDevice.getRequestTime()==0&&C_waitqueue.size()!=0)//运行时间结束
            {
                result.add(requestOfDevice.getPID());
                requestOfDevice=C_waitqueue.dequeue();//得到下一个运行的线程
                C[i].setRequestOfDevice(requestOfDevice);
            }
            if(requestOfDevice.getRequestTime()==0&&C_waitqueue.size()==0)
            {
                result.add(requestOfDevice.getPID());
                C[i].setRequestOfDevice(null);
                C[i].setFlag(false);
            }
        }
        return result;
    }
    public List<RequestOfDevice> getA_waiting()
    {
        List<RequestOfDevice> aqueue = new LinkedList<>();
        for(int i=0;i<A_waitqueue.size();i++)
        {
            RequestOfDevice r=A_waitqueue.print(i);
            aqueue.add(r);
            System.out.println(" " + r.getPID());
        }
        return aqueue;
    }
    public List<RequestOfDevice> getB_waiting()
    {
        List<RequestOfDevice> bqueue=new LinkedList<>();
        for(int i=0;i<B_waitqueue.size();i++)
        {
            RequestOfDevice r=B_waitqueue.print(i);
            bqueue.add(r);
            System.out.println(" " + r.getPID());
        }
        return bqueue;
    }
    public List<RequestOfDevice> getC_waiting()
    {
        List<RequestOfDevice> cqueue=new LinkedList<>();
        for(int i=0;i<C_waitqueue.size();i++)
        {
            RequestOfDevice r=C_waitqueue.print(i);
            cqueue.add(r);
            System.out.println(" " + r.getPID());
        }
        return cqueue;
    }
    public int[] Adevice()
    {
        int[] adevice=new int[2];
        for (int i = 0; i < 2; i++)
        {
            if(!A[i].isFlag())//false表示未被占用
                adevice[i] = -1;
            else
            {
                RequestOfDevice r=A[i].getRequestOfDevice();
                adevice[i] = r.getPID();
            }
        }
        return adevice;
    }
    public int[] Bdevice()
    {
        int[] bdevice=new int[3];
        for (int i = 0; i < 3; i++)
        {
            if(!B[i].isFlag())//false表示未被占用
                bdevice[i] = -1;
            else
            {
                RequestOfDevice r=B[i].getRequestOfDevice();
                bdevice[i] = r.getPID();
            }
        }
        return bdevice;
    }
    public int[] Cdevice()
    {
        int[] cdevice=new int[3];
        for (int i = 0; i < 3; i++)
        {
            if(!C[i].isFlag())//false表示未被占用
                cdevice[i] = -1;
            else
            {
                RequestOfDevice r=C[i].getRequestOfDevice();
                cdevice[i] = r.getPID();
            }
        }
        return cdevice;
    }
}
