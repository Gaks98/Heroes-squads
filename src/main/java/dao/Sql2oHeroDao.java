package dao;

import models.Hero;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oHeroDao implements HeroDao {
   private final Sql2o sql2o;

    public Sql2oHeroDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Hero hero){
        String sql = "INSERT INTO heroes (description) VALUES (:description)";

        try (Connection con = sql2o.open()){
    //createQuery() is used to create dynamic queries, which are queries defined directly within an application’s business logic:
    //on this connection, we pass in our sql statement
    //use bind(hero) to map our argument onto the query so we can use information from it.
    //executeUpdate() runs the query
    //When the update has finished, meaning a new row has been written into the database,
    // we retrieve the key (or row number) from that row with
    // .getKey(), and set that as the id for our object with task.setId(id);.This ensures no two objects ever have the same id!
    //getKey()  //int id (this one below) is now the row number (row “key”) of db.
            int id = (int) con.createQuery(sql, true).bind(hero).executeUpdate().getKey();
            //update object to set id now from database
            hero.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Hero> getAll(){
        try (Connection con = sql2o.open()){
            //executeAndFetch() fetches a list of all heroes from the Hero class
            return con.createQuery("SELECT * FROM heroes").executeAndFetch(Hero.class);
        }
    }

    @Override
    public Hero findById(int id) {
        try(Connection con = sql2o.open()){
            //addParameter() takes key/value pair, key must match below(i think from the sql statement then the value is
            // findById argument id)
            return con.createQuery("SELECT * FROM tasks WHERE id = :id").addParameter("id", id).executeAndFetchFirst(Hero.class); //fetch an individual item
        }
    }
}
