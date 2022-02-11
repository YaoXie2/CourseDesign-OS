package edu.scau.memory;

public class  MemoryManagement {
   //工厂类
    private static MemoryManagement instance;
    private Automgr mgr;
    public static MemoryManagement getInstance()
    {
        if(instance==null)
            instance=new MemoryManagement();
        return instance;
    }
    private MemoryManagement()
    {
        this.mgr=new Memorymgr();
    }
    public Automgr getMgr() {
        return mgr;
    }

}

