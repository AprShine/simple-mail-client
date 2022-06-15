package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mail.RetrieveEmailsUsingPOP3;
import util.ConnectStatus;
import util.MailUtil;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;


/**
 * Use:登录窗体类
 * @author AprShine
 */
public class LoginStage extends Stage {
    private boolean isRightMailBool=false;
    private boolean isNotNullBool=false;
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
        setResizable(false);
        setWidth(550);
        setHeight(400);
    }
    private void addComponents() {
        //布局设置
        VBox root=new VBox();
        root.setId("root");
        Scene scene=new Scene(root);
        scene.getStylesheets().add("css/LoginStage.css");
        setScene(scene);
        //添加组件
        VBox top=new VBox();
        Label section=new Label("添加用户");
        section.setId("section");
        VBox.setMargin(section,new Insets(20,10,15,15));
        Label tip=new Label("需要输入您的用户名和密码。");
        tip.setId("tip");
        VBox.setMargin(tip,new Insets(0,10,15,15));
        top.getChildren().addAll(section,tip);
        VBox content=new VBox();
        content.setPrefHeight(getHeight());
        content.setAlignment(Pos.CENTER_LEFT);
        Label hint_1=new Label("电子邮箱地址");
        hint_1.getStyleClass().add("hint");
        VBox.setMargin(hint_1,new Insets(0,0,0,15));
        account.getStyleClass().add("field");
        VBox.setMargin(account,new Insets(5,15,15,15));
        Tooltip accTip=new Tooltip("电子邮箱地址");
        account.setTooltip(accTip);
        account.setPromptText("请输入电子邮箱地址");
        Label hint_2=new Label("密码/授权码");
        hint_2.getStyleClass().add("hint");
        VBox.setMargin(hint_2,new Insets(0,0,0,15));
        pwd.getStyleClass().add("field");
        VBox.setMargin(pwd,new Insets(5,15,15,15));
        Tooltip pwdTip=new Tooltip("密码或授权码");
        pwd.setTooltip(pwdTip);
        pwd.setPromptText("请输入密码或授权码");
        content.getChildren().addAll(hint_1,account,hint_2,pwd);
        AnchorPane bottom=new AnchorPane();
        bottom.setId("bottom");
        bottom.setPadding(new Insets(10));
        Button confirm=new Button("确定");
        confirm.setDisable(true);
        Button cancel=new Button("取消");
        AnchorPane.setBottomAnchor(confirm,0.0);
        AnchorPane.setRightAnchor(cancel,0.0);
        AnchorPane.setBottomAnchor(cancel,0.0);
        AnchorPane.setRightAnchor(confirm,55.0);
        bottom.getChildren().addAll(confirm,cancel);
        //组装
        root.getChildren().addAll(top,content,bottom);

        //简单监听
        heightProperty().addListener((observable, oldValue, newValue) ->
                content.setPrefHeight(getHeight()));
        cancel.setOnAction(e->this.close());
        //监听是否是正确的邮箱
        account.textProperty().addListener((observable, oldValue, newValue) -> {
            Matcher mailMatcher= MailUtil.mailPattern.matcher(newValue);
            isRightMailBool = mailMatcher.matches();
            confirm.setDisable(!isRightMailBool || !isNotNullBool);
        });
        //监听密码是否为空值
        pwd.textProperty().addListener((observable,oldValue,newValue) -> {
            isNotNullBool= !newValue.equals("");
            confirm.setDisable(!isRightMailBool || !isNotNullBool);
        });
        //监听按钮状态
        confirm.setOnAction(event -> {
            //使用异步操作,防止出现未响应
            CompletableFuture<ConnectStatus> waitRes=
                    CompletableFuture.supplyAsync(()-> RetrieveEmailsUsingPOP3.getConnectionStatus(MailUtil.POP3Domain,
                            MailUtil.POP3SSLPort,
                            account.getText(),
                            pwd.getText()));
            try {
                ConnectStatus status = waitRes.get();
                if(status.equals(ConnectStatus.CONNECTED_POP3)){
                    MailStage mailStage=new MailStage();
                    mailStage.show();
                    this.close();
                    try {
                        Field field= Class.forName("Main").
                                getDeclaredField("welcomeStage");
                        field.setAccessible(true);
                        WelcomeStage welcomeStage=(WelcomeStage) field.get(null);
                        welcomeStage.close();
                    } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else if(status.equals(ConnectStatus.NO_PROVIDER_FOR_POP3)){
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("无法查询到服务器!");
                    alert.show();
                }else {
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("账号或密码错误!");
                    alert.show();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        });
    }
    private void addListener(){

    }
}
