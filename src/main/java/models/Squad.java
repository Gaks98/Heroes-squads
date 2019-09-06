package models;

import java.util.Objects;

public class Squad {
        private String name;
        private int id;

    public String getName() {
        return name;
    }

    public Squad (String name){
            this.name = name;
        }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Squad squad = (Squad) o;
        return id == squad.id &&
                Objects.equals(name, squad.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
