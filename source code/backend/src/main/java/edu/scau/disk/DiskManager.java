package edu.scau.disk;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiskManager {
    public Disk disk;
    public Map<String, Integer> blockNo;
    public Map<Integer, String> iblockNo;
    //0,1为fat表的位置

    //fat内容
    // -1表示当前磁盘块使用了没有后继
    // 0 表示当前磁盘块还未使用
    // x(正整数) 表示当前磁盘块使用了，后继为x
    public DiskManager() throws IOException //起始有根目录//占用1号磁盘块 第二块//开始必定存在一个根目录
    {
        //初始化
        disk = new Disk();
        blockNo = new HashMap<String, Integer>();
        iblockNo = new HashMap<Integer, String>();
        //0,1为fat表的位置
        blockNo.put("/", 2);//根目录
        iblockNo.put(2, "/");
    }

    //失败的条件有两个
    //文件夹：文件信息（8），空目录占用一个磁盘块（64）
    //文件:文件信息（8），文件内容占用max（1，(length + 63) / 64）
    public int createFile(MyFile myFile) throws IOException //如果返回-1表示文件创建失败
    {
        int slashIndex = myFile.path.lastIndexOf('/');
        if (slashIndex == 0) slashIndex++;
        int dirId = blockNo.get(myFile.path.substring(0, slashIndex));
        if (disk.calcFreeUnit(dirId) < 8) {
            if (disk.getFileLength(dirId) == 2) return -1;
            if (disk.getFileLength(dirId) == 1 && disk.countFreeBlock() == 0) return -1;
        }
        System.out.println(myFile.path);
        if (myFile.type.compareTo("dir") == 0) {
            if (disk.countFreeBlock() < 1) return -1;//不够空间
            //填充文件信息
            Byte[] message = new Byte[]{0, 0, 0, 0, 0, 0, 0, 0};
            int now = 0;
            for (int i = 0; i < 3; i++, now++) {
                if (i < myFile.name.length()) {
                    Byte c = Byte.parseByte("" + (myFile.name.charAt(i) + 0));
                    message[now] = c;
                } else {
                    message[now] = 0;
                }
            }
            now++;//没有拓展名
            now++;//0读写//待定
            int id = disk.assignId();
            message[now] = Byte.parseByte("" + id);//起始盘号
            //文件长度为0
            //三个字节 的文件长度待定
            //设想文件夹空间和文件空间最大128哥字节
            disk.updateFileMessage(dirId, message); //更新
            disk.setFat(id, -1);//更新fat表
            blockNo.put(myFile.path, id);
            iblockNo.put(id, myFile.path);
            return id;
        } else {
            if (myFile.content.length() > 128) return -1;//每个文件大小上限为128
            if (myFile.content.length() > disk.countFreeBlock() * 64) return -1;//剩余内存大小不足

            Byte[] message = new Byte[]{0, 0, 0, 0, 0, 0, 0, 0}; //填目录信息
            int now = 0;
            int point = myFile.name.indexOf('.');
            for (int i = 0; i < 3; i++, now++) {

                if (i < myFile.name.length() && i < point) {
                    Byte c = Byte.parseByte("" + (myFile.name.charAt(i) + 0));
                    message[now] = c;
                } else {
                    message[now] = 0;
                }
            }
            message[now++] = (byte) myFile.type.charAt(0);//没有拓展名
            now++;//0读写
            int id = disk.assignId();
            message[now] = (byte) id;//起始盘号
            disk.updateFileMessage(dirId, message); //更新目录项
            int sid = id;
            disk.updateFile(id, myFile.content);
//            System.out.println(myFile.path + " " + sid);
            blockNo.put(myFile.path, sid);
            iblockNo.put(sid, myFile.path);
            return sid;
        }
    }

    public void deleteFile(int diskStartNo) throws IOException //不可以删除根目录
    {
        String path = iblockNo.get(diskStartNo);
        String type = path.indexOf('.') == -1 ? "dir" : path.substring(path.indexOf('.') + 1);
        int slashIndex = path.lastIndexOf('/');
        if (slashIndex == 0) slashIndex++;
        String parentPath = path.substring(0, slashIndex);
        slashIndex = blockNo.get(parentPath);
        String nxpath;//下一个目录
        //recoverFreeBlockNumber
        String name = path.substring(path.lastIndexOf('/') + 1);
        int id;
        if (type.compareTo("dir") == 0) {
            id = diskStartNo;
            while (id != -1) {
                for (int i = 0; i < 64; i += 8) {
                    if (disk.diskBlocks.get(id).get(i) != (byte) 0) {
                        nxpath = path + "/";
                        for (int j = 0; j < 3; j++) {
                            if (disk.diskBlocks.get(id).get(i + j) != (byte) 0) {
                                nxpath += (char) disk.diskBlocks.get(id).get(i + j);
                            }
                        }
                        if (disk.diskBlocks.get(id).get(i + 3) == (byte) 't') nxpath += ".txt";
                        if (disk.diskBlocks.get(id).get(i + 3) == (byte) 'e') nxpath += ".exe";
//                        System.?ut.println(nxpath);
                        deleteFile(blockNo.get(nxpath));
                    }
                }
                int tmp = disk.getFat(id);
//                int tmp = disk.diskBlocks.get(id / 64).unit[id % 64];
                disk.setFat(id, 0);
//                disk.diskBlocks.get(id / 64).clr(id % 64);
                disk.recoverFreeBlockNumber(id);
                id = tmp;
            }
            blockNo.remove(path);
            iblockNo.remove(diskStartNo);
            disk.deleteFilefold(slashIndex, name);
        } else //删除文件
        {
            id = diskStartNo;
            while (id != -1) {
                disk.load();
                for (int i = 0; i < 64; i++) {
                    if (disk.diskBlocks.get(id).get(i) != (byte) 0) {
                        disk.diskBlocks.get(id).clr(i);
                    }
                }
                disk.store();
                int tmp = disk.getFat(id);
//                int tmp = disk.diskBlocks.get(id / 64).unit[id % 64];
                System.out.println("删除" + " " + id);
                disk.setFat(id, 0);
//                disk.diskBlocks.get(id / 64).clr(id % 64);
                disk.recoverFreeBlockNumber(id);
                id = tmp;
            }
            //在hash中删除
            blockNo.remove(path);
            iblockNo.remove(diskStartNo);
            disk.deleteFile(slashIndex, name);
        }
        disk.recoverFreeBlock();
    }

    public DiskBlock readDiskBlockInformation(int diskNumber) throws IOException //读取磁盘信息
    {
        if (diskNumber == -1) return null;
        if (diskNumber <= 1) {
            return disk.diskBlocks.get(diskNumber);
        }
        disk.load();
        String path = iblockNo.get(diskNumber);
        if (path == null) return disk.diskBlocks.get(diskNumber);
        System.out.println(diskNumber + path);
        String type = path.indexOf('.') == -1 ? "dir" : path.substring(path.indexOf('.') + 1);
        DiskBlock db = disk.diskBlocks.get(diskNumber);
        if (type.compareTo("dir") == 0) {
            if (diskNumber != 2) path += "/";
            for (int i = 0; i < 64; i += 8) {
                if (db.unit[i] != (byte) 0) {
                    String name = path;
                    for (int j = 0; j < 3; j++) {
                        if (db.unit[i + j] != 0) name += (char) db.unit[i + j];
                    }
                    if (db.unit[i + 3] == (byte) 'e') name += ".exe";
                    if (db.unit[i + 3] == (byte) 't') name += ".txt";
                    System.out.println(name);
                    System.out.println(name + " " + blockNo.get(name));
                    int tmp = blockNo.get(name);
                    while (tmp != -1) {
                        db.unit[i + 7] += 64 - disk.diskBlocks.get(tmp).getRestUnit();
                        tmp = disk.diskBlocks.get(tmp / 64).unit[tmp % 64];
                    }
                }
            }
        }
        return db;
    }

    public String readFileInformation(int diskStartNo) throws IOException //读取文件信息
    {
        System.out.println(diskStartNo);
        return disk.readFile(diskStartNo);
    }

    public boolean modifyFileInformation(int diskStartNo, String newcontext) throws IOException//修改磁盘中文件信息
    {
        if (newcontext.length() > 128) return false;//每个文件大小上限为128
        disk.load();
        if (Math.min(disk.getFileLength(diskStartNo) + disk.countFreeBlock(), 2) * 64 >= newcontext.length()) {
            disk.clearFile(diskStartNo);
            for (int idx = 0; idx < newcontext.length(); ) {
                for (int j = 0; j < 64 && idx < newcontext.length(); j++, idx++) {
                    disk.diskBlocks.get(diskStartNo).use(j, (int) newcontext.charAt(idx));
                }
                if (idx >= newcontext.length()) break;
//                int tmp = disk.getFat(diskStartNo);
                int tmp = disk.diskBlocks.get(diskStartNo / 64).unit[diskStartNo % 64];
                if (tmp == -1) {
                    tmp = disk.assignId();
//                    disk.setFat(diskStartNo,tmp);
                    disk.diskBlocks.get(diskStartNo / 64).clr(diskStartNo % 64);
                    disk.diskBlocks.get(diskStartNo / 64).use(diskStartNo % 64, tmp);
                    disk.diskBlocks.get(tmp / 64).use(tmp % 64, -1);
                }
                diskStartNo = tmp;
            }
        } else return false;
        disk.store();
        disk.recoverFreeBlock();

        //
//        if(disk.countFreeBlock(diskStartNo) )
        return true;
    }

    public void diskFormat() throws IOException {
        disk.format();
        blockNo = new HashMap<String, Integer>();
        iblockNo = new HashMap<Integer, String>();
        blockNo.put("/", 2);//根目录
        iblockNo.put(2, "/");
    }

    public boolean diskRecovery(String basePath) {
        List<Integer> restUnit;
        File dir = new File(basePath);
        if (!dir.isDirectory()) {
            System.out.println("恢复文件系统失败, 请确定data文件已存在");
            return false;
        }
        // 保存FilePools对象
        String path1 = basePath + "/BlockNo.json";
        String path2 = basePath + "/IBlockNo.json";
        String path3 = basePath + "/RestUnit.json";
        // 读取FilePools对象
        try {
            FileInputStream fileIn1 = new FileInputStream(path1);
            ObjectInputStream in1 = new ObjectInputStream(fileIn1);
            blockNo = (Map<String, Integer>) in1.readObject();
            in1.close();
            fileIn1.close();


            FileInputStream fileIn2 = new FileInputStream(path2);
            ObjectInputStream in2 = new ObjectInputStream(fileIn2);
            iblockNo = (Map<Integer, String>) in2.readObject();
            in2.close();
            fileIn2.close();

            FileInputStream fileIn3 = new FileInputStream(path3);
            ObjectInputStream in3 = new ObjectInputStream(fileIn3);
            restUnit = (List<Integer>) in3.readObject();
            in3.close();
            fileIn3.close();
            for (int i = 0; i < 128; i++) {
                disk.diskBlocks.get(i).setRestUnit(restUnit.get(i));
            }
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
        }
        if (!disk.blockRecovery(basePath)) return false;
        System.out.println("磁盘信息恢复成功!");
        return true;
    }

    public void diskSave(String basePath) {
        List<Integer> restUnit = new ArrayList<Integer>();
        for (int i = 0; i < 128; i++) {
            restUnit.add((disk.diskBlocks.get(i).getRestUnit()));
        }
        File dir = new File(basePath);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        // 保存FilePools对象
        String path1 = basePath + "/BlockNo.json";
        String path2 = basePath + "/IBlockNo.json";
        String path3 = basePath + "/RestUnit.json";
        try {
            FileOutputStream fileOut1 = new FileOutputStream(path1);
            ObjectOutputStream out1 = new ObjectOutputStream(fileOut1);
            out1.writeObject(blockNo);
            out1.close();
            fileOut1.close();

            FileOutputStream fileOut2 = new FileOutputStream(path2);
            ObjectOutputStream out2 = new ObjectOutputStream(fileOut2);
            out2.writeObject(iblockNo);
            out2.close();
            fileOut2.close();

            FileOutputStream fileOut3 = new FileOutputStream(path3);
            ObjectOutputStream out3 = new ObjectOutputStream(fileOut3);
            out3.writeObject(restUnit);
            out3.close();
            fileOut2.close();

            System.out.println("Serialized data is saved in BlockNo.json");
            System.out.println("Serialized data is saved in IBlockNo.json");
            System.out.println("Serialized data is saved in RestUnit.json");
        } catch (IOException i) {
            i.printStackTrace();
        }
        disk.blockSave(basePath);
        System.out.println("磁盘信息保存成功!");
    }
}