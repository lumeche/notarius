package com.notarius.urlshortener.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CRC32HashTest {
    private CRC32Hash testee;
    @BeforeEach
    public void setUp() throws Exception {
        testee=new CRC32Hash();
    }
    @Test
    public void testLenght(){
        var hash=testee.hash("/home/url/another/text");
        assertTrue(hash.length()<10);
    }

    @Test
    public void testRepreatability(){
        var hash1=testee.hash("/home/url/another/text");
        var hash2=testee.hash("/home/url/another/text");
        assertEquals(hash1,hash2);
    }
}