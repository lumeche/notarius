package com.notarius.urlshortener.persistance;

import java.util.Optional;

public interface UrlStorage {
    boolean storeHashedUrl(String url, String hash) throws PersistanceException;
    Optional<String> retriveUrlFromHash(String hash) throws PersistanceException;
}
