package Project;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BackgroundTask {
    @Scheduled(cron = "25 25 * * * *")
    public void executeTask() {
        System.out.println("La tâche planifiée s'exécute maintenant !");
    }
}
