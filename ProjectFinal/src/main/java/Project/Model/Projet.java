package Project.Model;

import Project.AccesBase.Postgres;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

public class Projet {
    String titre ,
    etat ,
    consigne;
    TypeProjet type ;
    Vector<Developpeur>developpeurs;
    Vector<Jira> jiras ;
    Vector<Site> sites ;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Date creation,deadLine;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getConsigne() {
        return consigne;
    }

    public void setConsigne(String consigne) {
        this.consigne = consigne;
    }



    public Vector<Developpeur> getDeveloppeurs() {
        return developpeurs;
    }

    public void setDeveloppeurs(Vector<Developpeur> developpeurs) {
        this.developpeurs = developpeurs;
    }

    public Vector<Jira> getJiras() {
        return jiras;
    }

    public void setJiras(Vector<Jira> jiras) {
        this.jiras = jiras;
    }

    public Vector<Site> getSites() {
        return sites;
    }

    public void setSites(Vector<Site> sites) {
        this.sites = sites;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Projet() {
    }

    public TypeProjet getType() {
        return type;
    }

    public void setType(TypeProjet type) {
        this.type = type;
    }

    public Projet(String titre, String etat, String consigne, TypeProjet type, Vector<Developpeur> developpeurs, Vector<Jira> jiras, Vector<Site> sites, Date creation, Date deadLine) {
        this.titre = titre;
        this.etat = etat;
        this.consigne = consigne;
        this.type = type;
        this.developpeurs = developpeurs;
        this.jiras = jiras;
        this.sites = sites;
        this.creation = creation;
        this.deadLine = deadLine;
    }


    public static long secId(Connection connection) throws Exception {
        String sql="select nextval('secProjet')";
        Vector valiny= Postgres.allWithConnection(sql,connection)[0];
        if (!valiny.isEmpty()){
            return ((Long) valiny.elementAt(0));
        }
        return -100;
    }

    public void insert() throws Exception  {
        try(Connection connection=Postgres.connection()) {
            String sql="insert into projet(idProjet, idEtat, deadlines, consigne, nomProjet, idType) VALUES ('%s','%s','%s','%s','%s','%s')";
            long idP=secId(connection);
            sql=String.format(sql,idP,etat,deadLine,consigne,titre,type.getId());
            Postgres.executeWithConnection(sql,connection);
            for (Developpeur dev : developpeurs){
                dev.insertDevelopper(idP,connection);
            }
            for (Jira jr : jiras){
                jr.insertJira(idP,connection);
            }
            for (Site st : sites){
                st.insertSite(idP,connection);
            }
        }
    }
}
