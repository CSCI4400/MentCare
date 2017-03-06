package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class InternetConnection {

    //May want to use InetAddress.isReachable if this doesn't meet our needs.
    public static boolean checkInternet() {
        try {
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            System.out.println("Internet connection was successful");
            return true;
        } catch (UnknownHostException e) {
            System.out.println("Internet connection was not successful");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Internet connection was not successful");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println("Internet connection was not successful");
            e.printStackTrace();
            return false;
        }
    }
        public static void main(String[] args) {
            checkInternet();            
    }
}
