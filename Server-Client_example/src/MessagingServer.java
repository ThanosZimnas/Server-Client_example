import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;



public class MessagingServer {
    protected static Map<Integer,Account> AccountToken = new ConcurrentHashMap<>();
    protected static Map<String,Account> AccountUsername = new ConcurrentHashMap<>();
    protected static AtomicInteger TCounter = new AtomicInteger(1024);
    protected static AtomicInteger MCounter = new AtomicInteger(1);
    public static void main(String[] args) throws Exception{
        if (args.length!=1){
            System.out.println("Non valid port selection");
            return;
        }
        int port=Integer.parseInt(args[0]);
        ServerSocket ssocket = new ServerSocket((port));
        System.out.println("Server is now running on port "+port);
        while (true){
          Socket csocket =  ssocket.accept();
          new ClientHandler(csocket).start();
        }

    }
}
