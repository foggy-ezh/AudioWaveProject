package com.audiowave.tverdakhleb.entity;

public class AlbumComment extends Entity {
    private String comment;
    private String userLogin;
    private long albumId;

    public AlbumComment(long userId, String comment) {
        super(userId);
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AlbumComment albumComment = (AlbumComment) o;

        if (albumId != albumComment.albumId) return false;
        if (comment != null ? !comment.equals(albumComment.comment) : albumComment.comment != null) return false;
        return userLogin != null ? userLogin.equals(albumComment.userLogin) : albumComment.userLogin == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (userLogin != null ? userLogin.hashCode() : 0);
        result = 31 * result + (int) (albumId ^ (albumId >>> 32));
        return result;
    }
}
