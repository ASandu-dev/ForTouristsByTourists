package com.school.fortouristsbytourists;

public class ReviewClass {
    private Integer ID;
   private String name, comment, date;

    public ReviewClass() {
    }

    public ReviewClass(Integer ID, String name, String comment, String date) {
        this.ID = ID;
        this.name = name;
        this.comment = comment;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
