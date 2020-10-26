package me.mandaveiga.concretesolutions.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudService<T> {

    Optional<T> save(T body);

    List<T> findAll();

    Optional<T> findById(UUID id);

    Optional<T> update(T body);

    Optional<Boolean> deleteById(UUID id);

    Optional<Boolean> deleteAll();
}
