package ui;

import core.DataBuffer;
import core.SendEmailsUsingSMTP;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.MailUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class WritePane extends VBox {
    private final List<String> attachFile=new ArrayList<>();
    public WritePane(){
        //布局
        VBox top=new VBox();
        top.setId("top");
        HBox top_toUser=new HBox();
        top_toUser.setId("top_toUser");
        HBox top_fromUser=new HBox();
        top_fromUser.setId("top_fromUser");
        VBox content=new VBox();
        content.setId("content");
        HBox bottom=new HBox();
        bottom.setId("bottom");
        //组件
        Label toUserLabel=new Label("投递邮箱地址:");
        TextField tf_toUser =new TextField();
        Label subjectLabel=new Label("邮件主题:");
        TextField tf_subject=new TextField();
        Label nickLabel=new Label("发件人昵称:");
        TextField tf_fromUserNick=new TextField();
        Button addAttach=new Button("添加附件");
        Button clearAttach=new Button("清空附件");
        HTMLEditor htmlEditor=new HTMLEditor();
        htmlEditor.setId("editor");
        htmlEditor.setHtmlText("输入您的内容...");
        content.getChildren().add(htmlEditor);
        Button send=new Button("发送");
        Button clear=new Button("清除");

        //组装
        top_toUser.getChildren().addAll(toUserLabel,tf_toUser,subjectLabel,tf_subject);
        top_fromUser.getChildren().addAll(nickLabel,tf_fromUserNick,addAttach,clearAttach);
        top.getChildren().addAll(top_toUser,top_fromUser);
        bottom.getChildren().addAll(send,clear);
        getChildren().addAll(top,content,bottom);
        //监听器
        clear.setOnAction(event -> htmlEditor.setHtmlText(""));
        addAttach.setOnAction(event -> {
            Stage stage=new Stage();
            FileChooser fileChooser=new FileChooser();
            fileChooser.setTitle("选择附件");
            fileChooser.setInitialDirectory(new File("C:"+File.separator));
            List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
            if(fileList!=null){
                fileList.forEach(file -> attachFile.add(file.getAbsolutePath()));
            }
            if(attachFile.size()!=0){
                addAttach.setText("添加附件("+attachFile.size()+")");
            }
        });
        clearAttach.setOnAction(event -> {
            attachFile.clear();
            addAttach.setText("添加附件");
        });
        send.setOnAction(event -> {
            //发送前的检查
            Matcher mailMatcher = MailUtil.mailPattern.matcher(tf_toUser.getText());
            if(!mailMatcher.matches()){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("检测到非法的邮箱地址!请重试~");
                alert.show();
            }else if(tf_subject.getText().equals("")){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("邮件主题似乎为空...");
                alert.show();
            }
            else {
                //发送邮件
                System.out.println(htmlEditor.getHtmlText());
                SendEmailsUsingSMTP sendEmailsUsingSMTP = new SendEmailsUsingSMTP();
                boolean isSucceed=
                        sendEmailsUsingSMTP.connectAndSendSmtp(MailUtil.SMTPDomain,
                        MailUtil.SMTPPort,
                        MailUtil.secureConfig,
                        DataBuffer.getCurrentUser().getAccount(),
                        DataBuffer.getCurrentUser().getPwd(),
                        tf_toUser.getText(),
                        tf_subject.getText(),
                        htmlEditor.getHtmlText(),
                        attachFile.toArray(new String[0]));
                Alert alert;
                if(isSucceed){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("发送成功");
                    tf_subject.setText("");
                    htmlEditor.setHtmlText("请输入您的内容...");

                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("发送失败");
                }
                alert.show();
            }

        });
    }
}
