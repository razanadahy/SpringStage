package Project.Controller;

import Project.Model.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Vector;
@RestController
@RequestMapping("/main")
public class MainController {

    @PostMapping(value = "/projet", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void insert(@ModelAttribute Projet projet) throws Exception {
        projet.insert();
        //renvoi d'email et de notification
    }

    @GetMapping("/typeprojets")
    public Vector<TypeProjet>types() throws Exception {
        return TypeProjet.listTypeProject();
    }
    @GetMapping("/developpers")
    public Vector<Developpeur>developpeurs() throws Exception {
        return Developpeur.listDevelopper();
    }
    @GetMapping("/traitement")
    public Vector<TypeTraitement>traitements() throws Exception {
        return TypeTraitement.listTraitement();
    }
    @PostMapping("/type")
    public void insertType(@RequestBody Type type) throws Exception {
        type.insert();
    }
    @DeleteMapping("/type")
    public void deleteType(@RequestBody Type type) throws Exception {
        type.delete();
    }
    @PostMapping("/login")
    public Utilisateur login(@RequestBody Login login) throws Exception {
        System.out.println(login.getType()+" "+login.getEmail());
        return login.login();
    }
    @PostMapping("/inscription")
    public Utilisateur inscription(@RequestParam("nom")String nom,@RequestParam("email")String email,@RequestParam("mdp")String mdp,@RequestParam("type")int type) throws Exception {
        return Login.inscription(nom,email,type,mdp);
    }
    @GetMapping("/notification/{idLead}")
    public Vector<Notification> getNotification(@PathVariable int idLead) throws Exception {
        return Notification.listNotifLead(idLead);
    }
    @GetMapping("/notification/{idLead}/{idNotification}")
    public Vector<MouvementNotif>getMouvementId(@PathVariable int idLead,@PathVariable int idNotification) throws Exception {
        return MouvementNotif.listMouvement(idNotification,idLead);
    }
    @GetMapping("/notifications/{idLead}")
    public Vector<MouvementNotif>getAll(@PathVariable int idLead) throws Exception {
        return MouvementNotif.listAll(idLead);
    }
}
