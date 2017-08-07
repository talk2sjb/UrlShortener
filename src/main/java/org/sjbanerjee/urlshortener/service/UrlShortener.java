package org.sjbanerjee.urlshortener.service;

import org.sjbanerjee.urlshortener.dao.RecordDao;
import org.sjbanerjee.urlshortener.exception.BadRequestException;
import org.sjbanerjee.urlshortener.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Component
public class UrlShortener {

    //TODO: logger implementation
    //TODO: unit tests


    @Autowired
    private RecordRepository repository;

    private static final String base_url = "localhost:8080/sh/";

    private static final int base_bit = 62;

    private static final int length = 5;

    private static final String[] elements = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z"
    };

    @Transactional
    @Cacheable(cacheNames="alias", key="#urltoshorten")
    public String shorten(String urltoshorten) {

        //Validate the URL
        URL url = null;
        try {
            url = new URL(urltoshorten);
        } catch (MalformedURLException e) {
            throw new BadRequestException("Not a valid URL!!");
        }

        Record record = new Record();
        record.setCreationDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 5);
        record.setExpiryDate(calendar.getTime());
        record.setUrl(urltoshorten);

        record = repository.save(record);

        //Get the last record index
        Long index = record.getId();
        System.out.println("last index " + index);

        //Get the get the alias
        String encoded = getAlias(index, length);
        String alias = base_url.concat(encoded);

        record.setAlias(encoded);
        repository.save(record);
        System.out.println("Short : " + alias);

        return alias;
    }


    /**
     * Get alias from the passed index
     */
    public String getAlias(long index, int length) {

        if(index == 0){
            return elements[0];
        }

        int counter = 0;
        StringBuilder sb = new StringBuilder("");
        while (index != 0) {
            long i = index % base_bit;
            sb.append(elements[(int) i]);
            index = index / base_bit;
            counter++;
        }

        return sb.reverse().toString();
    }

    @Cacheable(cacheNames="url", key="#alias")
    public String decodeAlias(String alias){
//        Record record = repository.findByAlias(alias);
        Record record = repository.findRecord(alias, new Date());
        if(record == null) return "";
        return record.getUrl();
    }
}
