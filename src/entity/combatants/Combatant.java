package entity.combatants;

import entity.skills.Skill;

public class Combatant {

    String name;
    int level;
    int xp;
    int health;
    int strength;
    int defense;
    int magic;
    int magicDefense;
    int luck;

    Skill[] skills;

    Affinities affinities;
    Resistances resistances;
    GrowthRates growthRates;
    BaseStats baseStats;

    public Combatant(String name, int level, int xp, Affinities affinities, Resistances resistances, GrowthRates growthRates, BaseStats baseStats, Skill[] skills){
        this.name = name;
        this.level = level;
        this.xp = xp;

        this.affinities = affinities;
        this.resistances = resistances;
        this.growthRates = growthRates;
        this.baseStats = baseStats;

        this.skills = skills;

        calculateStartingStats();
    }

    public Combatant(String name, int level, int xp){
        this.name = name;
        this.level = level;
        this.xp = xp;

        this.affinities = new Affinities();
        this.resistances = new Resistances();
        this.growthRates = new GrowthRates();
        this.baseStats = new BaseStats();

        calculateStartingStats();

    }

    void calculateStartingStats(){
        this.health = this.baseStats.health + (this.level * 7);
        this.strength = this.baseStats.strength + (this.level * Math.round(this.level * ((float) growthRates.strength /100)));
        this.defense = this.baseStats.defense + (this.level * Math.round(this.level * ((float) growthRates.defense /100)));
        this.magic = this.baseStats.magic + (this.level * Math.round((this.level * ((float) growthRates.magic /100))));
        this.magicDefense = this.baseStats.magicDefense + Math.round((this.level * ((float) growthRates.magicDefense /100)));
        this.luck = this.baseStats.luck + (this.level * Math.round(this.level * ((float) growthRates.luck /100)));
    }

    public void showStats(){
        System.out.println("name: " + name);
        System.out.println("Level: " + level);
        System.out.println("health: " + health);
        System.out.println("magic: " + magic);

    }

}
