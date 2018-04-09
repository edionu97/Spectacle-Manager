package Server;

import GuiController.ServerController;
import Network.Requests.*;
import Network.Response.*;
import Service.ILoginService;
import Service.IParticipationService;
import Service.ISaleService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSession implements  Runnable {

    private boolean loggedIn = true;
    private String username;
    private Socket networkChanel;
    private Server server;

    private ISaleService saleService;
    private  ILoginService loginService;
    private IParticipationService participationService;

    private ObjectInputStream readFromClient;
    private ObjectOutputStream writeToClient;

    private ServerController binder;

    ClientSession(Socket networkChanel, Server server, ILoginService loginService, IParticipationService participationService, ISaleService saleService, ServerController binder) {

        this.networkChanel = networkChanel;
        this.server = server;
        this.loginService = loginService;
        this.participationService = participationService;
        this.saleService = saleService;
        this.binder = binder;

        try {
            readFromClient = new ObjectInputStream(networkChanel.getInputStream());
            writeToClient = new ObjectOutputStream(networkChanel.getOutputStream());
            writeToClient.flush();
        } catch (IOException e) {
            System.out.println("ClientSession->" + e.getMessage());
        }

    }

    private void handleLoginRequest(LoginRequest request){

        LoginResponse response = new LoginResponse((loginService.canLogin(request.getUsername(),request.getPassword()) && server.getSess().get(request.getUsername()) == null )  +"");

        if(!response.getLoginMessage().equals("true"))loggedIn = false;
        else {
            server.addUser(request.getUsername(), this);
            binder.setText("New client connected -> " + request.getUsername());
        }

        sendResponse(response);
    }

    private void sendResponse(Response response){

        try {
            writeToClient.writeObject(response);
            writeToClient.flush();
        } catch (IOException e) {
            System.out.println("sendResponse->" + e.getMessage());
        }

    }

    private void handleLogoutRequest(LogoutRequest request){
        server.removeUser(request.getUserName(),this);
        binder.setText("Client " + request.getUserName() + " logged out");
        loggedIn = false;
        sendResponse(new LogoutResponse());
    }

    private void handleUserRequest(UserRequest request){
        sendResponse(new UserResponse(loginService.getInfoAfterLog(request.getUsername(),request.getPassword())));
    }

    private void handleGetAllParticipRequest(GetAllParticipationRequest request){
        sendResponse(new GetAllParticipationResponse(participationService.getAllParticip()));
    }

    private  void handleFilterByDateRequest(FilterByDateRequest request){
        sendResponse(new FilterByDateResponse(participationService.filterByDate(request.getDate())));
    }

    private void handleSaleRequest(SaleRequest request){

        SaleResponse response = new SaleResponse("");

        try{
            saleService.addVanzare(request.getClientName(),request.getDesiredSeats(),request.getShowCode(),request.getCodAg());

        }catch (Exception e){
            response = new SaleResponse(e.getMessage());
        }

        sendResponse(response);

        if(response.getError().equals(""))server.updateContent();
    }

    private void handleGetSpecRequest(GetSpecRequest request){
        sendResponse(new GetSpecResponse(saleService.getSpec()));
    }

    void sendUpdateResponse(){
        sendResponse(new UpdateResponse());
    }


    @Override
    public void run() {



        while(loggedIn){

            try {

                Request request = (Request)readFromClient.readObject();

                if(request instanceof LoginRequest)handleLoginRequest((LoginRequest)request);

                if(request instanceof LogoutRequest)handleLogoutRequest((LogoutRequest)request);

                if(request instanceof UserRequest)handleUserRequest((UserRequest)request);

                if(request instanceof GetAllParticipationRequest)handleGetAllParticipRequest((GetAllParticipationRequest)request);

                if(request instanceof  FilterByDateRequest) handleFilterByDateRequest((FilterByDateRequest)request);

                if(request instanceof  SaleRequest) handleSaleRequest((SaleRequest)request);

                if(request instanceof  GetSpecRequest) handleGetSpecRequest((GetSpecRequest)request);

            } catch (Exception e) {
                System.out.println("run1->" + e.getMessage());
                loggedIn = false;
            }

        }

        try {
            networkChanel.close();
        } catch (IOException e) {
            System.out.println("run2->" + e.getMessage());
        }


    }
}
