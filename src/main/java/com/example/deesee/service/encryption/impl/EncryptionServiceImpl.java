package com.example.deesee.service.encryption.impl;

import com.example.deesee.service.encryption.EncryptionService;
import org.springframework.stereotype.Service;


@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encrypt(String word, int key) {
        char[] charArray = new char[word.length()];

        // Loop through each character of the word
        for (int i = 0; i < word.length(); i++) {
            char c = word.toLowerCase().charAt(i);

            // Ignore whitespace, shift character
            if (Character.isWhitespace(c)) {
                charArray[i] = c;
            } else {
                charArray[i] = shiftChar(c, key);
            }

        }

        String encryptedWord = new String(charArray);
        return encryptedWord;
    }

    private char shiftChar(char c, int key) {

        boolean isNegative = key < 0;
        int steps = Math.abs(key);

        if(c < 'a' || c > 'z') return c;

        // Loop character through the alphabet n-steps
        for (int i = 0; i < steps; i++) {
            if (isNegative) {
                c--;
                if (c < 'a') c = 'z';
            } else {
                c++;
                if (c > 'z') c = 'a';
            }
        }

        return c;
    }
}
