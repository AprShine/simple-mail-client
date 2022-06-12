package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Use:登录窗体类
 * @author AprShine
 */
public class LoginStage extends Stage {
    private TextField account=new TextField();
    private PasswordField pwd=new PasswordField();
    public LoginStage(){
        init();
        addComponents();
        addListener();
    }

    private void init() {
        setTitle("Login your account");
        getIcons().add(new Image("images/logo.png"));
        setWidth(550);
        setHeight(450);
    }
    private void addComponents() {
        //布局设置
        VBox root=new VBox();
        Scene scene=new Scene(root);
        scene.getStylesheets().add("css/LoginStage.css");
        setScene(scene);

        VBox top=new VBox();
        Label section=new Label("添加用户");
        section.setId("section");
        Label tip=new Label("需要输入您的用户名和密码");
        tip.setId("tip");
        top.getChildren().addAll(section,tip);
        VBox content=new VBox();
        content.setPrefHeight(getHeight());
        content.setAlignment(Pos.CENTER_LEFT);
        Label hint_1=new Label("电子邮箱地址");
        hint_1.getStyleClass().add("hint");
        account.getStyleClass().add("field");
        Label hint_2=new Label("密码");
        hint_2.getStyleClass().add("hint");
        pwd.getStyleClass().add("field");
        content.getChildren().addAll(hint_1,account,hint_2,pwd);
        AnchorPane bottom=new AnchorPane();
        bottom.setPadding(new Insets(10));
        Button confirm=new Button("确定");
        Button cancel=new Button("取消");
        AnchorPane.setBottomAnchor(confirm,0.0);
        AnchorPane.setRightAnchor(cancel,0.0);
        AnchorPane.setBottomAnchor(cancel,0.0);
        AnchorPane.setRightAnchor(confirm,55.0);
        bottom.getChildren().addAll(confirm,cancel);
        //组装
        root.getChildren().addAll(top,content,bottom);

        //监听
        heightProperty().addListener((observable, oldValue, newValue) ->
                content.setPrefHeight(getHeight()));
        cancel.setOnAction(e->this.close());
    }
    private void addListener(){

    }
}
