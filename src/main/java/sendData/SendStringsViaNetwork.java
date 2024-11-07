package sendData;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendStringsViaNetwork {

    public static void main(String args[]) throws IOException {
        System.out.println("Starting Client...");
        Socket socket = new Socket("10.60.104.200", 8080);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Connection established... sending text");
        printWriter.println("Header File\n\n");
        printWriter.println("Tell me, what is it you plan");
        printWriter.println("to do with your one wild");
        printWriter.println("and precious life?");
        printWriter.println("Mary Oliver");
        System.out.println("Sending stop command");
        printWriter.println("stop");
        releaseResources(printWriter, socket);
        System.exit(0);
    }

    private static void releaseResources(PrintWriter printWriter, Socket socket) {
        printWriter.close();
 
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SendStringsViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
