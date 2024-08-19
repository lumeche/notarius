package com.notarius.urlshortener.persistance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class DBUrlStorage implements UrlStorage {
    @Autowired
    private HashEntityRepository hashEntityRepository;

    @Override
    public void storeHashedUrl(String hash, String url)  {
        hashEntityRepository.save(new HashEntity(hash, url));
    }

    @Override
    public Optional<String> retriveUrlFromHash(String hash)  {
        var url = hashEntityRepository.findById(hash).map(HashEntity::getUrl);
        log.debug("url {} found over {} records", url, hashEntityRepository.count());//FIXME: Remove this
        return url;
    }
}
