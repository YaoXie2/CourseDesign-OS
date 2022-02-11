package edu.scau.memory;

import java.util.LinkedList;

public class Memory {
    private int size ; //内存大小
    private int lastFind; //上次寻址结束的下标
    private LinkedList<Pcb> pcbs; //记录内存中进程控制块的双向链表
    private LinkedList<Hole> holes;//记录内存分区的双向链表
    private static final int MIN_SIZE = 0;//最小剩余分区大小
    private boolean flag=true;//标志申请内存成功与否

    public Memory(){
        holes = new LinkedList<Hole>();//内存分区的双向链表
        pcbs = new LinkedList<Pcb>();//
    }
//    public Memory(int size){
//        size = size ;
////        holes = new LinkedList<Hole>();//内存分区的双向链表
////        pcbs = new LinkedList<Pcb>();//
//        holes.add(new Hole(0,size,true));
//     }
   public Memory setMemory(Memory memory,int size){
        memory.size = size;
        memory.holes.add(new Hole(0,size,true));
        return memory;
   }

    public Memory FirstFit(Memory memory,int size,int id){
        int sum = 0;
        for(int i = 0;i<memory.getHoles().size();i++){
            //循环查找内存分配链
            sum++;
            memory.setLastFind(i);//为循环首次适应算法设置最后寻址下标
            Hole hole = memory.getHoles().get(i);
            if(hole.getSize()>=size&&hole.isFree()){
                System.out.println("查找"+sum+"次");
                return memory.getMemory(size,i,hole,id);
            }

        }
        System.out.println("out of memory!");
        flag=false;
        return  memory;
    }





    public Memory getMemory(int size,int location,Hole hole,int id){
        //size为申请大小，location为分配区的下标，hole为location对应的分区,id为进程的id
        //申请内存，更改内存分配链
        if(hole.getSize()-size>=MIN_SIZE){
            //若分配后当前分区剩余大小大于最小分区大小，则把当前分区分为两块
            Hole newHole = new Hole(hole.getHead()+size,hole.getSize()-size);
            holes.add(location+1,newHole);
            hole.setSize(size);
        }
        //pcbs.add();  添加一个就绪状态的进程 ,一个下标，一个元素
        pcbs.add(new Pcb(id,1,hole));
        System.out.println("pcbs_add" + id);
        //Pcb添加一个进程控制块
        hole.setFree(false);
        System.out.println("成功分配大小为" + size + "的内存");
                return this;
    }

    public Memory releaseMemory(int id){
        System.out.println("删除的id是:" + id);
        //记录此id对应的进程（忽略进程id与分区id相同，但进程不同的情况）
        Pcb pcb = null;
        //创建一个空的进程控制块
        boolean flag = false ;
        //抛出id,首先去到Pcbs里面进行检索，检索成功则去内存分配表里检索，对应的内存分配块，需要用id进行检索
        System.out.println("pcbs:");
        for (int i=0; i<pcbs.size(); i++) {
            System.out.print(pcbs.get(i).getId() + ' ');
        }
        System.out.println();
        for(int i= 0;i<pcbs.size();i++){
            if(id == pcbs.get(i).getId())//双向链表封装好的检索，getId()自己写的函数
            {
                pcb =pcbs.get(i);
                flag = true ;
                break ;
            }
        }

        if (flag == false){
            System.out.println("无此分区"+id);
            return this;
        }

        if(pcb!=null){
            //检索出对应的内存分区块
            //pcb已经确定了
            //对于pcb和内存分区块的匹配
            //size，head
            for(int i = 0;i<holes.size();i++){
                Hole hole = holes.get(i);
                if((pcb.getHole().getSize()==hole.getSize())&&(pcb.getHole().getHead()==hole.getHead())){
                    //确定找到了
                    id = i;
                    //成功获取到是几块
                    break;
                }
            }
        }
        Hole hole = holes.get(id);
        System.out.println("Memory.isFree:"+hole.isFree());
        //hole已经被确定为要更改的内存分区块
        if(hole.isFree())
        {
            System.out.println("此分区空闲,无需释放：\t" + id);

            return  this;
            //说明有可能接受到不存在于内存中的进程?
            // return this;
        }

        //用id释放pcb
        for (int i = 0; i < pcbs.size(); i++) {
            Pcb pcb2 = pcbs.get(i);
            if((pcb2.getHole().getSize()==hole.getSize())&&(pcb2.getHole().getHead()==hole.getHead())){
                pcbs.remove(i);
                break;
            }
        }

        //如果回收分区不是首分区，且前一个分区空闲
        if(id>0&&holes.get(id-1).isFree()){
            Hole lastHole = holes.get(id-1);
            lastHole.setSize(lastHole.getSize()+hole.getSize());
            holes.remove(id);
            id--;
        }
        //如果回收分区是尾部分区，且后一个分区空闲
        hole=holes.get(id);
        if(id<holes.size()-1&&holes.get(id+1).isFree()){
               Hole nextHole = holes.get(id+1);
               hole.setSize(nextHole.getSize()+hole.getSize());
               hole.setFree(true);
               holes.remove(nextHole);
        }
        holes.get(id).setFree(true);
        System.out.println("内存回收成功！");
        return this;

    }


//    public void showPcbs(Memory memory){
//        System.out.println("---------------------");
//        System.out.println("进程编号\t进程状态\t进程起始地址\t进程大小\t");
//        System.out.println("---------------------");
//        if(memory.getPcbs().size()>0) {
//            for (int i = 0; i < memory.getPcbs().size(); i++) {
//                Pcb pcb = memory.getPcbs().get(i);
//                System.out.println(pcb.getId() + "\t" + pcb.getState() + "\t" + pcb.getHole().getHead() + "\t" + pcb.getHole().getSize());
//            }
//        }
//        else
//        {
//            System.out.println("\t\t\t暂无进程");
//        }
//        System.out.println("-----------------");
//    }


    public int getLastFind() {
        return lastFind;
    }

    public void setLastFind(int lastFind) {
        this.lastFind = lastFind;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LinkedList<Hole> getHoles() {
        return holes;
    }

    public LinkedList<Pcb> getPcbs() {
        return pcbs;
    }

    public void setHoles(LinkedList<Hole> holes) {
        this.holes = holes;
    }

    public static int getMinSize() {
        return MIN_SIZE;
    }


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
