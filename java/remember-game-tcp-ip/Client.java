import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 50001);
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        System.out.println(dis.readUTF());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                writeMessage(dos,socket);
            }
        });
        thread.start();
        while (true){
            System.out.println(dis.readUTF());
        }
    }
    private static void writeMessage(DataOutputStream dos,Socket socket) {
        while (true) {
            try {
                dos.writeUTF(sc.nextLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
