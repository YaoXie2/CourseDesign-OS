package edu.scau.main;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * App使用Javalin作为前后端的服务器
 *
 * @author HolmesZ
 */
public class App extends Application {
    // 初始化各模块（单例模式）
    public static void init1() {
        Manager.init();
    }

    public static void serve() {
        Javalin app = Javalin.create(config -> {
            //todo 正式版需删除下一行配置
//            config.enableDevLogging();
            // 开启跨域访问
            config.enableCorsForAllOrigins();
            // 配置vue网页
            config.addStaticFiles(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
                staticFiles.location = Location.CLASSPATH;
                staticFiles.precompress = false;
            });
            config.addSinglePageRoot("/", "/public/index.html");
        }).events(event -> {
            event.serverStarted(() -> {
                System.out.println("\n\nPlease open link in browser: http://localhost:7000");
            });
        }).start(7000);

        // 确保程序退出时能正常结束服务器进程，以顺利关闭端口
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            app.stop();
            Manager.fm.save();
        }));

        app.get("/file/check", FileHandler::getFileItems);
        app.post("/file/create/file", FileHandler::createFile);
        app.post("/file/create/dir", FileHandler::createDir);
        app.post("/file/delete", FileHandler::delete);
        app.post("/file/modify", FileHandler::modify);
        app.get("/file/disk", FileHandler::disk);
        app.post("/file/diskblock", FileHandler::diskblock);

        app.post("/cpu/execute", CpuHandler::execute);
        app.post("/cpu/rr", CpuHandler::rr);
        app.get("/cpu/getinfo", CpuHandler::getinfo);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("操作系统课设启动器");
        Button btn = new Button();
        btn.setText("关闭程序");
        btn.setOnAction(event -> {
            System.exit(0);
        });
        Label label = new Label("服务器已启动\n请在浏览器打开 http://localhost:7000");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(label, btn);
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        init1();
        serve();

        try {
            Thread.sleep(1000);
            Runtime.getRuntime().exec("explorer http://localhost:7000");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 加载启动器（便于完整关闭服务器）
        launch(args);
    }
}
