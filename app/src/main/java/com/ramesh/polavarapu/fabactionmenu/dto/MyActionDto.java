package com.ramesh.polavarapu.fabactionmenu.dto;

import java.io.Serializable;

public class MyActionDto implements Serializable {

    private String sLabel;
    private int id;
    private int actionId;

    public String getLabel() {
        return sLabel;
    }

    public void setLabel(String sLabel) {
        this.sLabel = sLabel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }
}
