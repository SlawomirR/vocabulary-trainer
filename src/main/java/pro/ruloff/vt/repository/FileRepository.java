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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pro.ruloff.vt.cryptography.EncryptionService;
import pro.ruloff.vt.model.Entry;

@Slf4j
@Repository
public class FileRepository implements VtRepository {

  private final EncryptionService encryption;
  private final Entry entry;
  private final List<Entry> entries;
  private final String fileName;

  FileRepository(Entry entry, EncryptionService encryption, @Value("${DATA_FILE}") String fileName) {
    this.fileName = fileName;
    this.entries = new ArrayList<>();
    this.entry = entry;
    this.encryption = encryption;
  }

  public List<Entry> getAll() {
    return entries;
  }

  public void add(String original, String translation) {
    entries.add(entry.createNewEntry(original, translation));
  }

  public void saveEntries() throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      for (Entry singleEntry : entries) {
        final String ciphered = encryption.encrypt(singleEntry.getSemicolonSeparatedFields());
        writer.write(ciphered);
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
    return Files.readAllLines(Paths.get(fileName))
        .stream()
        .map(encryption::decrypt)
        .map(entry::parseRow)
        .collect(Collectors.toList());
  }
}
