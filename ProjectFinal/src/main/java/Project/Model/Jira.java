package Project.Model;

import Project.AccesBase.Postgres;

import java.sql.Connection;

public class Jira {
    String reference,url;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Jira() {
    }
    public void insertJira(long idProjet, Connection connection) throws Exception {
        String sql="insert into jira(reference, url, idProjet) VALUES ('%s','%s',%s)";
        sql=String.format(sql,reference,url,idProjet);
        Postgres.executeWithConnection(sql,connection);
    }
}
