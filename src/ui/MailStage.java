package ui;

import core.DataBuffer;
import core.RetrieveEmailsUsingPOP3;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.model.MailModel;
import util.MailUtil;

import java.io.IOException;

/** Use:主体窗口类
 * @author AprShine
 * */
public class MailStage extends Stage {
    //列表组件,便于更新
    private final TableView<MailModel> mailTable=new TableView<>();
    public MailStage(){
        init();
        initInBox();
        addComponent();
    }

    private void initInBox() {
        RetrieveEmailsUsingPOP3 oep=new RetrieveEmailsUsingPOP3();
        try {
            oep.getCompleteInbox(MailUtil.POP3Domain,
                    MailUtil.POP3SSLPort,
                    DataBuffer.getCurrentUser().getAccount(),
                    DataBuffer.getCurrentUser().getPwd(),MailUtil.secureConfig);
            DataBuffer.mailTableModel.forEach(mailModel ->
                    mailTable.getItems().add(mailModel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setTitle("AS邮箱客户端");
        getIcons().add(new Image("images/logo.png"));
        setResizable(false);
        setWidth(800);
        setHeight(600);
    }
    private void addComponent() {
        //Scene设置
        HBox root=new HBox();
        Scene scene=new Scene(root);
        setScene(scene);
        scene.getStylesheets().add("css/MailStage.css");
        //添加子组件
        VBox left =new VBox();
        VBox right=new VBox();
        left.setId("left");
        right.setId("right");
        HBox inbox=new HBox();
        HBox writeMail=new HBox();
        Label inboxLabel=new Label("收件箱");
        Label writeLabel=new Label("写信");
        inbox.getStyleClass().add("buttonBox");
        inbox.setId("buttonBoxClicked");
        writeMail.getStyleClass().add("buttonBox");
        inboxLabel.getStyleClass().add(("buttonLabel"));
        writeLabel.getStyleClass().add("buttonLabel");
        mailTable.setId("table");
        TableColumn<MailModel,String> fromUser=new TableColumn<>("发件人");
        fromUser.setCellValueFactory(param -> param.getValue().getFromUserProperty());
        fromUser.getStyleClass().add("column");
        TableColumn<MailModel,String> sendDate=new TableColumn<>("接收时间");
        sendDate.setCellValueFactory(param -> param.getValue().getSendDateProperty());
        sendDate.getStyleClass().add("column");
        TableColumn<MailModel,String> subject=new TableColumn<>("主题");
        subject.setCellValueFactory(param -> param.getValue().getSubjectProperty());
        subject.getStyleClass().add("column");
        TableColumn<MailModel,Boolean> isAttach=new TableColumn<>("是否有附件");
        isAttach.setCellValueFactory(param -> param.getValue().getIsAttachBoolProperty());
        isAttach.getStyleClass().add("column");
        WritePane writePane= new WritePane();
        writePane.setId("writePane");
        //组装
        inbox.getChildren().add(inboxLabel);
        writeMail.getChildren().add(writeLabel);
        left.getChildren().addAll(inbox,writeMail);
        mailTable.getColumns().addAll(fromUser,sendDate,subject,isAttach);
        right.getChildren().add(mailTable);
        root.getChildren().addAll(left,right);
        //添加监听器
        writeMail.setOnMouseClicked(event -> {
            right.getChildren().remove(0);
            right.getChildren().add(writePane);
            writeMail.setId("buttonBoxClicked");
            inbox.setId("");
        });
        inbox.setOnMouseClicked(event -> {
            right.getChildren().remove(0);
            right.getChildren().add(mailTable);
            inbox.setId("buttonBoxClicked");
            writeMail.setId("");
        });
        mailTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getClickCount()==2){
                System.out.println("yes");
                new ShowMailStage(mailTable.getSelectionModel().
                        selectedItemProperty().get()).show();
            }
        });
    }
}
