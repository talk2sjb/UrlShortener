package org.sjbanerjee.urlshortener.model;

import javax.persistence.*;

@Entity
@Table(name = "Record")
public class Record {

    @Id
    private Long id;
    private String url;
    private String alias;

    protected Record() {}

    public Record(Long id, String url, String alias) {
        this.id = id;
        this.url = url;
        this.alias = alias;

        System.out.println(this.toString() + " created");
    }

    @Override
    public String toString() {
        return String.format(
                "Record[id=%d, url='%s', alias='%s']",
                id, url, alias);
    }
}
