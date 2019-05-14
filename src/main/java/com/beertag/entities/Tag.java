package com.beertag.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "tag_name")
    private String tagName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private List<Beer> beers = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Beer> getBeers() {
        return beers;
    }

    public void setBeers(List<Beer> beers) {
        this.beers = beers;
    }

//    @Override
//    public boolean equals(Object obj) {
//        return obj.equals(this.tagName);
//    }

//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        }
//        if (!(o instanceof Tag)) {
//            return false;
//        }
//        Tag t = (Tag)o;
//        return t.tagName.equals(tagName);
//    }
}
