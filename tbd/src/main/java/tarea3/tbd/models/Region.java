package tarea3.tbd.models;

public class Region {
    private int gid;
    private String nom_reg;
    private String nom_prov;
    private String cod_com;
    private String nom_com;
    private float cod_regi;
    private float superficie;
    private int poblac02;
    private int pobl2010;
    private float shape_leng;
    private float shape_area;
    private double longitude;
    private double latitude;

    
    public int getId() {
        return gid;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public float getShape_area() {
        return shape_area;
    }
    public void setShape_area(float shape_area) {
        this.shape_area = shape_area;
    }
    public float getShape_leng() {
        return shape_leng;
    }
    public void setShape_leng(float shape_leng) {
        this.shape_leng = shape_leng;
    }
    public int getPobl2010() {
        return pobl2010;
    }
    public void setPobl2010(int pobl2010) {
        this.pobl2010 = pobl2010;
    }
    public int getPoblac02() {
        return poblac02;
    }
    public void setPoblac02(int poblac02) {
        this.poblac02 = poblac02;
    }
    public float getSuperficie() {
        return superficie;
    }
    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }
    public float getCod_regi() {
        return cod_regi;
    }
    public void setCod_regi(float cod_regi) {
        this.cod_regi = cod_regi;
    }
    public String getNom_com() {
        return nom_com;
    }
    public void setNom_com(String nom_com) {
        this.nom_com = nom_com;
    }
    public String getCod_com() {
        return cod_com;
    }
    public void setCod_com(String cod_com) {
        this.cod_com = cod_com;
    }
    public String getNom_prov() {
        return nom_prov;
    }
    public void setNom_prov(String nom_prov) {
        this.nom_prov = nom_prov;
    }
    public String getNom_reg() {
        return nom_reg;
    }
    public void setNom_reg(String nom_reg) {
        this.nom_reg = nom_reg;
    }
    public void setId(int gid) {
        this.gid = gid;
    }
}


