import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.Hero;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) { //type “psvm + tab” to autocreate this
        staticFileLocation("/public");

        //get: delete all heroes
        get("/heroes/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Hero.clearAllHeroes(); //change
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual hero
        get("/heroes/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToDelete = Integer.parseInt(req.params("id"));
            Hero deleteHero = Hero.findById(idOfHeroToDelete); //change
            deleteHero.deleteHero();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show all tasks
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Hero> heroes = Hero.getAll(); //change
            model.put("heroes", heroes);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new task form
        get("/heroes/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new task form
        post("/heroes", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String description = req.queryParams("description");
            Hero newHero = new Hero(description); //change
            heroDao.add(newHero);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual task
        get("/heroes/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToFind = Integer.parseInt(req.params("id"));
            Hero foundTask = Hero.findById(idOfHeroToFind); //change
            model.put("task", foundTask);
            return new ModelAndView(model, "hero-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a task
        get("/heroes/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToEdit = Integer.parseInt(req.params("id"));
            Hero editHero = Hero.findById(idOfHeroToEdit); //change
            model.put("editHero", editHero);
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a task
        post("/heroes/:id", (req, res) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            String newContent = req.queryParams("description");
            int idOfHeroToEdit = Integer.parseInt(req.params("id"));
            Hero editTask = Hero.findById(idOfHeroToEdit); //change
            editTask.update(newContent); //change
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}
