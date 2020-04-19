package pro.ruloff.vt.model;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = "singleton")
public class Entry {

  public static final String SEMICOLON = ";";

  private String original;
  private String translation;

  public Entry() {
    // used by Spring
  }

  private Entry(String original, String translation) {
    this.original = original;
    this.translation = translation;
  }

  public Entry createNewEntry(String original, String translation) {
    return new Entry(original, translation);
  }

  public Entry parseRow(String text) {
    String[] split = text.split(SEMICOLON);
    return createNewEntry(split[0].trim(), split[1].trim());
  }

  public String getSemicolonSeparatedFields() {
    return original + SEMICOLON + translation;
  }
}
