package pro.ruloff.vt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
class EntryRepository {

  private final List<Entry> entries;
  private final FileService fileService;
  private final Random random;

  EntryRepository(FileService fileService, Random random) {
    this.random = random;
    this.entries = new ArrayList<>();
    this.fileService = fileService;
  }

  List<Entry> getAll() {
    return entries;
  }

  Set<Entry> getRandomEntries(int number) {
    Set<Entry> randomEntries = new HashSet<>();
    while (randomEntries.size() < number
        && randomEntries.size() < entries.size()) {
      randomEntries.add(entries.get(random.nextInt(entries.size())));
    }
    return randomEntries;
  }

  void add(Entry entry) {
    entries.add(entry);
  }

  int size() {
    return entries.size();
  }

  boolean isEmpty() {
    return entries.isEmpty();
  }

  void loadData() {
    try {
      this.entries.addAll(fileService.readAllFile());
    } catch (IOException exp) {
      log.error("Problem reading file.", exp);
    }
  }
}
