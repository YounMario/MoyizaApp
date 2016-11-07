package com.younchen.chat.entity;


public class Comment implements java.io.Serializable {

    // Fields

    private Long id;
    private Long articleId;
    private Long userId;
    private Long reUserId;
    private String content;
    private String time;

    // Constructors

    /**
     * default constructor
     */
    public Comment() {
    }

    /**
     * full constructor
     */
    public Comment(Long articleId, Long userId, Long reUserId, String content,
                   String time) {
        this.articleId = articleId;
        this.userId = userId;
        this.reUserId = reUserId;
        this.content = content;
        this.time = time;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return this.articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReUserId() {
        return this.reUserId;
    }

    public void setReUserId(Long reUserId) {
        this.reUserId = reUserId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}