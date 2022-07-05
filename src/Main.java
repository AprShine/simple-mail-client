import javafx.stage.Stage;
import javafx.application.Application;

import ui.WelcomeStage;

/**
 * Use:Main主函数类
 * @author AprShine
 */
public class Main extends Application{
    /** 主窗口:欢迎 */
    public static WelcomeStage welcomeStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //不使用传递的形参,采用自己创建的Stage类
        welcomeStage =new WelcomeStage();
//        //显示相应的UI界面
        welcomeStage.show();

    }
}
