package Project.Fonction;

import Project.Model.MouvementNotif;
import Project.Model.Notification;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Function {
    public static String getDateDifferenceString(Date inputDate) {
        Date now = new Date();
        long diffInMillis = -now.getTime() + inputDate.getTime();
        long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);

        System.out.println("milliArg : "+inputDate.getTime());
        System.out.println("milliNow: "+now.getTime());
        if (diffInDays == 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            return "" + dateFormat.format(inputDate);
        } else if (diffInDays < 0) {
            if (diffInDays == -1) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                return "Hier à " + dateFormat.format(inputDate);
            } else {
                return "Il y a "+ (-diffInDays) +" jour(s)";
            }
        } else {
            return  "dans "+(+diffInDays ) +" jour(s)";
        }
    }

    public static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public static void main(String[] args) throws Exception {
//        String password = "teste";
//        String hashedPassword = sha256(password);
//        System.out.println("Original Password: " + password);
//        System.out.println("Hashed Password (SHA-256): " + hashedPassword);
//        System.out.println("Hashed Password (SHA-256): " +sha256("dev"));


//        Date now = new Date();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(now);
//
//        calendar.add(Calendar.DAY_OF_YEAR, -1);
//        Date tomorrow = calendar.getTime();
//        tomorrow.setHours(10);
//        String tomorrowArg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tomorrow);
//        System.out.println("Demain : " + tomorrowArg);
//        String result = getDateDifferenceString(tomorrow);
//        System.out.println(result);
//        for (Notification note : Notification.listNotifLead(2)){
//            System.out.println(note.getNom()+" "+note.getNombre()+" "+note.getIdNotification());
//        }
        for (MouvementNotif mv : MouvementNotif.listMouvement(1,2)){
            System.out.println(mv.isVue());
        }
    }
}
