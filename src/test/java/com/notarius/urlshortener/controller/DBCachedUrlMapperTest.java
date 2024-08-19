package com.notarius.urlshortener.controller;

import com.notarius.urlshortener.persistance.PersistanceException;
import com.notarius.urlshortener.persistance.UrlStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {DBCachedUrlMapper.class})
class DBCachedUrlMapperTest {

    private static final Logger log = LoggerFactory.getLogger(DBCachedUrlMapperTest.class);
    @Autowired
    private DBCachedUrlMapper testee;
    @MockitoBean
    private HashingFunction hashingFunction;
    @MockitoBean
    private UrlStorage urlStorage;

    public static final String URL="1234";
    public static final String HASH="asdf";

    @BeforeEach
    void setUp() throws PersistanceException {
        when(hashingFunction.hash(URL)).thenReturn(HASH);
        when(urlStorage.storeHashedUrl(anyString(),anyString())).thenReturn(true);
    }

    @Test
    public void testHashUrl() throws PersistanceException {
        //DO
        log.info("Hashing function");
        var hash=testee.encodeUrl(URL);
        //THEN
        assertEquals(HASH,hash.get().result());
        assertTrue(hash.get().success());
        verify(urlStorage, times(1)).storeHashedUrl(HASH, URL);
    }

    @Test
    public void testRetrieveStoredUrl() throws PersistanceException {
        //WHEN
        when(urlStorage.retriveUrlFromHash(HASH)).thenReturn(Optional.of(URL));
        //DO
        var r=testee.decodeUrl(HASH);
        //THEN
        assertEquals(URL,r.get().result());
        assertTrue(r.get().success());
    }

    @Test
    public void testRetrieveNonHashedUrl(){
        //DO
        var r=testee.decodeUrl(HASH);
        //THEN
        assertFalse(r.get().success());
    }

}