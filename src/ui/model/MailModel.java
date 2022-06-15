package ui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class MailModel {
    private SimpleStringProperty fromUser=new SimpleStringProperty();
    private SimpleStringProperty sendDate=new SimpleStringProperty();
    private SimpleStringProperty subject=new SimpleStringProperty();
    private SimpleBooleanProperty isAttachBool=new SimpleBooleanProperty();

    public MailModel(String fromUser,String sendDate,String subject,boolean isAttachBool){
        assert false;
        this.fromUser.set(fromUser);
        this.sendDate.set(sendDate);
        this.subject.set(subject);
        this.isAttachBool.set(isAttachBool);
    }

    public String getFromUser() {
        return fromUser.get();
    }

    public String getSendDate() {
        return sendDate.get();
    }

    public String getSubject() {
        return subject.get();
    }
    public SimpleStringProperty getFromUserProperty(){
        return fromUser;
    }
    public SimpleStringProperty getSendDateProperty(){
        return sendDate;
    }
    public SimpleStringProperty getSubjectProperty(){
        return subject;
    }
    public SimpleBooleanProperty getIsAttachBoolProperty(){
        return isAttachBool;
    }
}
