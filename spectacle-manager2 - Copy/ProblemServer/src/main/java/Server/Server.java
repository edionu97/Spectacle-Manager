package Server;

import GuiController.ServerController;
import Service.ILoginService;
import Service.IParticipationService;
import Service.ISaleService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class Server {

    private int port = -1;

    private Map<String, ClientSession> connectedClients = new TreeMap<>();

    private ILoginService loginService;
    private ISaleService saleService;
    private IParticipationService participationService;
    private ServerController binder;


    public void setBinder(ServerController binder) {
        this.binder = binder;
    }

    public Server(ILoginService loginService, ISaleService saleService, IParticipationService participationService, String propertyFile) {
        this.loginService = loginService;
        this.saleService = saleService;
        this.participationService = participationService;

        try{

            System.getProperties().load(getClass().getResourceAsStream(propertyFile));

            port = Integer.parseInt(System.getProperty("serverPort"));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    void addUser(String username, ClientSession session){
        connectedClients.put(username,session);
    }

    void removeUser(String username, ClientSession session){
        connectedClients.remove(username);
    }

    void updateContent(){
        for(Map.Entry<String, ClientSession> entry : connectedClients.entrySet()){
            ClientSession session = entry.getValue();
            session.sendUpdateResponse();
        }
    }

    Map <String, ClientSession> getSess(){
        return connectedClients;
    }


    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);

        while (true){

            System.out.println("Waiting for clients...");

            Socket client = serverSocket.accept();

            new Thread(new ClientSession(client,this,loginService,participationService,saleService,binder)).start();
        }
    }
}
