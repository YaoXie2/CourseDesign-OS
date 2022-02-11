package edu.scau.process;

public class PCB {
    private int pid;        //进程唯一标识符PID
    private int processState;//0是新建态，1是就绪态，2是运行态，3是阻塞态,-1是终止态
    private int ax;           //当前进程的上下文：寄存器ax的值
    private int pc;           //当前进程的上下文：数据寄存器
    private String ir;          //上下文：指令寄存器
    private int life;           //剩余轮转时间
    private String instructions[];  //以字符串的形式存放指令

    //对新分配的pcb的初始化，将其加入就绪队列
    public void initialize(String[] instructions){
        this.instructions=instructions;
        processState=1;
        ax=0;
        pc=0;
        ir=instructions[0];
        life=0;
        ReadyQueue.add(pid);
    }

    public int getPid()
    {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getProcessState() {
        return processState;
    }

    public void setProcessState(int processState) {
        this.processState = processState;
    }

    public int getAx() {
        return ax;
    }

    public void setAx(int ax) {
        this.ax = ax;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public String getIr() {
        return ir;
    }

    public void setIr(String ir) {
        this.ir = ir;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public String[] getInstructions() {
        return instructions;
    }

    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }
}
