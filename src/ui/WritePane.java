package ui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class WritePane extends VBox {
    public WritePane(){
        //布局
        VBox top=new VBox();
        top.setId("top");
        HBox top_fromUser=new HBox();
        HBox top_toUser=new HBox();
        VBox content=new VBox();
        content.setId("content");
        HBox bottom=new HBox();
        bottom.setId("bottom");
        //组件
        HTMLEditor htmlEditor=new HTMLEditor();
        htmlEditor.setId("editor");
        htmlEditor.setHtmlText("输入您的内容...");
        content.getChildren().add(htmlEditor);
        Button send=new Button("发送");
        Button clear=new Button("清除");
        bottom.getChildren().addAll(send,clear);
        //组装
        getChildren().addAll(top,content,bottom);
        //监听器
        clear.setOnAction(event -> htmlEditor.setHtmlText(""));
        send.setOnAction(event -> System.out.println(htmlEditor.getHtmlText()));
    }
}
