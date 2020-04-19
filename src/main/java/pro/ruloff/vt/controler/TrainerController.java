package pro.ruloff.vt.controler;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.springframework.stereotype.Controller;
import pro.ruloff.vt.model.Entry;
import pro.ruloff.vt.service.VtService;
import pro.ruloff.vt.utils.ConsoleOutputWriter;

@Controller
public class TrainerController {

  private static final int NUMBER_OF_REPETITIONS = 10;
  private static final int UNDEFINED = -1;
  private static final int ADD_ENTRY = 0;
  private static final int TEST = 1;
  private static final int CLOSE_APP = 2;

  private final VtService vtService;
  private final ConsoleOutputWriter consoleOutputWriter;
  private final Scanner scanner;

  public TrainerController(
      VtService vtService,
      ConsoleOutputWriter consoleOutputWriter,
      Scanner scanner) {
    this.vtService = vtService;
    this.consoleOutputWriter = consoleOutputWriter;
    this.scanner = scanner;
  }

  public void mainLoop() {
    consoleOutputWriter.println("Welcome in Vocabulary Trainer Application.");
    consoleOutputWriter.println("We loaded previously saved data.");

    int option = UNDEFINED;
    while (option != CLOSE_APP) {
      printMenu();
      option = chooseOption();
      executeOption(option);
    }
  }

  private void executeOption(int option) {
    switch (option) {
      case ADD_ENTRY:
        addEntry();
        break;
      case TEST:
        practice();
        break;
      case CLOSE_APP:
        close();
        break;
      default:
        consoleOutputWriter.println("Unknown option");
    }
  }

  private void practice() {
    final List<Entry> entries = vtService.loadData();
    if (entries.isEmpty()) {
      consoleOutputWriter.println("Please, add at least one entry to the database.");
      return;
    }
    int testSize = Math.min(entries.size(), NUMBER_OF_REPETITIONS);
    Set<Entry> randomEntries = vtService.getRandomEntries(testSize);
    int score = 0;
    for (Entry singleEntry : randomEntries) {
      consoleOutputWriter.println("Enter translation for: \"" + singleEntry.getOriginal() + "\"");
      String translation = scanner.nextLine();
      if (singleEntry.getTranslation().equalsIgnoreCase(translation)) {
        consoleOutputWriter.println("Correct answer");
        score++;
      } else {
        consoleOutputWriter.println("Wrong answer - " + singleEntry.getTranslation());
      }
    }
    consoleOutputWriter.println("Your score: " + score + "/" + testSize);
  }

  private void addEntry() {
    consoleOutputWriter.println("Give an original sentence:");
    final String original = trimSemicolon(scanner.nextLine());
    consoleOutputWriter.println("Give an expected translation:");
    final String translation = trimSemicolon(scanner.nextLine());
    vtService.addNewEntry(original, translation);
  }

  private String trimSemicolon(String text) {
    return text.replace(";", ".");
  }

  private void close() {
    try {
      vtService.saveEntries();
      consoleOutputWriter.println("All internal data saved.");
    } catch (IOException e) {
      consoleOutputWriter.println("ERROR saving data.");
    }
    consoleOutputWriter.println("See you soon!");
  }

  private void printMenu() {
    consoleOutputWriter.println("Choose an option:");
    consoleOutputWriter.println("0 - Add new sentence");
    consoleOutputWriter.println("1 - Practice");
    consoleOutputWriter.println("2 - Exit the application");
  }

  private int chooseOption() {
    int option;
    try {
      option = scanner.nextInt();
    } catch (InputMismatchException e) {
      option = UNDEFINED;
    } finally {
      scanner.nextLine();
    }
    if (option > UNDEFINED && option <= CLOSE_APP) {
      return option;
    } else {
      return UNDEFINED;
    }
  }
}
