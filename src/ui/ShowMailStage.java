package ui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ShowMailStage extends Stage {
    public ShowMailStage(){
        init();
        addComponent();
    }
    private void init() {
        setTitle("信件内容");
        getIcons().add(new Image("images/logo.png"));
        setResizable(false);
        setWidth(550);
        setHeight(400);
    }
    private void addComponent() {
        //布局
        VBox root=new VBox();
        Scene scene=new Scene(root);
        setScene(scene);
        //组件
        WebView webView=new WebView();
        //组装
        root.getChildren().add(webView);
    }

}
