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
    public void HeroInstantiatesCorrectly_true(){
        Hero hero =setupNewHero();
        assertEquals("Hulk",hero.getDescription());
    }

    @Test
    public void IsAssignedPropertyIsFalseAfterInstantiation(){
        Hero hero = setupNewHero();
        assertEquals(false,hero.isAssigned());
    }


    //HELPER METHOD
    public Hero setupNewHero(){
        return new Hero("Hulk",1);
    }
}