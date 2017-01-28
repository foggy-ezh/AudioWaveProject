package com.audiowave.tverdakhleb.entity;

import java.util.List;

public class Singer extends Entity {
    private String name;
    private List<Album> albums;

    public Singer(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Singer singer = (Singer) o;

        if (name != null ? !name.equals(singer.name) : singer.name != null) return false;
        return albums != null ? albums.equals(singer.albums) : singer.albums == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (albums != null ? albums.hashCode() : 0);
        return result;
    }
}

