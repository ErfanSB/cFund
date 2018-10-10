package com.arena.maraton;

public class ModelSarmaye {

    String image;
    String name;
    String fav;
    String like;
    String comment;
    String id;


    public ModelSarmaye(String id,String image, String name, String fav, String like, String comment) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.fav = fav;
        this.like = like;
        this.comment = comment;
    }

public String getId() {
    return id;
}
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
