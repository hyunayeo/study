import java.net.Socket;

public class User {
    private String id;
    private Socket socket;
    private boolean ready;
    public User(Socket socket){
        this.socket = socket;
        this.ready = false;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
