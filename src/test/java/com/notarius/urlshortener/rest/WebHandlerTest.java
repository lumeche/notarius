package com.notarius.urlshortener.rest;

import com.notarius.urlshortener.controller.MapperResponse;
import com.notarius.urlshortener.controller.UrlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {WebHandler.class})
class WebHandlerTest {

    @MockitoBean
    private UrlMapper mapper;
    @Autowired
    private WebHandler testee;


    @Test
    void encodeUrlHappy() {
        //WHEN
        Mockito.when(mapper.encodeUrl(Mockito.anyString()))
                .thenReturn(Optional
                        .of(new MapperResponse("something",true)));
        //DO
        var r=testee.encodeUrl("also");
        //THEN
        assertEquals("something",r.getBody());
        assertTrue(r.getStatusCode().is2xxSuccessful());
    }

    @Test
    void encodeUrlUrlError() {
        //WHEN
        Mockito.when(mapper.encodeUrl(Mockito.anyString()))
                .thenReturn(Optional
                        .of(new MapperResponse("something",false)));
        //DO
        var r=testee.encodeUrl("also");
        //THEN
        assertEquals("something",r.getBody());
        assertTrue(r.getStatusCode().is4xxClientError());
    }

    @Test
    void encodeUrlInternalError() {
        //WHEN
        Mockito.when(mapper.encodeUrl(Mockito.anyString()))
                .thenReturn(Optional
                        .empty());
        //DO
        var r=testee.encodeUrl("also");
        //THEN
        assertTrue(r.getStatusCode().is5xxServerError());
    }
    @Test
    void decodeUrlHappy() {
        //WHEN
        Mockito.when(mapper.decodeUrl(Mockito.anyString()))
                .thenReturn(Optional
                        .of(new MapperResponse("something",true)));
        //DO
        var r=testee.decodeUrl("also");
        //THEN
        assertEquals("something",r.getBody());
        assertTrue(r.getStatusCode().is2xxSuccessful());
    }


    @Test
    void decodeUrlUserError() {
        //WHEN
        Mockito.when(mapper.decodeUrl(Mockito.anyString()))
                .thenReturn(Optional
                        .of(new MapperResponse("something",false)));
        //DO
        var r=testee.decodeUrl("also");
        //THEN
        assertEquals("something",r.getBody());
        assertTrue(r.getStatusCode().is4xxClientError());
    }

    @Test
    void decodeUrlInternalError() {
        //WHEN
        Mockito.when(mapper.decodeUrl(Mockito.anyString()))
                .thenReturn(Optional
                        .empty());
        //DO
        var r=testee.decodeUrl("also");
        //THEN
        assertTrue(r.getStatusCode().is5xxServerError());
    }



}