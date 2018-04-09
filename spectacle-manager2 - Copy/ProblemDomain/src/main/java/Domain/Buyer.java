package Domain;

import java.io.Serializable;

public class Buyer implements Serializable{

    private int codC;

    private String nume,adresa,email;

    public Buyer(){}

    public Buyer(int codC, String nume, String adresa, String email) {
        this.codC = codC;
        this.nume = nume;
        this.adresa = adresa;
        this.email = email;
    }

    public int getCodC() {
        return codC;
    }

    public void setCodC(int codC) {
        this.codC = codC;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return codC +  " " + nume + " " + adresa + " " + email;
    }
}
