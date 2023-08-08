package Project.Model;

import Project.AccesBase.Postgres;

import java.util.Date;
import java.util.Vector;

public class MouvementNotif {
    int idMouvementNotif;
    String textNotif;
    Date dateNotif;
    boolean vue;

    public int getIdMouvementNotif() {
        return idMouvementNotif;
    }

    public void setIdMouvementNotif(int idMouvementNotif) {
        this.idMouvementNotif = idMouvementNotif;
    }

    public String getTextNotif() {
        return textNotif;
    }

    public void setTextNotif(String textNotif) {
        this.textNotif = textNotif;
    }

    public Date getDateNotif() {
        return dateNotif;
    }

    public boolean isVue() {
        return vue;
    }

    public void setVue(boolean vue) {
        this.vue = vue;
    }

    public void setDateNotif(Date dateNotif) {
        this.dateNotif = dateNotif;
    }

    public MouvementNotif(int idMouvementNotif, String textNotif, Date dateNotif, boolean vue) {
        this.idMouvementNotif = idMouvementNotif;
        this.textNotif = textNotif;
        this.dateNotif = dateNotif;
        this.vue = vue;
    }

    public MouvementNotif() {
    }

    public static Vector<MouvementNotif>listMouvement(int idNotification, int idLead) throws Exception {
        Vector<MouvementNotif>mouvement=new Vector<>();
        String sql= "select COALESCE(idMouvementNotif,-100),dateNotif,textNotif,coalesce((select true from vueNotifLead where rep.idMouvementNotif=idMouvementNotif limit 1),false) as vues from vNotificationLead as rep where (idLead=%s or idLead is null) and idNotification=%s order by vues";
        sql=String.format(sql,idLead,idNotification);
        return toObject((Vector<MouvementNotif>) mouvement, sql);
    }

    public static Vector<MouvementNotif>listAll(int idLead) throws Exception {
        Vector<MouvementNotif>mouvement=new Vector<>();
        String sql= "select COALESCE(idMouvementNotif,-100),dateNotif,textNotif,coalesce((select true from vueNotifLead where rep.idMouvementNotif=idMouvementNotif limit 1),false) as vues from vNotificationLead as rep where (idLead=%s or idLead is null) order by vues";
        sql=String.format(sql,idLead);
        return toObject((Vector<MouvementNotif>) mouvement, sql);
    }

    private static Vector<MouvementNotif> toObject(Vector<MouvementNotif> mouvement, String sql) throws Exception {
        Vector[]list= Postgres.allWithOutConnection(sql);
        for (int i = 0; i <list[0].size() ; i++) {
            if ((int)list[0].elementAt(i)!=-100){
                MouvementNotif mov=new MouvementNotif(
                        (int)list[0].elementAt(i),
                        (String)list[2].elementAt(i),
                        (Date)list[1].elementAt(i),
                        (boolean)list[3].elementAt(i)
                );
                mouvement.add(mov);
            }
        }
        return mouvement;
    }
}
