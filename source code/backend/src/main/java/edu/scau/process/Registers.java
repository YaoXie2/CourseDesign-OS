package edu.scau.process;

/**
 * 寄存器组，PC,IR,AX
 * AX存放的是当前x的值
 */

public class Registers {
    private static int pc;  //程序计数器
    private static String ir;//指令寄存器
    private static int ax;   //数据寄存器

    public static int getPc() {
        return pc;
    }

    public static void setPc(int pc) {
        Registers.pc = pc;
    }

    public static String getIr() {
        return ir;
    }

    public static void setIr(String ir) {
        Registers.ir = ir;
    }

    public static int getAx() {
        return ax;
    }

    public static void setAx(int ax) {
        Registers.ax = ax;
    }

    public static void show(){
        System.out.println("pc:"+pc);
        System.out.println("ir:"+ir);
        System.out.println("ax:"+ax);
    }
}
