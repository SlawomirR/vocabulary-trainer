package pro.ruloff.vt.cryptography;

public interface EncryptionService {

  String encrypt(String text);

  String decrypt(String cipher);
}
