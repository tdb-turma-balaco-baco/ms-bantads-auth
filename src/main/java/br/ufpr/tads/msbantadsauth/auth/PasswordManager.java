package br.ufpr.tads.msbantadsauth.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordManager {
    private static final PasswordGenerator passwordGenerator = new PasswordGenerator();
    private static final int passwordLength = 16;

    private final PasswordEncoder passwordEncoder;

    public String generateRandomPassword() {
        List<CharacterRule> rules = List.of(
                new CharacterRule(EnglishCharacterData.Digit),
                new CharacterRule(EnglishCharacterData.Alphabetical),
                new CharacterRule(BantadsCharacterData.Special)
        );

        String rawPassword = passwordGenerator.generatePassword(passwordLength, rules);
        log.warn("[dev -> remove] rawPassword: {}", rawPassword);
        return this.encode(rawPassword);
    }

    public String encode(String rawPassword) {
        return this.passwordEncoder.encode(rawPassword);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    enum BantadsCharacterData implements CharacterData {
        Special("INSUFFICIENT_SPECIAL", "!#$%&'()*+,-./:;<=>?@[]^_`{|}~");

        private final String errorCode;
        private final String characters;
    }
}
