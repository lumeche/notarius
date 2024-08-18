package com.notarius.urlshortener.persistance;

import org.springframework.data.repository.CrudRepository;

public interface HashEntityRepository extends CrudRepository<HashEntity,String> {
}
