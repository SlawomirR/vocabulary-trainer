package pro.ruloff.vt;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

class TrainerController {

  private static final int UNDEFINED = -1;
  private static final int ADD_ENTRY = 0;
  private static final int TEST = 1;
  private static final int CLOSE_APP = 2;

  private EntryRepository entryRepository = new EntryRepository();
  private FileService fileService = new FileService();
  private Scanner scanner = new Scanner(System.in);

  void mainLoop() {
    System.out.println("Welcome in Vocabulary Trainer Application.");
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
        test();
        break;
      case CLOSE_APP:
        close();
        break;
      default:
        System.out.println("Unknown option");
    }
  }

  private void test() {
    if (entryRepository.isEmpty()) {
      System.out.println("Please, add at least one word to the database.");
      return;
    }
    final int testSize = entryRepository.size() > 10 ? 10 : entryRepository.size();
    Set<Entry> randomEntries = entryRepository.getRandomEntries(testSize);
    int score = 0;
    for (Entry entry : randomEntries) {
      System.out.printf("Enter translation for: \"%s\"%n", entry.getOriginal());
      String translation = scanner.nextLine();
      if (entry.getTranslation().equalsIgnoreCase(translation)) {
        System.out.println("Correct answer");
        score++;
      } else {
        System.out.println("Wrong answer - " + entry.getTranslation());
      }
    }
    System.out.printf("Your score: %d/%d%n", score, testSize);
  }

  private void addEntry() {
    System.out.println("Give an original sentence:");
    String original = scanner.nextLine();
    System.out.println("Give an expected translation:");
    String translation = scanner.nextLine();
    Entry entry = new Entry(original, translation);
    entryRepository.add(entry);
  }

  private void close() {
    try {
      fileService.saveEntries(entryRepository.getAll());
      System.out.println("All internal data saved.");
    } catch (IOException e) {
      System.out.println("ERROR saving data.");
    }
    System.out.println("See you soon!");
  }

  private void printMenu() {
    System.out.println("Choose an option:");
    System.out.println("0 - Add new sentence");
//    System.out.println("Show sentences to practice today");
    System.out.println("1 - Practice");
    System.out.println("2 - Exit the application");
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