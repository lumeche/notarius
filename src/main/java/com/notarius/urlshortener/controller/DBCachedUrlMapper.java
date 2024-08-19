package com.notarius.urlshortener.controller;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.notarius.urlshortener.persistance.UrlStorage;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Slf4j
public class DBCachedUrlMapper implements UrlMapper {
    @Autowired
    private HashingFunction hashingFunction;
    @Autowired
    private UrlStorage urlStorage;

    @Value("${shortener.cache.size}")
    private int cacheSize;

    private LoadingCache<String, Optional<String>> cache;

    @PostConstruct
    public void setUp() {
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(cacheSize)
                .build(new CacheLoader<>() {
                    @Override
                    public Optional<String> load(String hash)  {
                        var url= urlStorage.retriveUrlFromHash(hash);
                        log.info("URL {} retrieved from storage for {}",url, hash);
                        return url;
                    }
                });
    }

    @Override
    public Optional<MapperResponse> encodeUrl(String url) {
        try {
            var encodedUrl = hashingFunction.hash(url);
            urlStorage.storeHashedUrl(encodedUrl, url);
            this.cache.put(encodedUrl,Optional.of(url));
            return Optional.of(new MapperResponse(encodedUrl, true));
        } catch (NoSuchElementException e) {
            log.error("Error encoding  Url", e);
            return Optional.of(new MapperResponse(Strings.EMPTY, false));
        }
    }

    @Override
    public Optional<MapperResponse> decodeUrl(String hash) {
        MapperResponse r = this.cache.getUnchecked(hash)
                .map(url -> new MapperResponse(url, true))
                .orElseGet(() -> new MapperResponse("", false));

        return Optional.of(r);
    }
}
