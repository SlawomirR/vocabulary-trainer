package pro.ruloff.vt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
class FileService {

  private final Entry entry;
  private String fileName = "data.csv";

  FileService(Entry entry) {
    this.entry = entry;
  }

  List<Entry> readAllFile() throws IOException {
    return Files.readAllLines(Paths.get(fileName))
        .stream()
        .map(entry::parseRow)
        .collect(Collectors.toList());
  }

  void saveEntries(List<Entry> entries) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      for (Entry singleEntry : entries) {
        writer.write(singleEntry.getSemicolonSeparatedFields());
        writer.newLine();
      }
    }
  }
}
