package jpabasic.sample;

import javax.persistence.Entity;

//@Entity
public class Album extends Item {

    private String artist;
    private String etc;

    public String getArtist() {
        return artist;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(final String etc) {
        this.etc = etc;
    }

}
