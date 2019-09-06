package models;

import java.util.Objects;

public class Hero {
    private String description;
    private boolean assigned;
    private int id;
    private int squadId;

    public Hero(String description,int squadId){
        this.description = description;
        this.assigned =false;
        this.squadId = squadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return assigned == hero.assigned &&
                id == hero.id &&
                squadId == hero.squadId &&
                Objects.equals(description, hero.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, assigned, id, squadId);
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

    public void setSquadId(int squadId) {
        this.squadId = squadId;
    }

    public int getSquadId() {
        return squadId;
    }
}
