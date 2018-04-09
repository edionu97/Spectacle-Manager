package Domain;


import org.junit.Assert;
import org.junit.Test;


public class ArtistTest {

    private Artist artist = new Artist(1,"onu","edy","onu_edy@yahoo.com");

    @Test
    public void getCodA() throws Exception {
        Assert.assertEquals(artist.getCodA(),1);
    }

    @Test
    public void setCodA() throws Exception {
        artist.setCodA(2);
        Assert.assertEquals(artist.getCodA(),2);
    }

    @Test
    public void getNume() throws Exception {
        Assert.assertEquals(artist.getNume(),"onu");
    }

    @Test
    public void setNume() throws Exception {
        artist.setNume("ONU");
        Assert.assertEquals(artist.getNume(),"ONU");
    }

    @Test
    public void setPrenume() throws Exception {
        artist.setPrenume("Eduard");
        Assert.assertEquals(artist.getPrenume(),"Eduard");
    }

    @Test
    public void getEmail() throws Exception {
        Assert.assertEquals(artist.getEmail(),"onu_edy@yahoo.com");
    }

    @Test
    public void setEmail() throws Exception {
        artist.setEmail("email");
        Assert.assertEquals(artist.getEmail(),"email");
    }



}