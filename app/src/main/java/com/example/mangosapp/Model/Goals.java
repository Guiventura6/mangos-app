package com.example.mangosapp.Model;

public class Goals {

    private String id;
    private String title;
    private String reason;
    private String created;
    private String deadline;
    private int amount;
    private int total_amount;

    public Goals() {}

    public Goals(String id, String title, String reason, String created, String deadline, int amount, int total_amount) {
        this.id = id;
        this.title = title;
        this.reason = reason;
        this.created = created;
        this.deadline = deadline;
        this.amount = amount;
        this.total_amount = total_amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }
}
