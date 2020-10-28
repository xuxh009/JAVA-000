import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * desc
 *
 * @author xuxh
 * @date 2020/10/25 10:48
 */
public class MySocketServer {


    static int i = 0;
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8801);

            while (true) {
                Socket socket = server.accept();
                service(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void service(Socket socket) throws IOException {

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type:text/html;charset=utf-8");
        writer.println();
        writer.write("hello,socket-" + i++);
        writer.flush();
        writer.close();

        socket.close();
    }
}
