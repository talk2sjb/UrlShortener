package org.sjbanerjee.urlshortener.dao;

import org.sjbanerjee.urlshortener.exception.BadRequestException;
import org.sjbanerjee.urlshortener.model.Record;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class RecordDao {

    @PersistenceContext
    private EntityManager em;

    public void persist(Record record) {

        em.persist(record);
        em.flush();
    }
    public long getLastRecordIndex() {
        return em.createQuery("SELECT id FROM Record")
                .getResultList()
                .size();
    }

    public String getUrl(String alias) {
        if(alias.trim() == null){
            throw new BadRequestException("Invalid alias");
        }

        return em.createQuery("SELECT OBJECT(record) FROM Record record WHERE record.alias=:alias", Record.class)
                .setParameter("alias", alias)
                .getResultList()
                .get(0)
                .getUrl();
    }


}
