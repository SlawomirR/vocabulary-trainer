package pro.ruloff.vt.cryptography;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_SINGLETON)
@Profile("dev")
public class NoCipherService implements EncryptionService {

  @Override
  public String encrypt(String text) {
    return text;
  }

  @Override
  public String decrypt(String cipher) {
    return cipher;
  }
}
