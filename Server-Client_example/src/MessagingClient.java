import java.io.*;
import java.net.Socket;
public class MessagingClient {
    public static void main(String[] args)throws  Exception{
        if(args.length<4){
            System.out.println("Non valid arguments");
            return;
        }
        String IP=args[0];
        int port=Integer.parseInt(args[1]);
        StringBuilder parametres = new StringBuilder();
        parametres.append(Integer.parseInt(args[2]));
        for (int i=3;i<args.length;i++)
        parametres.append(",").append(args[i]);
        try (
            Socket socket = new Socket(IP, port);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        ){
            output.println(parametres.toString());
        String answer;
        while((answer=input.readLine())!= null)
        System.out.println(answer);}
    }
}
