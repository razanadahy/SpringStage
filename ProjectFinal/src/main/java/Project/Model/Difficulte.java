package Project.Model;

public class Difficulte {
    int id;
    String nom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Difficulte() {
    }

    public Difficulte(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
}
