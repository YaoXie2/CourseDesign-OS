package edu.scau.main;

import io.javalin.http.Context;

import java.io.IOException;
import java.util.Arrays;

import static edu.scau.main.Manager.*;

//fetch(url, {
//        body: formData, // must match 'Content-Type' header
//        mode: "cors",
//        method: 'POST' // *GET, POST, PUT, DELETE, etc.
//        })
//        .then(response => console.log(response))

/**
 * 处理文件与前端数据的交互
 */
public class FileHandler {
    // 消息类，用于返回错误信息
    private static class Msg {
        public String error;
    }
    private static Msg msg = new Msg();

    // fat表项
    private static class FatItem {
        public int num;
        public boolean used;
        public int next;
        public FatItem(int num) {this.num = num; this.used = false; next =0;}
    }
    private static FatItem[] fat = new FatItem[128];
    static {
        for (int i=0; i<128; i++)
            fat[i] = new FatItem(i);
    }


    public static void getFileItems(Context ctx) {
        try {
            System.out.println(fm.checkFile("/").children);
            ctx.json(fm.checkFile("/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件
     * path 文件路径，如（/d1/a.txt）
     * content 文件内容
     */
    public static void createFile(Context ctx) {
        try {
            if (fm.createFile(ctx.formParam("content"), ctx.formParam("path"))) {
                ctx.json(fm.checkFile("/"));
            } else {
                msg.error = "存在重名文件\n或创建的文件个数超过了该目录的容量（16个）\n或创建的文件内容过长";
                System.out.println(msg.error);
                ctx.json(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建目录
     * path 目录路径
     */
    public static void createDir(Context ctx) {
        System.out.println();
        System.out.println(ctx.formParam("path"));
        try {
            if (fm.createFile(null, ctx.formParam("path"))) {
                ctx.json(fm.checkFile("/"));
            } else {
                msg.error = "创建的文件个数超过了该目录的容量（16个）或创建的文件内容过长";
                ctx.json(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件或目录
     * path 删除目标的路径
     */
    public static void delete(Context ctx) {
        String path = ctx.formParam("path");
        try {
            if (fm.deleteFile(path)) {
                ctx.json(fm.checkFile("/"));
            } else {
                msg.error = "路径名错误或不能删除根文件夹";
                ctx.json(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改文件
     * path 修改文件的路径
     * content 修改文件的内容
     */
    public static void modify(Context ctx) {
        try {
            if (fm.modifyFile(ctx.formParam("path"), ctx.formParam("content"))) {
                ctx.json(fm.checkFile("/"));
            }
            else {
                msg.error = "不能修改文件夹或文件长度过长（新的文件内容超过了128个字节）";
                ctx.json(msg);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * 获取fat表
     */
    public static void disk(Context ctx) {
        try {
            for (int i = 0, j = 0; i < 2; i++) {
                for (byte b : fm.checkDiskBlock(i).unit) {
                    fat[j].used = (b != 0);
                    fat[j].next = b;
                    j++;
                }
            }
            ctx.json(fat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取磁盘块信息
     */
    public static void diskblock(Context ctx) {
        try {
            byte[] unit = fm.checkDiskBlock(Integer.parseInt(ctx.formParam("num"))).unit;
            ctx.json(Arrays.toString(unit));
//            ctx.json(unit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
