import java.net.InetAddress;
import java.net.UnknownHostException;

public class Inet {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inet = InetAddress.getLocalHost();
        System.out.println("IP Address: " + inet.getHostAddress());
        System.out.println("IP Name: " + inet.getHostName());


    }
}
