package com.notarius.urlshortener.persistance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class DBUrlStorage implements UrlStorage{
    @Autowired
    private HashEntityRepository hashEntityRepository;
    @Override
    public boolean storeHashedUrl(String hash, String url) throws PersistanceException {
        hashEntityRepository.save(new HashEntity(hash,url));
        return true;
    }

    @Override
    public Optional<String> retriveUrlFromHash(String hash) throws PersistanceException {
        var url= hashEntityRepository.findById(hash).map(HashEntity::getUrl);
        log.debug("url {} found over {} records",url,hashEntityRepository.count());//FIXME: Remove this
        return url;
    }
}
