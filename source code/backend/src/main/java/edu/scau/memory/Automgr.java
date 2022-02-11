package edu.scau.memory;

public interface Automgr {
    //一个接口
    abstract Memory FirstFit1(Memory memory, int size, int id);

    abstract Memory releaseMemory1(int id);

    abstract Memory setMemory1(Memory memory, int size);

    public Memory getMemory();
}
