package models;

public class Hero {
    private String description;
    private boolean assigned;
    private int id;

    public Hero(String description){
        this.description = description;
        this.assigned =false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    //getter
    public boolean isAssigned() {
        return assigned;
    }

}
