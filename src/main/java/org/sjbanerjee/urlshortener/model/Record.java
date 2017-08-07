package org.sjbanerjee.urlshortener.model;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Record")
public class Record implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String url;

    @Column
    private String alias;

    @Column
    private Date creationDate;

    @Column
    private Date expiryDate;

    public Record() {}

     public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getAlias() {
        return alias;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return String.format(
                "Record[id=%d, url='%s', alias='%s']",
                id, url, alias);
    }
}
