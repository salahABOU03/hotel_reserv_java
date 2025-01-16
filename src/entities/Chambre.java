package entities;

public class Chambre {
    private static int count;
    private int id;
    private String telephone;
    private Categorie categorie;
    private byte[] image; // L'image stockée sous forme de tableau d'octets (BLOB)

    public Chambre(String telephone, Categorie categorie, byte[] image) {
        this.id = ++count;
        this.telephone = telephone;
        this.categorie = categorie;
        this.image = image;
    }

    public Chambre(int id, String telephone, Categorie categorie, byte[] image) {
        this.id = id;
        this.telephone = telephone;
        this.categorie = categorie;
        this.image = image;
    }

    public Chambre(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Numéro " + id;
    }
}
