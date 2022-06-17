package ui;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ui.model.MailModel;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import java.io.IOException;


public class ShowMailStage extends Stage {

    public ShowMailStage(MailModel mailModel){
        init();
        addComponent(mailModel);
    }
    private void init() {
        setTitle("信件内容");
        getIcons().add(new Image("images/logo.png"));
        setWidth(950);
        setHeight(800);
    }
    private void addComponent(MailModel mailModel) {
        //布局
        VBox root=new VBox();
        Scene scene=new Scene(root);
        scene.getStylesheets().add("css/ShowMailStage.css");
        setScene(scene);
        HBox topBox=new HBox();
        topBox.setId("topBox");
        HBox subjectBox=new HBox();
        subjectBox.setId("subjectBox");
        HBox downloadBox=new HBox();
        downloadBox.setId("downloadBox");
        //组件
        Label fromLabel=new Label("发件人："+mailModel.getFromUser());
        fromLabel.getStyleClass().add("topLabel");
        Label sendTime=new Label("发送时间："+mailModel.getSendDate());
        sendTime.getStyleClass().add("topLabel");
        Label subject=new Label(mailModel.getSubject());
        subject.setId("subject");
        WebView webView=new WebView();
        webView.setId("webView");
        webView.getEngine().loadContent(mailModel.getContent());
        //组装
        topBox.getChildren().addAll(fromLabel,sendTime);
        subjectBox.getChildren().addAll(subject);
        root.getChildren().addAll(topBox,subjectBox);
        if(mailModel.getIsAttachBool()){
            Button download= new Button("下载附件");
            download.setOnAction(event -> {
                DirectoryChooser directoryChooser=new DirectoryChooser();
                directoryChooser.setTitle("选择附件保存路径");

                mailModel.getAttachFile().forEach(mimeBodyPart -> {
                    try {
                        mimeBodyPart.saveFile(directoryChooser.showDialog(this) +
                                MimeUtility.decodeText(mimeBodyPart.getFileName()));
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("保存成功");
                        alert.show();
                    } catch (IOException | MessagingException e) {
                        e.printStackTrace();
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("保存失败");
                        alert.show();
                    }
                });
            });
            downloadBox.getChildren().add(download);
            root.getChildren().add(downloadBox);
        }
        root.getChildren().add(webView);
        //监听器
        heightProperty().addListener((observable, oldValue, newValue) ->
                webView.setMinHeight(newValue.doubleValue()-topBox.getHeight()-subjectBox.getHeight()-10));

    }

}
