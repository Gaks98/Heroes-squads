import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oHeroDao;
import models.Hero;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) { //type “psvm + tab” to autocreate this
        staticFileLocation("/public");

        String connectionString = "jdbc:h2:~/heroes-squad.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oHeroDao heroDao = new Sql2oHeroDao(sql2o);

        //get: delete all heroes
        get("/heroes/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            heroDao.clearAllHeroes(); //change
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual hero
        get("/heroes/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToDelete = Integer.parseInt(req.params("id"));
            Hero deleteHero = heroDao.findById(idOfHeroToDelete); //change
            heroDao.deleteById(idOfHeroToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show all heroes
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Hero> heroes = heroDao.getAll(); //change
            model.put("heroes", heroes);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new hero form
        get("/heroes/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new hero form
        post("/heroes", (req, res) -> { //URL to make new hero on POST route
            Map<String, Object> model = new HashMap<>();
            String description = req.queryParams("description");
            Hero newHero = new Hero(description); //change
            heroDao.add(newHero);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual hero
        get("/heroes/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToFind = Integer.parseInt(req.params("id"));
            Hero foundTask = heroDao.findById(idOfHeroToFind); //change
            model.put("task", foundTask);
            return new ModelAndView(model, "hero-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a hero
        get("/heroes/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToEdit = Integer.parseInt(req.params("id"));
            Hero editHero = heroDao.findById(idOfHeroToEdit); //change
            model.put("editHero", editHero);
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a hero
        post("/heroes/:id", (req, res) -> { //URL to update hero on POST route
            Map<String, Object> model = new HashMap<>();
            String newContent = req.queryParams("description");
            int idOfHeroToEdit = Integer.parseInt(req.params("id"));
            Hero editHero = heroDao.findById(idOfHeroToEdit); //change
            heroDao.update(idOfHeroToEdit,newContent); //change
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

    }
}
