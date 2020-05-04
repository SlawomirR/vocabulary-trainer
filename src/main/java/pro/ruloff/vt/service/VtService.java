package pro.ruloff.vt.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.stereotype.Service;
import pro.ruloff.vt.model.Entry;
import pro.ruloff.vt.repository.VtRepository;

@Service
public class VtService {

  private final Random random;
  private final VtRepository vtRepository;

  VtService(VtRepository vtRepository, Random random) {
    this.vtRepository = vtRepository;
    this.random = random;
  }

  public Set<Entry> getRandomEntries(int number) {
    Set<Entry> randomEntries = new HashSet<>();
    List<Entry> entries = vtRepository.getAll();
    while (randomEntries.size() < number
        && randomEntries.size() < entries.size()) {
      randomEntries.add(entries.get(random.nextInt(entries.size())));
    }
    return randomEntries;
  }

  public void saveEntries() throws IOException {
    vtRepository.saveEntries();
  }

  public void addNewEntry(String original, String translation) {
    vtRepository.add(original, translation);
  }

  public List<Entry> loadData() {
    return vtRepository.loadAllData();
  }
}
