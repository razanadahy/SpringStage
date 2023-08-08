package Project.Model;

import Project.AccesBase.Postgres;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Vector;

public class Protection {
    String nom,description;
    Difficulte difficulte;
    MultipartFile fichier;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Difficulte getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(Difficulte difficulte) {
        this.difficulte = difficulte;
    }

    public Protection(String nom, String description, Difficulte difficulte, MultipartFile fichier) {
        this.nom = nom;
        this.description = description;
        this.difficulte = difficulte;
        this.fichier = fichier;
    }

    public MultipartFile getFichier() {
        return fichier;
    }

    public void setFichier(MultipartFile fichier) {
        this.fichier = fichier;
    }

    public Protection() {
    }

    public long insertProtection(Connection connection) throws Exception {
        String sql="select nextval('secProtection')";
        Vector valiny= Postgres.allWithConnection(sql,connection)[0];
        if (!valiny.isEmpty()){
            sql="insert into protection(idProtection, nomProtection, idDifficulte, description, file) VALUES ('%s','%s','%s','%s','%s')";
            long val=((Long) valiny.elementAt(0));
            String originalFileName = fichier.getOriginalFilename();
            File fileDir = ResourceUtils.getFile("File/");
            Path filePath = Paths.get(fileDir.getAbsolutePath(), originalFileName);
            Files.copy(fichier.getInputStream(), filePath);
            String format = String.format(sql, val, nom, difficulte.getId(), description, originalFileName);
            Postgres.executeWithConnection(format,connection);
            return val;
        }
        return -100;
    }
}
