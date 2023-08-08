package Project.Model;

public class Utilisateur {
    int id,type;
    String email;

    public Utilisateur(int id, int type, String email) {
        this.id = id;
        this.type = type;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Utilisateur() {
    }
}
