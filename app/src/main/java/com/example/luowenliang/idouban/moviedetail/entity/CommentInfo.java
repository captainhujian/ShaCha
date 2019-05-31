package com.example.luowenliang.idouban.moviedetail.entity;

public class CommentInfo {
    private String commenterPisture;
    private String commenterName;
    private double commenterRating;
    private String commentTime;
    private String comment;
    private int usefulCount;

    public CommentInfo(String commenterPisture, String commenterName, double commenterRating, String commentTime, String comment, int usefulCount) {
        this.commenterPisture = commenterPisture;
        this.commenterName = commenterName;
        this.commenterRating = commenterRating;
        this.commentTime = commentTime;
        this.comment = comment;
        this.usefulCount = usefulCount;
    }

    public String getCommenterPisture() {
        return commenterPisture;
    }

    public void setCommenterPisture(String commenterPisture) {
        this.commenterPisture = commenterPisture;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public double getCommenterRating() {
        return commenterRating;
    }

    public void setCommenterRating(double commenterRating) {
        this.commenterRating = commenterRating;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUsefulCount() {
        return usefulCount;
    }

    public void setUsefulCount(int usefulCount) {
        this.usefulCount = usefulCount;
    }
}
