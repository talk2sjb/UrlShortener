package org.sjbanerjee.urlshortener.service;

import org.sjbanerjee.urlshortener.dao.RecordDao;
import org.sjbanerjee.urlshortener.exception.BadRequestException;
import org.sjbanerjee.urlshortener.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Component
public class UrlShortener {

    //TODO: logger implementation
    //TODO: unit tests

    @Autowired
    RecordDao dao;

    //I know this beats the purpose of a short url.. duh
    private static final String base_url = "https://sjbanerjee.com/sh/";

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
//    public Record shorten(@Value("${url}") String urltoshorten){
    public String shorten(String urltoshorten) {

        //Validate the URL
        URL url = null;
        try {
            url = new URL(urltoshorten);
        } catch (MalformedURLException e) {
            throw new BadRequestException("Not a valid URL!!");
        }

        //Get the last record index
        Long index = dao.getLastRecordIndex();
        System.out.println("last index " + index);

        //Get the get the alias
        String encoded = getAlias(index, length);
        String alias = base_url.concat(encoded);

        //Create a record and persist
        //TODO: Do not persist duplicate records
        Record record = new Record(index + 1, url.toString(), encoded);
        addRecord(record);
        dao.persist(record);
        System.out.println("Short : " + alias);

        return alias;
    }

    /**
     * Persist the record. A transactional call
     */
    @Transactional
    private void addRecord(Record record) {
        dao.persist(record);
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

    public String decodeAlias(String alias){
        return dao.getUrl(alias);
    }
}
