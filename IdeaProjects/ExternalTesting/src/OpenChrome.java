import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenChrome {
    public static void main(String[] args) throws IOException {
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                java.awt.Desktop.getDesktop().browse(new URI("https://www.reddit.com"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}