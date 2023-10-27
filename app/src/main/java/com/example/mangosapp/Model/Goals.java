package com.example.mangosapp.Model;

public class Goals {

    private String id;
    private String title;
    private String reason;
    private String created;
    private String deadline;
    private double current_amount;
    private double total_amount;

    public Goals() {
    }

    public Goals(String id, String title, String reason, String created, String deadline, double current_amount, double total_amount) {
        this.id = id;
        this.title = title;
        this.reason = reason;
        this.created = created;
        this.deadline = deadline;
        this.current_amount = current_amount;
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

    public double getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(double current_amount) {
        this.current_amount = current_amount;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}