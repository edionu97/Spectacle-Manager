package Domain;

import java.io.Serializable;

public class Artist implements Serializable{

    private int codA;

    private String nume,prenume,email;

    public Artist(){}

    public int getCodA() {
        return codA;
    }

    public void setCodA(int codA) {
        this.codA = codA;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Artist(int codA, String nume, String prenume, String email) {
        this.codA = codA;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }

    @Override
    public String toString(){
        return codA + " " + nume + " " + prenume + " " + email;
    }
}
