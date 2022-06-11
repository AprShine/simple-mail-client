package ui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.DrawUtil;

/**
 * Use:登录窗口的窗口类
 * @author AprShine
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
        initStyle(StageStyle.TRANSPARENT);
        setWidth(800);
        setHeight(600);
    }
    private void addComponent(){
        //layout
        VBox root=new VBox();
        root.setId("root");
        //manage scene
        Scene scene=new Scene(root);
        //Scene与CSS进行绑定
        scene.getStylesheets().add("css/WelcomeStage.css");
        setScene(scene);
        //component
        //顶部
        VBox top=new VBox();
        top.setId("top");
        top.setPrefHeight(30);
        ////自建标题栏////
        AnchorPane title=new AnchorPane();
        Button btn_min=new Button("-");
        btn_min.setId("minWindow");
        Tooltip tip_min=new Tooltip("最小化");
        btn_min.setTooltip(tip_min);
        Button btn_max=new Button("□");
        btn_max.setId("maxWindow");
        Tooltip tip_max=new Tooltip("最大化");
        btn_max.setTooltip(tip_max);
        Button btn_close=new Button("×");
        btn_close.setId("closeWindow");
        Tooltip tip_close=new Tooltip("关闭");
        btn_close.setTooltip(tip_close);
        title.getChildren().addAll(btn_min,btn_max,btn_close);
        AnchorPane.setRightAnchor(btn_close,0.0);
        AnchorPane.setRightAnchor(btn_max,55.0);
        AnchorPane.setRightAnchor(btn_min,110.0);
        top.getChildren().add(title);
        //内容
        VBox content=new VBox();

        //组装
        root.getChildren().addAll(top,content);

        // easy listener
        btn_min.setOnAction(e->setIconified(true));
        btn_max.setOnAction(e->setMaximized(!isMaximized()));
        btn_close.setOnAction(e->System.exit(0));
        //拖动事件
        EventHandler<MouseEvent> dragWindow=new EventHandler<MouseEvent>() {
            double oldX;
            double oldY;
            double oldScreenX;
            double oldScreenY;
            @Override
            public void handle(MouseEvent event) {
                if(event.getEventType()==MouseEvent.MOUSE_PRESSED){
                    oldX=getX();
                    oldY=getY();
                    oldScreenX=event.getScreenX();
                    oldScreenY=event.getScreenY();
                }else if(event.getEventType()==MouseEvent.MOUSE_DRAGGED){
                    setX(oldX+event.getScreenX()-oldScreenX);
                    setY(oldY+event.getScreenY()-oldScreenY);
                }
            }
        };
        top.setOnMousePressed(dragWindow);
        top.setOnMouseDragged(dragWindow);
        //拉伸事件
        DrawUtil.addDrawFunc(this,root);
    }
    private void addCommonListener(){

        //监听坐标变化

    }

}
