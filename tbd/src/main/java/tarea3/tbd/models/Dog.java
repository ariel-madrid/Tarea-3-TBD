package tarea3.tbd.models;

//import org.postgis.PGgeometry;
//import org.postgis.Point;

public class Dog {
    private Integer id;
    private String name;
    private double longitude;
    private double latitude;
    private double cod_regi;
    private Integer radio;
    //private PGgeometry location;

    public Integer getId() {
        return id;
    }

    /* public PGgeometry getLocation() {
        return location;
    }

    public void setLocation(PGgeometry location) {
        this.location = location;
    } */

    public Integer getRadio() {
        return radio;
    }

    public void setRadio(Integer radio) {
        this.radio = radio;
    }

    public void setCodRegi(double cod_regi)
    {
        this.cod_regi = cod_regi;
    }

    public double getCodRegi()
    {
        return this.cod_regi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
