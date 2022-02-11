package edu.scau.disk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Map;

public class Disk { //整个磁盘
    static public String filename = "disk.dat";
    List<DiskBlock> diskBlocks;
    int maxDiskSize = 8192;
    static final int blockNumber = 128;
    List<Integer> freeBlockNumber;//目前可使用的磁盘块编号
    public Disk() throws IOException {
        freeBlockNumber = new ArrayList<Integer>();
        for(int i = 127;i >= 3;i --) freeBlockNumber.add(i);//0,1存储fat表,2根目录
        diskBlocks = new ArrayList<DiskBlock>();
        File readFrom = new File(filename);
//        if(readFrom.isFile() && readFrom.exists())
//        {
//            boolean ok = readFrom.delete();
//            System.out.print("exists" + ok);
//        }
        readFrom.createNewFile();
        for(int i = 0;i < 128;i ++) diskBlocks.add(new DiskBlock());
//        for(int j = 0;j < 64;j ++)
//        {
//            System.out.println(diskBlocks.get(0).unit[j]);
//        }
        setFat(0,-1);
        setFat(1,-1);
        setFat(2,-1);
//        for(int j = 0;j < 64;j ++)
//        {
//            System.out.println(diskBlocks.get(0).unit[j]);
//        }
        if(readFrom.isFile() && readFrom.canWrite() && readFrom.canRead())
        {
            FileOutputStream rafFile = new FileOutputStream(readFrom);
            for(int i = 0;i < diskBlocks.size();i ++)
            {
                rafFile.write(diskBlocks.get(i).unit,0,64);
            }
            rafFile.close();
        }
    }
    public void format() throws IOException {
        File readFrom = new File(filename);
        System.out.println("磁盘初始化");

        if(readFrom.isFile() && readFrom.exists())
        {
            boolean ok = readFrom.delete();
            System.out.print("exists" + ok);
        }
        readFrom.createNewFile();
        diskBlocks = new ArrayList<DiskBlock>();
        for(int i = 0;i < 128;i ++) diskBlocks.add(new DiskBlock());
        setFat(0,-1);
        setFat(1,-1);
        setFat(2,-1);
        if(readFrom.isFile() && readFrom.canWrite() && readFrom.canRead())
        {
            FileOutputStream rafFile = new FileOutputStream(readFrom);
            for (DiskBlock diskBlock : diskBlocks) {
                rafFile.write(diskBlock.unit, 0, 64);
            }
            rafFile.close();
        }
    }
    public void setFat(int no,int value) throws IOException {
        this.load();
        int x = no/64,y = no % 64;
//        System.out.println("--"+this.diskBlocks.get(0).getRestUnit());
        if(value != 0) this.diskBlocks.get(x).use(y,value);
        else
        {
//            System.out.println(no + " " +y + " " + value);
            this.diskBlocks.get(x).clr(y);
        }
//        System.out.println("--"+this.diskBlocks.get(0).getRestUnit());
        this.store();
    }
    public Byte getFat(int no) throws IOException {
        this.load();
        return this.diskBlocks.get(no/64).get(no % 64);
    }
    public int countFreeBlock()
    {
        return freeBlockNumber.size();
    }
    public int assignId()
    {
        int id = freeBlockNumber.get(freeBlockNumber.size() - 1);
        freeBlockNumber.remove(freeBlockNumber.size() - 1);
        return id;
    }
    public int calcFreeUnit(int id) throws IOException //获取目录项的空闲空间
    {
        this.load();
        int sum = 0;
        while(id != -1)
        {
            sum += diskBlocks.get(id).getRestUnit();
            id = getFat(id);
        }
        return sum;
    }
    public void updateFile(int id,String context) throws IOException {
        this.load();
        for(int idx = 0;idx < context.length();)
        {
            for(int j = 0;j < 64 && idx < context.length();j ++,idx ++)
            {
                diskBlocks.get(id).use(j,(int)context.charAt(idx));
            }
            if(idx >= context.length()) break;
            int tmp = assignId();
//            setFat(id,tmp);
            diskBlocks.get(id / 64).clr(id);
            diskBlocks.get(id / 64).use(id,tmp);
            id = tmp;
        }
//        setFat(id,-1);//更新fat表
        diskBlocks.get(id / 64).use(id,-1);
        this.store();
    }
    public void updateFileMessage(int id,Byte[] b) throws IOException {
        this.load();
        int sid = id;
        int now = 1;
        while(id != -1)
        {
            for(int i = 0;i < 64;i += 8)
            {
                if( diskBlocks.get(id).get(i) == (byte)0)
                {
                    for(int j = 0;j < 8;j ++)
                    {
                        diskBlocks.get(id).use(i + j,b[j]);
                    }
                    this.store();
                    return;
                }
            }
            int t = getFat(id);
            if(t == -1 && now == 1)
            {
                t = assignId();
                setFat(id,0);
                setFat(id,t);
                setFat(t,-1);
            }
            now ++;
            id = getFat(id);
        }
        this.store();
    }
    public void recoverFreeBlockNumber(int id)
    {
        freeBlockNumber.add(id);
    }
    public String readFile(int id) throws IOException {
        this.load();
        String context = new String("");
        while(id != -1)
        {
            for(int i = 0;i < 64;i ++)
            {
                if(diskBlocks.get(id).get(i) != (byte)0)
                {
                    context += (char)diskBlocks.get(id).get(i);
                }
//                System.out.println(diskBlocks.get(id).get(i));
            }
//            id = getFat(id);
//            System.out.println(id + " " + context);
            id = diskBlocks.get(id /64).unit[id %64];
        }
        this.store();
        return context;
    }
    public int getFileLength(int id) throws IOException {
        this.load();
        int res = 0;
        while(id != -1)
        {
            res ++;
            id = getFat(id);
        }
        this.store();
        return res;
    }
    public void clearFile(int id) throws IOException {
        this.load();
        while(id != -1)
        {
            for(int i = 0;i < 64;i ++)
            {
                if(diskBlocks.get(id).get(i) != (byte)0)
                {
                    diskBlocks.get(id).clr(i);
                }
            }
//            id = getFat(id);
            id = diskBlocks.get(id / 64).unit[id %64];
        }
        this.store();
    }
    public void deleteFilefold(int slashIndex,String name) throws IOException {
        this.load();
        Byte[] fileName = new Byte[3];
        for(int i = 0;i < 3;i ++)
        {
            if(i < name.length()) fileName[i] = (byte)name.charAt(i);
            else fileName[i] = (byte) 0;
        }
        int id = slashIndex;
        while(id != -1) // 还要在父目录删除
        {
//            for(int i = 0;i < 3;i ++) System.out.print(name + " " +fileName[i]);
//            System.out.println("--"+path + " " + slashIndex);
            for(int i = 0;i < 64;i += 8)
            {
                //是文件夹文件名相同
                if(diskBlocks.get(id).get(i) != (byte)0 && diskBlocks.get(id).get(i + 3) == (byte)0)
                {
                    boolean ok = true;//匹配
                    for(int j = 0;j < 3;j ++)
                    {
                        if(diskBlocks.get(id).get(i + j) != fileName[j]) ok = false;
                    }
                    if(ok)
                    {
                        for(int j = 0;j < 8;j ++)
                        {
                            diskBlocks.get(id).clr(i + j);
                        }
                        this.store();
                        return;
                    }
                }
            }
//            int tmp = getFat(id);
            int tmp = diskBlocks.get(id / 64).unit[id % 64];
            id = tmp;
        }
        this.store();
    }
    public void deleteFile(int slashIndex,String name) throws IOException {
        this.load();
        Byte[] fileName = new Byte[3];
        int point = name.indexOf('.');
        for(int i = 0;i < 3;i ++)
        {
            if(i < name.length() && i < point) fileName[i] = (byte)name.charAt(i);
            else fileName[i] = (byte) 0;
        }
        int id = slashIndex;
        while(id != -1) // 还要在父目录删除
        {
            for(int i = 0;i < 64;i += 8)
            {
                //是文件,文件名后缀相同
                if(diskBlocks.get(id).get(i) != (byte)0 && diskBlocks.get(id).get(i + 3) == (byte)name.charAt(point + 1))
                {
                    boolean ok = true;//匹配
                    for(int j = 0;j < 3;j ++)
                    {
                        if(diskBlocks.get(id).get(i + j) != fileName[j]) ok = false;
                    }
                    if(ok)
                    {
//                            System.out.println(ok);
                        for(int j = 0;j < 8;j ++)
                        {
//                                System.out.println(i + j);
                            diskBlocks.get(id).clr(i + j);
                        }
                        this.store();
                        return;
                    }
                }
            }
//            int tmp = getFat(id);
            int tmp = diskBlocks.get(id / 64).unit[id % 64];
            id = tmp;
        }
        this.store();
    }
    public void load() throws IOException {
        File readFrom = new File(filename);
//        System.out.print(readFrom.getAbsolutePath());
        if(readFrom.isFile() && readFrom.canWrite() && readFrom.canRead())
        {
            FileInputStream rafFile = new FileInputStream(readFrom);
            for(int i = 0;i < diskBlocks.size();i ++)
            {
                rafFile.read(diskBlocks.get(i).unit,0,64);
            }
            rafFile.close();
        }
    }
    public void store() throws IOException {
        File readFrom = new File(filename);
        if(readFrom.isFile() && readFrom.canWrite() && readFrom.canRead())
        {
            FileOutputStream rafFile = new FileOutputStream(readFrom);
            for(int i = 0;i < diskBlocks.size();i ++)
            {
                rafFile.write(diskBlocks.get(i).unit,0,64);
            }
            rafFile.close();
        }
    }
    public void recoverFreeBlock() throws IOException {
        this.load();
        boolean[] vis = new boolean[128];
        for(int i = 0;i < 128;i ++) vis[i] = false;
        for(int i = 0;i < 128;i ++)
        {
            if(vis[i]) continue;
            vis[i] = true;
            if(diskBlocks.get((i / 64)).unit[(i % 64)] != -1 && diskBlocks.get((i / 64)).unit[(i % 64)] != 0)
            {
                int id = diskBlocks.get((i / 64)).unit[(i % 64)];
                if(diskBlocks.get(id).getRestUnit() == 64)
                {
                    diskBlocks.get(id / 64).clr(id % 64);
                    diskBlocks.get(i / 64).clr(i % 64);
                    diskBlocks.get(i / 64).use(i % 64,-1);
                    recoverFreeBlockNumber(id);
                }
            }
        }
        this.store();
    }
    public void blockSave(String basePath)
    {
        File dir = new File(basePath);
        if(!dir.isDirectory()){
            dir.mkdir();
        }
        // 保存FilePools对象
        String path = basePath + "/BlocksId.json";
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(freeBlockNumber);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in BlocksId.json");
        }catch(IOException i)
        {
            i.printStackTrace();
        }

        // 磁盘信息保存
        System.out.println("磁盘块编号保存成功!");
    }
    public boolean blockRecovery(String basePath)
    {
        File dir = new File(basePath);
        if(!dir.isDirectory()){
            System.out.println("恢复文件系统失败, 请确定data文件夹已存在");
            return false;
        }
        // 保存FilePools对象
        String path = basePath + "/BlocksId.json";
        // 读取FilePools对象
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            freeBlockNumber = (List<Integer>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
        }

        System.out.println("磁盘块编号恢复成功!");
        return true;
    }
}
