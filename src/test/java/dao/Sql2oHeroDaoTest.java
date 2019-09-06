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
        heroDao.add(hero);
        assertNotEquals(originalId,hero.getId());
    }
}
