package pro.ruloff.vt.utils;

import org.springframework.stereotype.Component;
import pro.ruloff.vt.formatter.TextFormatter;

@Component
public class ConsoleOutputWriter {

  private final TextFormatter textFormatter;

  public ConsoleOutputWriter(TextFormatter textFormatter) {
    this.textFormatter = textFormatter;
  }

  public void println(String text) {
    System.out.println(textFormatter.format(text));
  }
}
