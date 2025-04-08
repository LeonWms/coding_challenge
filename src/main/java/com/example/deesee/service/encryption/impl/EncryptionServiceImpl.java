package com.example.deesee.service.encryption.impl;

import com.example.deesee.service.encryption.EncryptionService;
import org.springframework.stereotype.Service;


@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encrypt(String word, int key) {
        char[] charArray = new char[word.length()];

        for (int i = 0; i < word.length(); i++) {
            char c = word.toLowerCase().charAt(i);

            if(!Character.isWhitespace(c)) c+=key;
            charArray[i] = c;
        }

        String encryptedWord = new String(charArray);
        return encryptedWord;
    }
}
