package org.sjbanerjee.urlshortener.service;

import org.sjbanerjee.urlshortener.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by talk2sjb on 8/7/17.
 */
public interface RecordRepository extends JpaRepository<Record, Long> {

    public Record findByAlias(String alias);

    public List<Record> findRecordsByExpiryDateLessThan(Date expiry);

    @Query("select r from Record r where r.alias = ?1 and r.expiryDate > ?2")
    public Record findRecord(String alias, Date expiry);
}
