package pro.ruloff.vt;

class Entry {

  private String original;
  private String translation;

  Entry(String original, String translation) {
    this.original = original;
    this.translation = translation;
  }

  String getOriginal() {
    return original;
  }

  void setOriginal(String original) {
    this.original = original;
  }

  String getTranslation() {
    return translation;
  }

  void setTranslation(String translation) {
    this.translation = translation;
  }

  @Override
  public String toString() {
    return original + ";" + translation;
  }
}
