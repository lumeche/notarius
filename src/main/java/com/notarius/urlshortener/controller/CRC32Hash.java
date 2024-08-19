package com.notarius.urlshortener.controller;

import org.springframework.stereotype.Component;

import java.util.zip.CRC32;
import java.util.zip.Checksum;
@Component
public class CRC32Hash implements HashingFunction{
    @Override
    public String hash(String data) {
        byte[] bytes = data.getBytes();
        Checksum crc32 = new CRC32();

        crc32.update(bytes, 0, bytes.length);
        long checksum = crc32.getValue();
        return Long.toHexString(checksum);
    }
}
