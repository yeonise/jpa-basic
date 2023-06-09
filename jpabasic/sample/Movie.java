package jpabasic.sample;

import javax.persistence.Entity;

//@Entity
public class Movie extends Item {

    private String director;
    private String actor;

    public String getDirector() {
        return director;
    }

    public void setDirector(final String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(final String actor) {
        this.actor = actor;
    }

}
