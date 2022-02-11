package edu.scau.disk;

public class DiskBlock { //单个磁盘块
    private int restUnit = 64;
    public static final int blocksize = 64;
    public byte[] unit;
    public DiskBlock()
    {
        unit = new byte[64];
        for(int i = 0;i < blocksize;i ++) this.unit[i] = (byte) 0;
        restUnit = 64;
    }
    public void use(int id,int k)
    {
       this.unit[id] = (byte)k;
        this.restUnit --;
    }
    public void clr(int id)
    {
        this.unit[id] = (byte)0;
//        System.out.println(id + " " +restUnit);
        this.restUnit ++;
    }
    public byte get(int id)
    {
        return this.unit[id];
    }
    public int getRestUnit()
    {
        return restUnit;
    }
    public void setRestUnit(int value)
    {
        this.restUnit = value;
    }
}
