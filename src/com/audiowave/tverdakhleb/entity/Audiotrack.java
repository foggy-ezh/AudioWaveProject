package com.audiowave.tverdakhleb.entity;

public class Audiotrack extends Entity {
    private String name;
    private String location;
    private double cost;
    private boolean blocked;
    private long albumId;
    private String albumCoverURI;
    private long singerId;
    private String singerName;

    public Audiotrack(long id, String name, String location, double cost, boolean blocked, long albumId) {
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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

    public long getSingerId() {
        return singerId;
    }

    public void setSingerId(long singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true; }
        if (o == null || getClass() != o.getClass()){ return false; }
        if (!super.equals(o)){ return false; }

        Audiotrack that = (Audiotrack) o;

        if (Double.compare(that.cost, cost) != 0){ return false; }
        if (blocked != that.blocked){ return false; }
        if (albumId != that.albumId){ return false; }
        if (singerId != that.singerId){ return false; }
        if (name != null ? !name.equals(that.name) : that.name != null){ return false; }
        if (location != null ? !location.equals(that.location) : that.location != null){ return false; }
        if (albumCoverURI != null ? !albumCoverURI.equals(that.albumCoverURI) : that.albumCoverURI != null){
            return false;}
        return singerName != null ? singerName.equals(that.singerName) : that.singerName == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (blocked ? 1 : 0);
        result = 31 * result + (int) (albumId ^ (albumId >>> 32));
        result = 31 * result + (albumCoverURI != null ? albumCoverURI.hashCode() : 0);
        result = 31 * result + (int) (singerId ^ (singerId >>> 32));
        result = 31 * result + (singerName != null ? singerName.hashCode() : 0);
        return result;
    }
}
