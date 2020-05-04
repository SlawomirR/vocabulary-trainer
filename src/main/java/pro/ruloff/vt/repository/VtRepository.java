package pro.ruloff.vt.repository;

import java.io.IOException;
import java.util.List;
import pro.ruloff.vt.model.Entry;

public interface VtRepository {

  List<Entry> loadAllData();

  void saveEntries() throws IOException;

  void add(String original, String translation);

  List<Entry> getAll();
}
