package Domain;

import java.io.Serializable;

public class Sale implements Serializable {

    private Buyer buyer;
    private Show show;
    private Employee employee;
    private int vandute;

    public Sale(int codS, int codC, int codA, int vandute){
        buyer = new Buyer(codC,null,null,null);
        show = new Show(codS,null,null,0,0,null);
        employee = new Employee(codA,null,null);
        this.vandute = vandute;
    }

    public Sale(Buyer buyer, Show show, Employee employee, int vandute) {
        this.buyer = buyer;
        this.show = show;
        this.employee = employee;
        this.vandute = vandute;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }



    public Sale(){}

    public int getDorite() {
        return vandute;
    }

    public void setDorite(int vandute) {
        this.vandute = vandute;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return buyer.getNume() + " " + vandute +  " " + show.getLocatie() + " " + show.getSeTinePe();
    }
}
