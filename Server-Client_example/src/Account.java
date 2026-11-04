import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username;
    private int authToken;
    private List<Message> messagebox;

    public Account(String username,int authToken){
        this.username=username;
        this.authToken=authToken;
        messagebox= new ArrayList<>();
    }

    public String getUsername(){
        return username;
    }
    public int getAuthToken(){
        return authToken;
    }
    public List<Message> getMessagebox(){
        return messagebox;
    }
}
