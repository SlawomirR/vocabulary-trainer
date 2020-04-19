package pro.ruloff.vt.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pro.ruloff.vt.model.Entry;

@Slf4j
@Repository
public class FileRepository implements VtRepository {

  private static final String FILE_NAME = "data.csv";

  private final Entry entry;
  private final List<Entry> entries;

  FileRepository(Entry entry) {
    this.entries = new ArrayList<>();
    this.entry = entry;
  }

  public List<Entry> getAll() {
    return entries;
  }

  public void add(String original, String translation) {
    entries.add(entry.createNewEntry(original, translation));
  }

  public void saveEntries() throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
      for (Entry singleEntry : entries) {
        writer.write(singleEntry.getSemicolonSeparatedFields());
        writer.newLine();
      }
    }
  }

  public List<Entry> loadAllData() {
    try {
      this.entries.addAll(readWholeFile());
    } catch (IOException exp) {
      log.error("Problem reading file.", exp);
    }
    return entries;
  }

  private List<Entry> readWholeFile() throws IOException {
    return Files.readAllLines(Paths.get(FILE_NAME))
        .stream()
        .map(entry::parseRow)
        .collect(Collectors.toList());
  }
}
