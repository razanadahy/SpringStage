package Project.Model;

import Project.AccesBase.Postgres;

import java.sql.Connection;
import java.util.Vector;


public class Developpeur {

    int id;
    String nom,email;
    double estimation;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getEstimation() {
        return estimation;
    }

    public void setEstimation(double estimation) {
        this.estimation = estimation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Developpeur(int id, String nom, String email, double estimation) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.estimation = estimation;
    }

    public Developpeur() {
    }

    public static Vector<Developpeur> listDevelopper() throws Exception {
        Vector<Developpeur>developpeurs=new Vector<>();
        String sql="select idUtilisateur, nom, email from developper where valid=0";
        Vector [] list= Postgres.allWithOutConnection(sql);
        for (int i = 0; i <list[0].size() ; i++) {
            Developpeur dev=new Developpeur((int) list[0].elementAt(i),(String) list[1].elementAt(i),(String) list[2].elementAt(i),0);
            developpeurs.add(dev);
        }
        return developpeurs;
    }
    public void insertDevelopper(long idProjet, Connection connection) throws Exception {
        String sql="insert into respDev(idProject, idDev, tempsEstimation) VALUES ('%s','%s',0)";
        sql=String.format(sql,idProjet,id);
        Postgres.executeWithConnection(sql,connection);
    }
}
