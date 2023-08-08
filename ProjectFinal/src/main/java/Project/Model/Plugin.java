package Project.Model;

import Project.AccesBase.Postgres;

import java.sql.Connection;
import java.util.Vector;

public class Plugin {
    String nom,ssh;

    public Plugin(String nom, String ssh) {
        this.nom = nom;
        this.ssh = ssh;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSsh() {
        return ssh;
    }

    public void setSsh(String ssh) {
        this.ssh = ssh;
    }

    public Plugin() {
    }
    public long insertPlugin(Connection connection) throws Exception {
        String sql="select nextval('secPlug')";
        Vector valiny= Postgres.allWithConnection(sql,connection)[0];
        if (!valiny.isEmpty()){
            sql="insert into plugin(idPlugin, nomPlugin, ssh) VALUES ('%s','%s','%s')";
            long val=((Long) valiny.elementAt(0));
            sql=String.format(sql,val,nom,ssh);
            Postgres.executeWithConnection(sql,connection);
            return val;
        }
        return -100;
    }
}
