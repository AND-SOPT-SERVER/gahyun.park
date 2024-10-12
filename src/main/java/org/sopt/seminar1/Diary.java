package org.sopt.seminar1;

public class Diary {
    private final Long id;
    private String body;

    private Boolean deleted;

    public Diary(final Long id, final String body, final Boolean deleted) {
        this.id = id;
        this.body = body;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(final Boolean deleted) {
        this.deleted = deleted;
    }

    public void setBody(final String body) {
        this.body = body;
    }

}
