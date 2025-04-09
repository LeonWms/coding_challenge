package com.example.deesee.service;

import com.example.deesee.service.encryption.EncryptionService;
import com.example.deesee.service.encryption.impl.EncryptionServiceImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncryptionServiceTest {

    @ParameterizedTest
    @CsvSource({
            "'Superman', 2, 'uwrgtocp'",
            "'xyz', 3, 'abc'",
            "'a b c d', 1, 'b c d e'",
            "'abc', 0, abc",
            "abc, -3, xyz",
            "'xyz', -1, 'wxy'"
    })
    void testEncrypt(String word, int encryptionKey, String expectedWord) {
        EncryptionService encryptionService = new EncryptionServiceImpl();

        String encryptedWord = encryptionService.encrypt(word, encryptionKey);
        assertEquals(expectedWord, encryptedWord);
    }


}
