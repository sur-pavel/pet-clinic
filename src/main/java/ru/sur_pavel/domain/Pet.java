package ru.sur_pavel.domain;

import javax.persistence.*;

public class Pet {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String nick;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    public enum Status {

        NEW,

        PUBLISHED,

        EXPIRED

    }

    public Pet() {

    }

    public Pet(String nick, Client client, Status status) {
        this.nick = nick;
        this.client = client;
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
