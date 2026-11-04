public class Message {
    private int id;
    private boolean isRead;
    private String sender;
    private String receiver;
    private  String body;

    public Message(int id,String sender,String receiver,String body){
        this.id=id;
        this.isRead=false;
        this.sender=sender;
        this.receiver=receiver;
        this.body=body;
    }
    public int getID(){
        return id;
    }

    public void setIsRead(boolean status){
        this.isRead=status;
        return;
    }
    public  boolean getIsRead(){
        return isRead;
    }
    public String getSender(){
        return sender;
    }
    public String getReceiver(){
        return receiver;
    }
    public String getBody(){
        return body;
    }
}
