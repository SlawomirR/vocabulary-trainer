package pro.ruloff.vt;

import java.util.Random;
import java.util.Scanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeansCollection {

  @Bean
  @Scope(scopeName = "singleton")
  Scanner scanner() {
    return new Scanner(System.in);
  }

  @Bean
  @Scope(scopeName = "singleton")
  Random random() {
    return new Random();
  }
}
