package dao;

import models.Hero;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oHeroDao implements HeroDao {
   private final Sql2o sql2o;

    public Sql2oHeroDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Hero hero){
        String sql = "INSERT INTO heroes (description) VALUES (:description)";

        try (Connection con = sql2o.open()){
            
        }
    }
}
