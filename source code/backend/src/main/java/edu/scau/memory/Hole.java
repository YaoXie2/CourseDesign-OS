package edu.scau.memory;

public class Hole {
    //空闲分区 "块“”链“
    private int head; //在内存中的起始地址
    private int size; //长度
    private boolean isFree; //该分区块是否空闲

    public Hole(int head,int size){
           this.head = head ;
           this.size = size ;
           this.isFree = true ;
       }
    //新分配一个内存空间，修改空闲分区链
    public Hole(int head,int size,boolean isFree){
        this.head = head ;
        this.size = size ;
        this.isFree = isFree ;
       }
    //

//    @Override
//    public String toString(){
//        //?
//
//    }
    public int getHead(){
        return head;
    }

    public int getSize(){
        return size ;
    }

    public void setHead(int head) {
        this.head = head;
    }
    public void setSize(int size){
        this.size = size ;
    }

    public void setFree(boolean free) {
        this.isFree = free;
    }
    public boolean isFree(){return isFree;}
}
