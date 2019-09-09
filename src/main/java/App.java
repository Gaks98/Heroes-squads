import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oHeroDao;
import dao.Sql2oSquadDao;
import models.Hero;
import models.Squad;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) { //type “psvm + tab” to autocreate this

        ProcessBuilder process = new ProcessBuilder();
        Integer port;

        // This tells our app that if Heroku sets a port for us, we need to use that port.
        // Otherwise, if they do not, continue using port 4567.

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);


        staticFileLocation("/public");

//        String connectionString = "jdbc:h2:~/heroes-squad.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        String connectionString = "postgresql://ec2-50-19-222-129.compute-1.amazonaws.com:5432/ddjuodi3mtjnms";
        Sql2o sql2o = new Sql2o(connectionString, "jhxvujddxjoapg", "15f4fd21b8e9e4b4e1e90012e08053af2bc104e82f0cac675d30205fdac80820");
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oHeroDao heroDao = new Sql2oHeroDao(sql2o);
        Sql2oSquadDao squadDao = new Sql2oSquadDao(sql2o);

        //get: show all heroes in all squads and show all squads
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
//            List<Squad> allSquads = squadDao.getAll();
//            model.put("squads", allSquads);
//            List<Hero> heroes = heroDao.getAll();
//            model.put("heroes", heroes);
            return new ModelAndView(model, "index2.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new squad
//        get("/squads/new", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Squad> squads = squadDao.getAll(); //refresh list of links for navbar
//            model.put("squads", squads);
//            return new ModelAndView(model, "squad-form.hbs"); //new layout
//        }, new HandlebarsTemplateEngine());
//
//        //post: process a form to create a new squad
//        post("/squads", (req, res) -> { //new
//            Map<String, Object> model = new HashMap<>();
//            String name = req.queryParams("name");
//            Squad newSquad = new Squad(name);
//            squadDao.add(newSquad);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());

        //get: delete all squads and all heroes
        get("/squads/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            squadDao.clearAllSquads();
            heroDao.clearAllHeroes();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all heroes
        get("/heroes/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            heroDao.clearAllHeroes(); //change
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific squad (and the heroes it contains)
        get("/categories/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSquadToFind = Integer.parseInt(req.params("id")); //new
            Squad foundSquad = squadDao.findById(idOfSquadToFind);
            model.put("squad", foundSquad);
            List<Hero> allHeroesBySquad = squadDao.getAllHeroesBySquad(idOfSquadToFind);
            model.put("heroes", allHeroesBySquad);
            model.put("squads", squadDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "squad-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a squad
        get("/squads/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editSquad", true);
            Squad squad = squadDao.findById(Integer.parseInt(req.params("id")));
            model.put("squad", squad);
            model.put("squads", squadDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "squad-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a squad
        post("/squads/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSquadToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newSquadName");
            squadDao.update(idOfSquadToEdit, newName);
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

//        //get: show all heroes
//        get("/", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Hero> heroes = heroDao.getAll(); //change
//            model.put("heroes", heroes);
//            return new ModelAndView(model, "index.hbs");
//        }, new HandlebarsTemplateEngine());

        //get: show new hero form
        get("/heroes/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> squads = squadDao.getAll();
            model.put("squads",squads);
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        //heroes: process new hero form
        post("/heroes", (req, res) -> { //URL to make new hero on POST route
            Map<String, Object> model = new HashMap<>();
            List<Squad> allSquads = squadDao.getAll();
            model.put("squads", allSquads);
            String description = req.queryParams("description");
            int squadId =Integer.parseInt(req.queryParams("squadId"));
            Hero newHero = new Hero(description,squadId); //change
            heroDao.add(newHero);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

//        //get: show an individual hero
//        get("/heroes/:id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfHeroToFind = Integer.parseInt(req.params("id"));
//            Hero foundHero = heroDao.findById(idOfHeroToFind); //change
//            model.put("hero", foundHero);
//            return new ModelAndView(model, "hero-detail.hbs");
//        }, new HandlebarsTemplateEngine());


        //get: show an individual hero that is nested in a squad
        get("/squads/:squad_id/heroes/:hero_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToFind = Integer.parseInt(req.params("task_id")); //pull id - must match route segment
            Hero foundHero = heroDao.findById(idOfHeroToFind); //use it to find hero
            int idOfSquadToFind = Integer.parseInt(req.params("category_id"));
            Hero foundSquad = heroDao.findById(idOfSquadToFind);
            model.put("squad", foundSquad);
            model.put("hero", foundHero); //add it to model for template to display
            model.put("squads", squadDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "hero-detail.hbs"); //individual hero page.
        }, new HandlebarsTemplateEngine());

      //get: show a form to update a hero
        get("/heroes/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> allSquads = squadDao.getAll();
            model.put("squads", allSquads);
            Hero hero = heroDao.findById(Integer.parseInt(req.params("id")));
            model.put("hero", hero);
            model.put("editHero", true);
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        //hero: process a form to update a hero
        post("/heroes/:id", (req, res) -> { //URL to update hero on POST route
            Map<String, Object> model = new HashMap<>();
            int heroToEditId = Integer.parseInt(req.params("id"));
            String newContent = req.queryParams("description");
            int newSquadId = Integer.parseInt(req.queryParams("squadId"));
            heroDao.update(heroToEditId, newContent, newSquadId);  // remember the hardcoded squadId we placed? See what we've done to/with it?
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/squad-form2", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "squad-form2.hbs");
        }, new HandlebarsTemplateEngine());

        get("/squadList", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String nameOfSquad = request.queryParams("nameOfSquad");
            String squadMission = request.queryParams("squadMission");
            int squadSize = Integer.parseInt(request.queryParams("squadSize"));
            model.put("nameOfSquad", nameOfSquad);
            model.put("squadMission", squadMission);
            model.put("squadSize", squadSize);
            return new ModelAndView(model, "squadList.hbs");
        }, new HandlebarsTemplateEngine());

        get("/hero-form2", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "hero-form2.hbs");
        }, new HandlebarsTemplateEngine());

        get("/heroes", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String nameOfHero = request.queryParams("nameOfHero");
            String squadType = request.queryParams("squadType");
            String heroStrength = request.queryParams("heroStrength");
            String heroWeakness = request.queryParams("heroWeakness");
            String heroAge = request.queryParams("heroAge");
            model.put("nameOfHero", nameOfHero);
            model.put("squadType", squadType);
            model.put("heroStrength", heroStrength);
            model.put("heroWeakness", heroWeakness);
            model.put("heroAge", heroAge);
            return new ModelAndView(model, "heroes.hbs");
        }, new HandlebarsTemplateEngine());



    }
}
