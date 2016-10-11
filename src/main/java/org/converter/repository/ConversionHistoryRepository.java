package org.converter.repository;

import org.converter.domain.ConversionHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConversionHistoryRepository extends CrudRepository<ConversionHistory, Long> {

    List<ConversionHistory> findTop10ByUserIdOrderByTimestampDesc(Long userId);
}
