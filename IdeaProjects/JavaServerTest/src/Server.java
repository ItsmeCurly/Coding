import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String [] args) throws IOException {
        int portNumber = 80 ;
        System.out.println("Creating server socket on port " + portNumber);
        ServerSocket ss = new ServerSocket(portNumber);
        while(true) {
            Socket socket = ss.accept();
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("What's your name?");

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = br.readLine();

            pw.println("Hello, " + str);
            pw.close();
            socket.close();

            System.out.println("Just said hello to: " + str);
        }
    }
}
