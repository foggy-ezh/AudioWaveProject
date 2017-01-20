package com.audiowave.tverdakhleb.entity;

public class Album extends Entity {

    private String albumName;
    private int releaseYear;
    private String coverURI;
    private Boolean blocked;
    private long singerId;
    private String singerName;

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
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        if (!super.equals(o)){ return false;}

        Album album = (Album) o;

        if (releaseYear != album.releaseYear){ return false;}
        if (albumName != null ? !albumName.equals(album.albumName) : album.albumName != null){ return false;}
        if (coverURI != null ? !coverURI.equals(album.coverURI) : album.coverURI != null){ return false;}
        if (singerId != album.singerId){ return false;}
        if (blocked != null ? !blocked.equals(album.blocked) : album.blocked != null){ return false;}
        return singerName != null ? singerName.equals(album.singerName) : album.singerName == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (albumName != null ? albumName.hashCode() : 0);
        result = 31 * result + releaseYear;
        result = 31 * result + (coverURI != null ? coverURI.hashCode() : 0);
        result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
        result = 31 * result + (int) (singerId ^ (singerId >>> 32));
        result = 31 * result + (singerName != null ? singerName.hashCode() : 0);
        return result;
    }
}
