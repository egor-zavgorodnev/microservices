package com.epam.mentoring.models;

import java.io.Serializable;

public class Instance implements Serializable {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
