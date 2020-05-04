package pro.ruloff.vt.cryptography;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_SINGLETON)
@Profile("prod")
public class CaesarCipherService implements EncryptionService {

  private final int letterShiftNumber;

  public CaesarCipherService(@Value("${LETTERS_SHIFT_NUMBER}") int letterShiftNumber) {
    this.letterShiftNumber = letterShiftNumber;
  }

  private int shift(int number) {
    return number + letterShiftNumber;
  }

  private int shiftBack(int number) {
    return number - letterShiftNumber;
  }

  @Override
  public String encrypt(String text) {
    return text.chars()
        .map(this::shift)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }

  @Override
  public String decrypt(String cipher) {
    return cipher.chars()
        .map(this::shiftBack)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }
}
