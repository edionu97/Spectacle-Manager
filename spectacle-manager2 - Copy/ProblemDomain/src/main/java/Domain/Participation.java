package Domain;

import java.io.Serializable;

public class Participation implements Serializable {

    private Artist artist;
    private Show show;


    public Participation(int codA, int codS){
        artist = new Artist(codA,null,null,null);
        show = new Show(codS,null,null,0,0,null);
    }

    public Participation(Artist artist, Show show) {
        this.artist = artist;
        this.show = show;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    @Override
    public String toString() {

        return  artist.getNume()+ " "+
                artist.getEmail() + " " +
                show.getSeTinePe() +" " +
                show.getLocatie() + " " +
                show.getDisponibile() + " " +
                show.getVandute();
    }
}
