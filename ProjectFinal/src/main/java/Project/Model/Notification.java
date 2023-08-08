package Project.Model;

import Project.AccesBase.Postgres;

import java.util.Vector;

public class Notification {
    int idNotification;
    String nom;
    int nombre;

    public Notification(int idNotification, String nom, int nombre) {
        this.idNotification = idNotification;
        this.nom = nom;
        this.nombre = nombre;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public static Vector<Notification>listNotifLead(int idLead) throws Exception {
        Vector<Notification>list = new Vector<>();

        String sql="select count(idMouvementNotif)-(select count(idMouvementNotif) from vNotificationLead where rep.idNotification=vNotificationLead.idNotification and vNotificationLead.idLead=%s )\n" +
                "    as nombre,notification,idNotification\n" +
                "from vNotificationLead as rep\n" +
                "group by notification, idNotification order by nombre desc";

        sql=String.format(sql,idLead);
        Vector[]valiny= Postgres.allWithOutConnection(sql);
        for (int i = 0; i <valiny[0].size() ; i++) {
            list.add(new Notification((int)valiny[2].elementAt(i),(String) valiny[1].elementAt(i), (int)(long) valiny[0].elementAt(i)));
        }
        return list;
    }
}
