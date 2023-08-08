package Project.Model;

import Project.AccesBase.Postgres;

public class Type {
    int idModif ;
    String valeurModif;

    public Type(int idModif, String valeurModif) {
        this.idModif = idModif;
        this.valeurModif = valeurModif;
    }

    public int getIdModif() {
        return idModif;
    }

    public void setIdModif(int idModif) {
        this.idModif = idModif;
    }

    public String getValeurModif() {
        return valeurModif;
    }

    public void setValeurModif(String valeurModif) {
        this.valeurModif = valeurModif;
    }

    public Type() {
    }

    public void insert() throws Exception {
        String sql="insert into %s (nomType) values ('%s')";
        if (idModif==0){
            sql=String.format(sql,"typeProjet",valeurModif);
        }else{
            sql=String.format(sql,"typeTraitement",valeurModif);
        }
        Postgres.executeWithOutConnection(sql);
    }

    public void delete() throws Exception {
        String sql="update %s set etat=1 where idType='%s'";
        if (idModif==0){
            sql=String.format(sql,"typeProjet",valeurModif);
        }else{
            sql=String.format(sql,"typeTraitement",valeurModif);
        }
        Postgres.executeWithOutConnection(sql);
    }
}
