package pro.ruloff.vt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainerApp {

  public static void main(String[] args) {
    Object trainer = SpringApplication.run(TrainerApp.class)
        .getBean("trainerController");
    ((TrainerController) trainer).mainLoop();
  }
}
