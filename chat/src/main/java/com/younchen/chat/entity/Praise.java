package com.younchen.chat.entity;

public class Praise implements java.io.Serializable {

    // Fields

    private Long id;
    private Long articleId;
    private Long userId;
    private String nickName;

    // Constructors

    /**
     * default constructor
     */
    public Praise() {
    }

    /**
     * full constructor
     */
    public Praise(Long articleId, Long userId, String nickName) {
        this.articleId = articleId;
        this.userId = userId;
        this.nickName = nickName;
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

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}