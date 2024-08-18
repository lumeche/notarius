package com.notarius.urlshortener.controller;

import java.util.Optional;

public interface UrlMapper {
    Optional<MapperResponse> encodeUrl(String url);

    Optional<MapperResponse> decodeUrl(String url);
}
