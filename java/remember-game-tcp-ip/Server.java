import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static List<Socket> clientSockets = new ArrayList<>();
    static List<User> userList = new ArrayList<>();
    static Boolean runningGame = false;
    static String answer = null;

    public static void main(String[] args) {
        // ServerSocket 생성
        try {
            ServerSocket serverSocket = new ServerSocket(50001);

            // client 접속되면 socket 생성
            while (!runningGame) {
                if (userList.size() < 6) {
                    System.out.println("연결 대기 중...");
                    Socket socket = serverSocket.accept();
                    System.out.println(socket.getRemoteSocketAddress());
                    clientSockets.add(socket);
                    User user = new User(socket);
                    userList.add(user);
                    serverSocket(user);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void serverSocket(User user) {
        Socket socket = user.getSocket();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream os = socket.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(os);
                    InputStream is = socket.getInputStream();
                    DataInputStream dis = new DataInputStream(is);

                    if (user.getId() == null) {
                        dos.writeUTF("접속되었습니다.");
                        dos.writeUTF("사용자 아이디를 입력해주세요");
                        String userId = dis.readUTF();
                        dos.writeUTF("사용자 아이디가 설정되었습니다.");
                        user.setId(userId);
                        sendToAllClients("+" + user.getId() + "님이 접속하셨습니다.", socket);
                        dos.writeUTF("현재 접속자는 " + userList.size() + "명 입니다.");
                    }
                    while (true) {
                        // 시작 출력
                        dos.writeUTF("준비되시면 ready 입력하세요");

                        // 사용자 입력 받기
                        String input = dis.readUTF();
                        System.out.println(input);

                        String ready = "ready";
                        if (ready.equals(input)) {
                            user.setReady(true);
                            if (userList.size() > 1 && allReadyCheck()) {
                                startGame();
                            } else {
                                dos.writeUTF("다른 사용자가 준비하기를 대기해주세요");
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });
        thread.start();
    }

    private static void sendToAllClients(String read, Socket currentSocket) {
        for (Socket socket : clientSockets) {
            try {
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);

                if (currentSocket != socket) {
                    dos.writeUTF(read);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean allReadyCheck() {
        boolean rtn = userList.stream().allMatch(user -> user.isReady());
        if (rtn) {
            System.out.println("ready All");
        }
        return rtn;
    }

    public static String getUserInput(User user) {
        Socket socket = user.getSocket();
        try {
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            dos.writeUTF("your turn >>");
            return dis.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void resetUserState() {
        for (User user : userList) {
            user.setReady(false);
        }
    }

    private static void startGame() {
        System.out.println("==게임시작==");
        sendToAllClients("곧 게임이 시작됩니다.", null);
        clearMessage();
        runningGame = true;
        while (runningGame) {
            rememberNumber();
        }
    }

    private static void rememberNumber() {
        for (User user : userList) {
            String userInput = getUserInput(user);
            sendToAllClients(user.getId() + " 님 입력값 >> " + userInput, user.getSocket());
            if (answer == null) {
                System.out.println("초기값이 입력되었습니다.");
                answer = userInput;
                sendToAllClients("3초 뒤에 사라집니다.", user.getSocket());
                clearMessage();
            } else if (checkNumber(userInput)) {
                System.out.println("성공");
                sendToAllClients("3초 뒤에 사라집니다.", user.getSocket());
                clearMessage();
            } else {
                System.out.println("게임 종료");
                sendToAllClients(user.getId()+" 님 틀렸습니다", null);
                sendToAllClients("게임이 종료됩니다.", null);
                runningGame = false;
                answer = null;
                resetUserState();
                restartUser();
                break;
            }
        }
    }

    private static Boolean checkNumber(String userInput) {
        String subUserInput = userInput.substring(0, userInput.length() - 1);
        if (answer.equals(subUserInput)) {
            answer = userInput;
            return true;
        } else {
            return false;
        }
    }

    private static void restartUser() {
        for (User user : userList) {
            if (!user.isReady())
                serverSocket(user);
        }
    }

    private static void clearMessage() {
        try {
            Thread.sleep(3000);
            sendToAllClients("\n".repeat(100),null);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

