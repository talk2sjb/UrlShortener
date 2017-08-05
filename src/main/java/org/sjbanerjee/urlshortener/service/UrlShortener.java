package org.sjbanerjee.urlshortener.service;

import org.sjbanerjee.urlshortener.dao.RecordDao;
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

    private static final String base_url = "https://sjbanerjee.com/";

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
    public Record shorten(String urltoshorten) {

        //Validate the URL
        URL url = null;
        try {
            url = new URL(urltoshorten);
        } catch (MalformedURLException e) {
            System.out.println("Not a valid URL!!");
            return null;
        }

        //Get the last record index
        Long index = dao.getLastRecordIndex();
        System.out.println("last index " + index);

        //Get the get the alias
        String alias = base_url.concat(getAlias(index, length));

        //Create a record and persist
        Record record = new Record(index + 1, url.toString(), alias);
        addRecord(record);
        dao.persist(record);
        System.out.println("Short : " + alias);

        return record;
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
}
