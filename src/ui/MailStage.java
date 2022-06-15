package ui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/** Use:主体窗口类
 * @author AprShine
 * */
public class MailStage extends Stage {

    public MailStage(){
        init();
        addComponent();
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
        //添加子组件

        //组装

        //添加监听器

    }
}
