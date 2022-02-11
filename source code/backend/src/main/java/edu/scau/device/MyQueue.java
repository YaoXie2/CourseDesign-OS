package edu.scau.device;

/***
 * @author *00
 * @time 21.10.11
 * @Attention 之前使用普通泛型队列，只能储存10个结构体(也就是一个流程下来一个设备只能处理10个)
 * 所以改成了循环队列泛型，大小可以自己在test新建的时候固定大小
 * @param <E>
 */

public class MyQueue<E>{
    private int head = 0;
    private int tail = 0;
    private E[] data = null;
    public MyQueue(int Capacity) {

        data = (E[]) new Object[Capacity+1];     //注意这要new一个比Capacity大1的数组，刚开始new了一个Capacity的函数，下面有个错误一直没找出来，
        //new一个Capacity大小的数组也行，但是下面判断条件一定要改，
    }

    public int getCapacity() {
        return data.length-1;
    }
    public void enqueue(E e) {                          //进队列

        if ((tail+1) == head) {                         //此处判断数组是否满了，满了以后reSize一个2*data.length+1的数组
            System.out.println("队列已满！将reSize");
            reSize(data.length*2);

        }
        data[tail] = e;
        tail++;
        tail = tail % data.length;


    }
    private void reSize(int newSize) {
        E[] newData  = (E[]) new Object[newSize+1];
        int j = 0;
        for (int i = head; i != tail; ) {
            newData[j] = data[i];
            j++;
            i++;
            i = i%data.length;
        }
        tail = j+1;
        head = 0;
        data = newData;

    }

    public E dequeue() {             //出队列

        E s = data[head] ;
        head++;
        head = head %data .length;
        return s;

    }

    public E print(int index) {


        return data[head+index];
    }


    public int size() {

        if (tail< head) {
            return tail+data.length - head ;
        }else {
            return tail - head;
        }


    }

    public boolean isEmpty() {

        return head == tail;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = head; i != tail; ) {
            stringBuffer.append(data[i]+",");

            i++;
            i = i%data.length;

        }
        stringBuffer.append("tail");

        return stringBuffer.toString();

    }
}

