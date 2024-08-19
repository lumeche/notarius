package com.notarius.urlshortener.rest;

import com.notarius.urlshortener.controller.MapperResponse;
import com.notarius.urlshortener.controller.UrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebHandler {
    @Autowired
    private  UrlMapper mapper;
    @GetMapping("/encodeUrl")
    public ResponseEntity<String> encodeUrl(@RequestParam String url){
        return mapper.encodeUrl(url)
                .map(WebHandler::buildResponse)
                .orElseGet(()->ResponseEntity.internalServerError().build());
    }


    @GetMapping("decodeUrl")
    public ResponseEntity<String> decodeUrl(@RequestParam String url){
        return mapper.decodeUrl(url)
                .map(WebHandler::buildResponse)
                .orElseGet(()->ResponseEntity.internalServerError().build());
    }

    private static ResponseEntity<String> buildResponse(MapperResponse r) {
        return ResponseEntity
                .status(r.success() ? 200 : 422)
                .body(r.result());
    }
}
