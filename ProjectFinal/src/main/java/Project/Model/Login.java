package Project.Model;

import Project.AccesBase.Postgres;
import Project.Fonction.Function;

import java.sql.Connection;
import java.util.Vector;

public class Login {
    int type;
    String email;
    String mdp;

    public Login() {
    }

    public Login(int type, String email, String mdp) {
        this.type = type;
        this.email = email;
        this.mdp = mdp;
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

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Utilisateur login() throws Exception {
        Utilisateur utilisateur=new Utilisateur(-100,type,email);
        String sql="select %s from %s where (email='%s' or nom='%s') and mdp='%s' and valid=0";
        if (type==1){
            sql=String.format(sql,"idLead", "lead", email,email,Function.sha256(mdp));
        }else{
            sql=String.format(sql,"idUtilisateur", "developper", email,email, Function.sha256(mdp));
        }
        Vector<Object> valiny= Postgres.allWithOutConnection(sql)[0];
        if (!valiny.isEmpty()){
            utilisateur.setId((Integer) valiny.elementAt(0));
            return utilisateur;
        }
        return utilisateur;
    }
    public static Utilisateur inscription(String nom,String mail,int type,String mdp) throws Exception {
        String sql="insert into %s (nom, email, mdp,valid) VALUES ('%s','%s','%s',1)";
        if (type==1){
            sql=String.format(sql,"lead",nom,mail,Function.sha256(mdp));
        }else {
            sql=String.format(sql,"developper",nom,mail,Function.sha256(mdp));
        }
        try (Connection connection=Postgres.connection()){
            Postgres.executeWithConnection(sql,connection);
            //insertion dans notification
        }
        return new Utilisateur(-50,type,mail);
    }
}
