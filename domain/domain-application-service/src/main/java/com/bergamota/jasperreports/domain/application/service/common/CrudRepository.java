package com.bergamota.jasperreports.domain.application.service.common;

import java.util.Optional;

public interface CrudRepository<T, ID> {

    T save(T obj);
    Optional<T> findById(ID id);
    void remove(ID id);

}
