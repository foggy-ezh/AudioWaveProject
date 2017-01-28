package com.audiowave.tverdakhleb.entity;

import java.math.BigDecimal;
import java.util.List;

public class Audiotrack extends Entity {
    private String name;
    private String location;
    private BigDecimal cost;
    private boolean blocked;
    private long albumId;
    private String albumCoverURI;
    private Singer singer;
    private List<Singer> featuredSinger;

    public Audiotrack(long id, String name, String location, BigDecimal cost, boolean blocked, long albumId) {
        super(id);
        this.name = name;
        this.location = location;
        this.cost = cost;
        this.blocked = blocked;
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumCoverURI() {
        return albumCoverURI;
    }

    public void setAlbumCoverURI(String albumCoverURI) {
        this.albumCoverURI = albumCoverURI;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public void setFeaturedSinger(List<Singer> featuredSinger) {
        this.featuredSinger = featuredSinger;
    }

    public List<Singer> getFeaturedSinger() {
        return featuredSinger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Audiotrack that = (Audiotrack) o;

        if (blocked != that.blocked) return false;
        if (albumId != that.albumId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (albumCoverURI != null ? !albumCoverURI.equals(that.albumCoverURI) : that.albumCoverURI != null)
            return false;
        if (singer != null ? !singer.equals(that.singer) : that.singer != null) return false;
        return featuredSinger != null ? featuredSinger.equals(that.featuredSinger) : that.featuredSinger == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (blocked ? 1 : 0);
        result = 31 * result + (int) (albumId ^ (albumId >>> 32));
        result = 31 * result + (albumCoverURI != null ? albumCoverURI.hashCode() : 0);
        result = 31 * result + (singer != null ? singer.hashCode() : 0);
        result = 31 * result + (featuredSinger != null ? featuredSinger.hashCode() : 0);
        return result;
    }
}
