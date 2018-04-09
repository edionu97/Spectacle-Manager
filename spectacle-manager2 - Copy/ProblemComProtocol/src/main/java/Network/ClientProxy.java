package Network;

import Domain.Employee;
import Domain.Participation;
import Domain.Sale;
import Domain.Show;
import Network.Requests.*;
import Network.Response.*;
import Utils.Observer;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientProxy implements IClientProxy {

    private int serverPort = -1;
    private String serverLocation = null;

    private ObjectOutputStream writeToServer;
    private ObjectInputStream readFromServer;
    private Socket communicationChanel;
    private BlockingQueue <Response> responses = new LinkedBlockingQueue<>();
    private List<Observer> observers = new LinkedList<>();
    private volatile boolean connected = true;


    /**
     * Construct an new channel of communication between a client and a server
     * @param propsLocation (the location of the property file)
     */

    public ClientProxy(String propsLocation){

        try {
            System.getProperties().load(getClass().getResourceAsStream(propsLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverPort = Integer.parseInt(System.getProperty("serverPort"));

        serverLocation = System.getProperty("serverLocation");

    }

    /**
     * Making a connection with the server
     */

    private void makeConnection(){

        try {
            connected = true;

            communicationChanel = new Socket(serverLocation,serverPort);
            writeToServer = new ObjectOutputStream(communicationChanel.getOutputStream());
            readFromServer = new ObjectInputStream(communicationChanel.getInputStream());
            writeToServer.flush();

        } catch (Exception e) {
            System.out.println("makeConnection-->" + e.getMessage());
        }

        new Thread(this::workerJob).start();
    }

    /**
     * Closing the connection established with the server
     */

    private void closeConnection(){

        try{
            readFromServer.close();
            writeToServer.close();
            communicationChanel.close();
            connected = false;
        }catch (Exception e){
            System.out.println("closeConnection->" + e.getMessage());
        }
    }

    /**
     * Sending an request to server
     * @param request -> any kind of request(LoginRequest,LogoutRequest and so on)
     */

    private void sendRequest(Request request){

        try{
            writeToServer.writeObject(request);
            writeToServer.flush();
        }catch (Exception e){
            System.out.println("sendRequest->" + e.getMessage());
        }
    }

    /**
     * The function on which reads all the server responses
     */


    private void workerJob(){

        while(connected){

            try {

                Response response = (Response)readFromServer.readObject();

                if(!(response instanceof  UpdateResponse)) {
                    responses.put(response);
                    continue;
                }

                notifyAllObservers();

            }catch (Exception e){
                connected = false;//if the server connection is lost
            }

        }
    }

    /**
     * the function which gets an response from the reading thread
     * @return the read response
     */

    private Response readResponse(){
        Response response = null;

        try{
            response = responses.take();
        }catch (Exception e){
            System.out.println("readResponse->" + e.getMessage());
        }

        return response;
    }

    /**
     *
     * @param userName ->the user's name
     * @param password ->the user's password
     * @return true if the user exists in database of false contrary
     */

    @Override
    public boolean canLogin(String userName, String password) {

        makeConnection();

        LoginRequest request = new LoginRequest(userName,password);

        sendRequest(request);

        boolean connected =  ((LoginResponse)readResponse()).getLoginMessage().equals("true");

        if(!connected){
            closeConnection();
        }

        return connected;
    }

    /**
     *
     * @param userName-> the user's name
     * @param password-> the user's password
     * @return all the information about the logged user
     */

    @Override
    public Employee getInfoAfterLog(String userName, String password) {
        sendRequest(new UserRequest(userName,password));
        return ((UserResponse)readResponse()).getEmployee();
    }



    @Override
    public boolean isLoggedIn(String username, String password) {
        return false;
    }

    @Override
    public List<Employee> getAllUsers() {
        return null;
    }


    /**
     *
     * @param username-> logs out the logged user
     */

    @Override
    public void logout(String username) {
        Request request = new LogoutRequest(username);
        sendRequest(request);
        readResponse();
    }

    /**
     *
     * @param date-> a correct date
     * @return a list which contains all participation with the date equal to @date
     */

    @Override
    public List<Participation> filterByDate(Date date) {
        sendRequest(new FilterByDateRequest(date));
        return ((FilterByDateResponse)readResponse()).getFilteredList();
    }

    /**
     *
     * @return a list with all the existing participations from database
     */

    @Override
    public List<Participation> getAllParticip() {
        sendRequest(new GetAllParticipationRequest());
        return ((GetAllParticipationResponse)readResponse()).getList();
    }

    /**
     *
     * @param clientName ->the name of the client
     * @param desiredSeats ->the number of seats which the user want to rent
     * @param showCode -> the code of the show
     * @param codAg -> the code of the the seller
     * @throws Exception if there are no more available seats at the spectacle or if in database the client already bought some tickets at the show
     */

    @Override
    public void addVanzare(String clientName, int desiredSeats, int showCode, int codAg) throws Exception {
        sendRequest(new SaleRequest(clientName, desiredSeats, showCode, codAg));
        SaleResponse saleResponse = (SaleResponse) readResponse();
        if (!saleResponse.getError().equals("")) throw new Exception(saleResponse.getError());
    }

    @Override
    public List<Show> getSpec() {
        sendRequest(new GetSpecRequest());
        return ((GetSpecResponse)readResponse()).getList();
    }

    @Override
    public List<Sale> getAllSales() {
        return null;
    }


    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        observers.forEach(x-> Platform.runLater(x::reloadContent));
    }
}
