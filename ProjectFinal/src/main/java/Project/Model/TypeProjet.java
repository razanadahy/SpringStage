package Project.Model;

import Project.AccesBase.Postgres;

import java.util.Vector;

public class TypeProjet {
    int id;
    String type;

    public TypeProjet(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public TypeProjet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void insert() throws Exception {
        String sql="insert into typeProjet(nomType) values ('%s')";
        sql=String.format(sql,this.getType());
        Postgres.executeWithOutConnection(sql);
    }
    public static Vector<TypeProjet>listTypeProject() throws Exception {
        Vector<TypeProjet>types=new Vector<>();
        String sql="select idType,nomType from typeProjet where etat=0";
        Vector[]list=Postgres.allWithOutConnection(sql);
        for (int i = 0; i <list[0].size() ; i++) {
            TypeProjet typeProjet=new TypeProjet((int)list[0].elementAt(i),(String) list[1].elementAt(i));
            types.add(typeProjet);
        }
        return types;
    }
}
