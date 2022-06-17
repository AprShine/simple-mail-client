package ui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.internet.MimeBodyPart;
import java.util.List;

public class MailModel {
    private final SimpleStringProperty fromUser=new SimpleStringProperty();
    private final SimpleStringProperty sendDate=new SimpleStringProperty();
    private final SimpleStringProperty subject=new SimpleStringProperty();
    private final SimpleBooleanProperty isAttachBool=new SimpleBooleanProperty();

    private String Content;

    private List<MimeBodyPart> attachFile;

    public MailModel(String fromUser,String sendDate,String subject,boolean isAttachBool){
        assert false;
        this.fromUser.set(fromUser);
        this.sendDate.set(sendDate);
        this.subject.set(subject);
        this.isAttachBool.set(isAttachBool);
    }

    public void setAttachFile(List<MimeBodyPart> attachFile) {
        this.attachFile = attachFile;
    }

    public List<MimeBodyPart> getAttachFile() {
        return attachFile;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContent() {
        return Content;
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
    public boolean getIsAttachBool(){
        return isAttachBool.get();
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
