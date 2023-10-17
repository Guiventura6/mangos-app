package com.example.mangosapp.Model;

public class Goals {

    private String id;
    private String description;
    private String created;
    private String deadline;
    private int amount;

    public Goals() {}

    public Goals(String id, String description, String created, String deadline, int amount) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.deadline = deadline;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
