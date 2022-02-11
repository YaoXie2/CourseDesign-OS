package edu.scau.memory;

public class Memorymgr implements Automgr {
    //实现接口的类

    private static Memory memory = new Memory();

    public Memory setMemory1(Memory memory, int size) {
        return memory.setMemory(memory, size);
    }

    public Memory FirstFit1(Memory memory, int size, int id) {
        return memory.FirstFit(memory, size, id);
    }

    public Memory releaseMemory1(int id) {
        return memory.releaseMemory(id);
    }
    public Memory getMemory(){
        return memory;
    }

}
