package edu.scau.disk;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    // 文件管理池,用path作为hash的key
    private static FileManager fm = new FileManager();
    public DiskManager dm;
    public Map<String, MyFile> filesPool;

    public static FileManager getInstance() {
        if (recovery()) {
            System.out.println("数据恢复成功！");
        }
        return fm;
    }

    FileManager() {
        try {
            dm = new DiskManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        filesPool = new HashMap<String, MyFile>();
        MyFile root = new MyFile(null, "/");
        root.name = "/";
        filesPool.put("/", root);

    }


    // 新建文件
    public boolean createFile(String context, String path) throws IOException {
        if (filesPool.get(path) != null) {
            System.out.println(path + " 已经存在于文件系统中");
            return false;
        }
        MyFile myFile = new MyFile(context, path);
        myFile.startDiskBlockNum = dm.createFile(myFile);    // 通过disk去判断是否能创建文件或目录
        if (myFile.startDiskBlockNum != -1) {
            int slashIndex = path.lastIndexOf('/');
            if (slashIndex == 0) {
                slashIndex += 1;
            }
            String parentPath = path.substring(0, slashIndex);
            MyFile parentMyFile = filesPool.get(parentPath);
            if (parentMyFile != null) {
                parentMyFile.children.add(myFile);
            }
            filesPool.put(myFile.path, myFile);
            System.out.println(path + " 创建成功");
            return true;
        } else {
            return false;
        }
    }

    // 删除文件
    public boolean deleteFile(String path) throws IOException {
        MyFile myFile = filesPool.get(path);
        if (path.equals("/")) {
            System.out.println("根目录无法删除");
            return false;
        }
        String fileName = path.substring(path.lastIndexOf('/') + 1);

        int slashIndex = path.lastIndexOf('/');
        if (slashIndex == 0) {
            slashIndex += 1;
        }
        String parentPath = path.substring(0, slashIndex);
        MyFile parentMyFile = filesPool.get(parentPath);

        if (parentMyFile != null) { // 删除parentFile的child下面的该文件
            parentMyFile.children.remove(parentMyFile.children.indexOf(myFile));
        }
        if (fileName.indexOf('.') == -1) { // 说明是目录
            for (int i = 0; i < myFile.children.size(); i++) {
                String childFilePath = myFile.children.get(i).path;
                filesPool.remove(childFilePath);
            }
        }
        dm.deleteFile(myFile.startDiskBlockNum);    // 删除磁盘中的文件
        filesPool.remove(path); // 将其从文件池中移除
        System.out.println(path + " 从文件系统中删除了");
        return true;
    }

    // 更改文件内容
    public boolean modifyFile(String path, String context) throws IOException {
        MyFile myFile = filesPool.get(path);
        if (myFile.type.equals("dir")) {
            System.out.println(myFile.path + " 是一个目录，无法更改其内容");
            return false;
        }
        if (dm.modifyFileInformation(myFile.startDiskBlockNum, context)) {
            myFile.content = context;
            System.out.println(myFile.path + " 内容更改成功");
            return true;
        }
        System.out.println(myFile.path + " 内容更改失败，新内容过大，请重新输入。");
        return false;
    }

    // 查看文件内容
    public MyFile checkFile(String path) throws IOException {
        System.out.println("当前查看的文件或目录是 " + path);
        MyFile myFile = filesPool.get(path);
        myFile.content = dm.readFileInformation(myFile.startDiskBlockNum);
        return myFile;
    }

    public DiskBlock checkDiskBlock(int diskNum) throws IOException {
        System.out.println("当前查看的磁盘编号是 " + diskNum);
        DiskBlock db = dm.readDiskBlockInformation(diskNum);
        return db;
    }

    // 返回用户当前点击的文件的子目录
    public ArrayList<MyFile> currentChildFiles(String path) {
        MyFile myFile = filesPool.get(path);
        if (myFile == null) {
            System.out.println(path + " 不存在于文件系统中,无法找到其子目录或文件");
            return null;
        } else if (myFile.children == null || myFile.children.size() == 0) {
            System.out.println(path + " 没有子文件或目录(null)");
            return null;
        } else {
            System.out.println(path + " 子文件或目录返回成功(list)");
            return myFile.children;
        }
    }

    public void format() throws IOException {
        filesPool = new HashMap<String, MyFile>();
        MyFile root = new MyFile(null, "/");
        filesPool.put("/", root);
        System.out.println("文件池格式化");
        dm.diskFormat();
        System.out.println("删除data目录");
        File dir = new File("data");
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
            dir.delete();
        }
    }

    public void save() {
        String basePath = "data";
        File dir = new File(basePath);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        // 保存FilePools对象
        String path = basePath + "/FilePools.json";
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(fm.filesPool);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in FilePools.json");
        } catch (IOException i) {
            i.printStackTrace();
        }

        // 磁盘信息保存
        dm.diskSave(basePath);
        System.out.println("文件系统保存成功!");
    }

    public static boolean recovery() {
        String basePath = "data";
        File dir = new File(basePath);
        if (!dir.isDirectory()) {
            System.out.println("恢复文件系统失败, 请确定data文件已存在");
            return false;
        }
        // 保存FilePools对象
        String path = basePath + "/FilePools.json";
        // 读取FilePools对象
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            fm.filesPool = (Map<String, MyFile>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
        }

        // 磁盘信息恢复
        if (!fm.dm.diskRecovery(basePath)) return false;
        System.out.println("文件系统恢复成功!");
        return true;
    }

}
