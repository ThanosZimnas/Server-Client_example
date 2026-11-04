import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.regex.Pattern;

public class ClientHandler extends Thread{
    private  Socket socket;
    public ClientHandler(Socket socket){
        this.socket=socket;
    }
    public  void run(){
        try {
            BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String arguments= in.readLine();
            if (arguments==null) return;
            String[] args=arguments.split("\\,");
            int function = Integer.parseInt(args[0]);

            if (function<1 || function>6)
            {    System.out.println("Non valid request type");
            return;}
            if (function==1)
                CreateAccount(args,out);
            if (function==2)
                ShowAccounts(args,out);
            if (function==3)
                SendMessage(args,out);
            if (function==4)
                ShowInbox(args,out);
            if (function==5)
                ReadMessage(args,out);
            if (function==6)
                DeleteMessage(args,out);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void CreateAccount(String[] param,PrintWriter out){
        String username=param[1];
        if (Pattern.matches("^[a-zA-Z0-9_]+$", username))
        { if (!MessagingServer.AccountUsername.containsKey(username)){
                int authtoken = MessagingServer.TCounter.incrementAndGet();
                Account acc = new Account(username,authtoken);
                MessagingServer.AccountUsername.put(username,acc);
                MessagingServer.AccountToken.put(authtoken,acc);
                out.println(authtoken);
            }
        else {
            out.println("Sorry, the user already exists");
            return;
            }}
        else{
            out.println("Invalid Username");
            return;
        }
    }
    public  void ShowAccounts(String[] param,PrintWriter out){
        if (!MessagingServer.AccountToken.containsKey(Integer.parseInt(param[1]))){
            out.println("Invalid authToken");
            return;
        }
        int i=0;
        for (String username:MessagingServer.AccountUsername.keySet()){
            i++;
            out.println(i+". "+username);
        }
    }
    public void SendMessage(String[] param,PrintWriter out){
        if (!MessagingServer.AccountToken.containsKey(Integer.parseInt(param[1]))) {
            out.println("Invalid authToken");
            return;
        }
        if (!MessagingServer.AccountUsername.containsKey(param[2])){
            out.println("User does not exist");
            return;
        }
        Message msg = new Message(MessagingServer.MCounter.incrementAndGet(),MessagingServer.AccountToken.get(Integer.parseInt(param[1])).getUsername(),param[2],param[3]);
        MessagingServer.AccountUsername.get(param[2]).getMessagebox().add(msg);
        out.println("OK");

    }
    public  void ShowInbox(String[] param,PrintWriter out){
        if (!MessagingServer.AccountToken.containsKey(Integer.parseInt(param[1]))) {
            out.println("Invalid authToken");
            return;
        }
        List<Message> inbox = MessagingServer.AccountToken.get(Integer.parseInt(param[1])).getMessagebox();
        String asterisk;
        for (Message msg:inbox){
            if(msg.getIsRead())
                asterisk="";
            else asterisk="*";
            out.println(msg.getID()+". from: "+msg.getSender()+asterisk);
        }
    }
    public void ReadMessage(String[] param,PrintWriter out){
        if (!MessagingServer.AccountToken.containsKey(Integer.parseInt(param[1]))) {
            out.println("Invalid authToken");
            return;
        }
        for (Message msg:MessagingServer.AccountToken.get(Integer.parseInt(param[1])).getMessagebox()){
            if ( msg.getID()==Integer.parseInt(param[2]) )
                {out.println(("("+msg.getSender()+")"+msg.getBody()));
                msg.setIsRead(true);
                return;}
        }
        out.println("Message ID does not exist");

    }
    public void DeleteMessage(String[] param,PrintWriter out){
        if (!MessagingServer.AccountToken.containsKey(Integer.parseInt(param[1]))) {
            out.println("Invalid authToken");
            return;
        }
        out.println(MessagingServer.AccountToken.get(Integer.parseInt(param[1])).getMessagebox().removeIf(msg->msg.getID()==Integer.parseInt(param[2])) ? "OK" : "Message does not exist");
    }



}
