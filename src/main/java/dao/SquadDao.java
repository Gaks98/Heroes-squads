package dao;

import models.Squad;
import models.Hero;
import java.util.List;

public interface SquadDao {

        //LIST
        List<Squad> getAll();

        //CREATE
        void add (Squad category);

        //READ
        Squad findById(int id);

        //UPDATE
        void update(int id, String name);

        //DELETE
        void deleteById(int id);
        void clearAllSquads();

}
