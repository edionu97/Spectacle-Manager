package Domain;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;

public class Show implements Serializable {

    private int codS;

    private Date seTinePe;

    private Time incepeLa;

    private int disponibile,vandute;

    private String locatie;

    public Show(){}

    public Show(int codS, Time incepeLa, Date seTinePe, int disponibile, int vandute, String locatie) {
        this.codS = codS;
        this.incepeLa = incepeLa;
        this.seTinePe = seTinePe;
        this.disponibile = disponibile;
        this.vandute = vandute;
        this.locatie = locatie;
    }

    public int getCodS() {
        return codS;
    }

    public void setCodS(int codS) {
        this.codS = codS;
    }

    public Time getIncepeLa() {
        return incepeLa;
    }

    public void setIncepeLa(Time incepeLa) {
        this.incepeLa = incepeLa;
    }

    public Date getSeTinePe() {
        return seTinePe;
    }

    public void setSeTinePe(Date seTinePe) {
        this.seTinePe = seTinePe;
    }

    public int getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(int disponibile) {
        this.disponibile = disponibile;
    }

    public int getVandute() {
        return vandute;
    }

    public void setVandute(int vandute) {
        this.vandute = vandute;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    @Override
    public String toString(){
        return codS + " "+ seTinePe + " " + incepeLa + " " + locatie + " "+ disponibile + " " + vandute;
    }
}
