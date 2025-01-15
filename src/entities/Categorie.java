package entities;

public class Categorie {
    private static int count;
    private int id;
    private String code;
    private String libelle;

    public Categorie(String code, String libelle) {
        this.id = ++count;
        this.code = code;
        this.libelle = libelle;
    }

    public Categorie(int id) {
        this.id = id;
    }

    public Categorie(int id, String code, String libelle) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return   libelle ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
