package Project.Model;

import Project.AccesBase.Postgres;

import java.util.Vector;

public class TypeTraitement {
    String traitement;
    int id;

    public String getTraitement() {
        return traitement;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeTraitement(String traitement, int id) {
        this.traitement = traitement;
        this.id = id;
    }

    public TypeTraitement() {
    }

    public static Vector<TypeTraitement> listTraitement() throws Exception {
        Vector<TypeTraitement> valiny=new Vector<>();
        String sql="select  idType,nomType from typeTraitement where etat=0";
        Vector[] list= Postgres.allWithOutConnection(sql);
        for (int i = 0; i <list[0].size() ; i++) {
            TypeTraitement traitement=new TypeTraitement((String) list[1].elementAt(i),(int) list[0].elementAt(i));
            valiny.add(traitement);
        }
        return valiny;
    }
}
