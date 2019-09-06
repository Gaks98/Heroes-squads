package dao;

import models.Hero;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2oHeroDaoTest {
    private Sql2oHeroDao heroDao;
    private Connection conn;

    @Before
    public void setup() throws Exception{
  //JDBC TellS H2 to write the test database into memory, using the sql file we just created, which preps the database for us.
        String connectionString ="jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"","");
        heroDao = new Sql2oHeroDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception{
        conn.close();
    }

    @Test
    public void AddingHeroSuccessfullySetsHeroId() throws Exception {
        Hero hero = new Hero("Hulk");
        int originalId = hero.getId();
    //if you get an error it'scoz add() method is from the interface and has not been implemented in the Sql2oDao class
    // and also that's why we have to pass our objects through it so as to be found and saved
        heroDao.add(hero);
        assertNotEquals(originalId,hero.getId());
    }

    @Test
    public void ExistingHeroesCanBeFoundById() throws Exception{
        Hero hero = new Hero("Hulk");
        heroDao.add(hero);
        //coz findById() method is from the interface and has not been implemented yet that's why there is an error.
        Hero foundHero = heroDao.findById(hero.getId());
        assertEquals(hero,foundHero);
    }
    
    @Test
    public void GetAllRetrievesAllHeroes_2() throws Exception{
        Hero hero1 = new Hero("Hulk");
        Hero hero2 = new Hero("superman");
        heroDao.add(hero1);
        heroDao.add(hero2);
        assertEquals(2,heroDao.getAll().size());
    }

    @Test
    public void NoHeroesAreFoundIfThereIsNone_0() throws Exception {
        assertEquals(0,heroDao.getAll().size());
    }

    @Test
    public void update_changesHeroesDetail() throws Exception{
        String initialDescription = "Hulk";
        Hero hero = new Hero(initialDescription);
        heroDao.add(hero);
        heroDao.update(hero.getId(),"wonder woman");
        Hero updatedHero = heroDao.findById(hero.getId());
        assertNotEquals(initialDescription,updatedHero.getDescription());
    }

    @Test
    public void deleteById_deletesCorrectHero_0() throws Exception {
        Hero hero = new Hero("Hulk");
        heroDao.add(hero);
        heroDao.deleteById(hero.getId());
        assertEquals(0,heroDao.getAll().size());
    }

    @Test
    public void clearAll_clearsAll() {
        Hero hero = new Hero("Hulk");
        Hero otherHero = new Hero("wonder woman");
        heroDao.add(hero);
        heroDao.add(otherHero);
        //will hold the number of heroes before they are all cleared
        int daoSize = heroDao.getAll().size();
        heroDao.clearAllHeroes();
        //daoSize is still equal to 2 while heroDao.getAll().size() is equal to zero
        //daoSize is not deleted after the clearAllHeroes() because it's not part of it.
        assertTrue(daoSize > 0 && daoSize > heroDao.getAll().size());
    }
}
