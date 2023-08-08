package Project.Model;

import Project.AccesBase.Postgres;

import java.sql.Connection;

public class Site {
    String nom ,
    platform, domain ;
    Plugin plugin ;
    Protection protection;
    TypeTraitement traitement ;

    public Site() {
    }

    public Site(String nom, String platform, String domain, Plugin plugin, Protection protection, TypeTraitement traitement) {
        this.nom = nom;
        this.platform = platform;
        this.domain = domain;
        this.plugin = plugin;
        this.protection = protection;
        this.traitement = traitement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public Protection getProtection() {
        return protection;
    }

    public void setProtection(Protection protection) {
        this.protection = protection;
    }

    public TypeTraitement getTraitement() {
        return traitement;
    }

    public void setTraitement(TypeTraitement traitement) {
        this.traitement = traitement;
    }

    public void insertSite(long idProjet, Connection connection) throws Exception {
        long insertPlugin=plugin.insertPlugin(connection);
        long idProtection=protection.insertProtection(connection);
        String sql="insert into site(nomSite, Plateforme, idProtection, domaine, idPlugin, idTypeTraitement, idProjet) VALUES ('%s','%s','%s','%s','%s','%s','%s')";
        sql=String.format(sql,nom,platform,idProtection,domain,insertPlugin,traitement.getId(),idProjet);
        Postgres.executeWithConnection(sql,connection);
    }
}
