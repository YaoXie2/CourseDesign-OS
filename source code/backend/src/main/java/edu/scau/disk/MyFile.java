package edu.scau.disk;

import java.io.Serializable;
import java.util.ArrayList;

public class MyFile implements Serializable {
    // 文件名（包含拓展名的）
    public String name;
    
    // 文件内容
    public String content;
    
    // 文件路径
    public String path;
    
    // 文件拓展名字（exe/txt/dir）
    public String type;

    // 其实盘块号
    public int startDiskBlockNum;

    // 子目录或文件
    public ArrayList<MyFile> children = new ArrayList<MyFile>();

    public MyFile(String context, String path) {
        this.name = path.substring(path.lastIndexOf('/')+1);
        this.type = name.indexOf('.')==-1 ? "dir":name.substring(name.indexOf('.')+1);
        this.content = context; // 如果是目录的话，这个属性是null
        this.path = path;
        if(!this.type.equals("dir")){
            this.children = null;
        }
        this.startDiskBlockNum = -1;
    }
}
