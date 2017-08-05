package org.sjbanerjee.urlshortener.dao;

import org.sjbanerjee.urlshortener.model.Record;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class RecordDao {

    @PersistenceContext
    private EntityManager em;

    public void persist(Record record) {

        em.persist(record);
    }

    public long getLastRecordIndex() {
        return em.createQuery("SELECT id FROM Record").getResultList().size();
    }

}
