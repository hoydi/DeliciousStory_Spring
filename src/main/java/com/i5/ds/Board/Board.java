package com.i5.ds.Board;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ds_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Integer postId;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "POST_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date postDate;

    @Column(name = "VIEWS", nullable = false)
    private Integer views = 0; // 기본값 설정

    // 기본 생성자
    public Board() {
    	this.views = 0;
    }

    // 매개변수 생성자
    public Board(String userId, String title, String content, Date postDate) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.postDate = postDate;

    }

    // Getters and Setters
    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @PrePersist
    protected void onCreate() {
        if (this.postDate == null) {
            this.postDate = new Date();
        }
    }
}
