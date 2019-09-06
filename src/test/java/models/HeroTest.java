package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeroTest {

    @Test
    public void NewHeroObjectGetsCorrectlyCreated_true(){
        Hero hero = setupNewHero();
        assertEquals(true,hero instanceof Hero);
    }

    @Test
    public void




    //HELPER METHOD
    public Hero setupNewHero(){
        return new Hero("Hulk");
    }
}