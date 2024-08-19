package com.notarius.urlshortener.persistance;

import java.util.Optional;

public interface UrlStorage {
    void storeHashedUrl(String hash, String url) ;
    Optional<String> retriveUrlFromHash(String hash);
}
