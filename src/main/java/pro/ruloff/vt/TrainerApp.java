package pro.ruloff.vt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pro.ruloff.vt.controler.TrainerController;

@SpringBootApplication
public class TrainerApp {

  public static void main(String[] args) {
    TrainerController trainer = SpringApplication.run(TrainerApp.class)
        .getBean(TrainerController.class);
    trainer.mainLoop();
  }
}
