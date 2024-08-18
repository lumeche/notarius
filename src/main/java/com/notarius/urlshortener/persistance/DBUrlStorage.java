package com.notarius.urlshortener.persistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DBUrlStorage implements UrlStorage{
    @Autowired
    private HashEntityRepository hashEntityRepository;
    @Override
    public boolean storeHashedUrl(String url, String hash) throws PersistanceException {
        hashEntityRepository.save(new HashEntity(hash,url));
        return false;
    }

    @Override
    public Optional<String> retriveUrlFromHash(String hash) throws PersistanceException {
        return hashEntityRepository.findById(hash).map(HashEntity::getUrl);
    }
}
