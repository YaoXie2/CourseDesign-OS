package edu.scau.memory;

public class Pcb { //进程控制块
    private int id; //进程id
    private Hole hole; // 进程分配的内存起始，进程分配的内存长度
    private int state; //进程的状态

    public Pcb(int id,int state,Hole hole) {
        //构造进程控制块,
        // 先分配了内存然后，然后创建进程控制块
        this.id = id;
        this.hole = hole;
        this.state = state; //进程最初的状态应该是怎样？
    }
    public int getId(){
        return id;
    }
    public int getState(){
        return state ;
    }
    public Hole getHole(){
        return hole;
    }
    //public void setId{} 不能改变id
    public void setState(int state){
       this.state = state ;
    }



}
