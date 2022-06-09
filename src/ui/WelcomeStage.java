package ui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Use:登录窗口的窗口类
 * Author:@AprShine
 * Time:2022.6.6
 */
public class WelcomeStage extends Stage {
    //比较重要的数据成员

    //方法体
    public WelcomeStage(){
        init();
        addComponent();
        addCommonListener();
    }
    private void init(){
        setTitle("JavaMail");
        getIcons().add(new Image("images/logo.png"));
        //将默认的标题栏隐藏
//        initStyle(StageStyle.TRANSPARENT);
        setWidth(800);
        setHeight(600);
    }
    private void addComponent(){
        //layout
        AnchorPane root=new AnchorPane();
        root.setId("root");
        //manage scene
        Scene scene=new Scene(root);
        //Scene与CSS进行绑定
        scene.getStylesheets().add("css/WelcomeStage.css");
        setScene(scene);
        //component

        ////标题栏////

        // easy listener


    }
    private void addCommonListener(){

        //监听坐标变化测试
//        xProperty().addListener((observable, oldValue, newValue) -> System.out.println("x坐标为:"+newValue));
//        yProperty().addListener((observable, oldValue, newValue) -> System.out.println("y坐标为:"+newValue));
//        widthProperty().addListener(((observable, oldValue, newValue) -> System.out.println("宽度为:"+ newValue)));
//        heightProperty().addListener(((observable, oldValue, newValue) -> System.out.println("高度为"+newValue)));

    }

}
