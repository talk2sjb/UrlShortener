package org.sjbanerjee.urlshortener.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Record")
public class Record implements Serializable{

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "alias")
    private String alias;

    protected Record() {}

    public Record(Long id, String url, String alias) {
        this.id = id;
        this.url = url;
        this.alias = alias;

        System.out.println(this.toString() + " created");
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return String.format(
                "Record[id=%d, url='%s', alias='%s']",
                id, url, alias);
    }
}
