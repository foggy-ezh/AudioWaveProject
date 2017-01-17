package com.audiowave.tverdakhleb.entity;

public class Album extends Entity {

    private String albumName;
    private int releaseYear;
    private String coverURI;
    private Boolean blocked;

    public Album(long id, String albumName, int releaseYear, String coverURI, Boolean blocked) {
        super(id);
        this.albumName = albumName;
        this.releaseYear = releaseYear;
        this.coverURI = coverURI;
        this.blocked = blocked;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getCoverURI() {
        return coverURI;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setCoverURI(String coverURI) {
        this.coverURI = coverURI;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
