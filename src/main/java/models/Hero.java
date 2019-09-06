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

    public void setId(int id) {
        this.id = id;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public int getId() {
        return id;
    }

    //getter
    public boolean isAssigned() {
        return assigned;
    }

}
