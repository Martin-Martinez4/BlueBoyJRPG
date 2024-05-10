package entity.combatants;

import entity.skills.Skill;

import java.util.Random;

public class Combatant {

    public String name;
    public int level;
    public int xp;
    public int health;
    public int magicPower;
    public int strength;
    public int defense;
    public int magic;
    public int magicDefense;
    public int luck;

    public Skill[] skills;

    public Affinities affinities;
    public Resistances resistances;
    public GrowthRates growthRates;
    public BaseStats baseStats;

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
        this.magicPower = this.baseStats.magicPower + (this.level * 3);
        this.strength = this.baseStats.strength + (this.level * Math.round(this.level * ((float) growthRates.strength /100)));
        this.defense = this.baseStats.defense + (this.level * Math.round(this.level * ((float) growthRates.defense /100)));
        this.magic = this.baseStats.magic + (this.level * Math.round((this.level * ((float) growthRates.magic /100))));
        this.magicDefense = this.baseStats.magicDefense + Math.round((this.level * ((float) growthRates.magicDefense /100)));
        this.luck = this.baseStats.luck + (this.level * Math.round(this.level * ((float) growthRates.luck /100)));
    }

    boolean willAttackHit(){
        // this will be fleshed out later
        return true;
    }

    boolean willAttackCrit(){
        // this will be fleshed out later
        return false;
    }

    public int attackTarget(Skill skill, Combatant target){
        if(!this.willAttackHit()){
            // indicate the attack missed
            return 0;
        }
        float targetResistance = switch (skill.getElement()) {
            case fire -> target.resistances.getFire();
            case ice -> target.resistances.getIce();
            case force -> target.resistances.getForce();
            case lighting -> target.resistances.getLighting();
            case dark -> target.resistances.getDark();
            case light -> target.resistances.getLight();
            case physical -> target.resistances.getPhysical();
        };

        float attackerAffinities = switch (skill.getElement()) {
            case fire -> this.affinities.getFire();
            case ice -> this.affinities.getIce();
            case force -> this.affinities.getForce();
            case lighting -> this.affinities.getLighting();
            case dark -> this.affinities.getDark();
            case light -> this.affinities.getLight();
            case physical -> this.affinities.getPhysical();
        };

        if(targetResistance == Resistances.absorb){
            // handle enemy absorbs damage as health
            return -1*skill.getPower()/2;
        }
        else if(targetResistance == Resistances.nullify){
            // handle enemy takes 0 damage
            return 0;
        }
        else if(targetResistance == Resistances.reflect){
            // handle damage is dealt back to player as a neutral element attack of .8 * attack power
            // handle later, not implemented in game.
            return 0;
        }

        int damageAmount  = 0;

        // Math.floor((Math.pow(this.level,  .85 + (this.level/(target.level+this.level))) is used to closer correlate attack output to level difference
        if(skill.getType() == Skill.type.physical){

            damageAmount = (int) Math.floor((Math.pow(this.level, .95 + (this.level/(target.level+this.level))) * 1.25 + this.strength * (skill.getPower()) * attackerAffinities) / (targetResistance + target.defense * .25));
        }
        else if(skill.getType() == Skill.type.magic){
            damageAmount = (int) Math.floor((Math.pow(this.level, .95 + (this.level/(target.level+this.level))) * 1.25 + this.magic * (skill.getPower()) * attackerAffinities) / (targetResistance + target.magicDefense * .25));

        }

        // Target or player takes damage
        return damageAmount;
    }

    public void levelUp(){
            // Health and MP always increase
            // There are three stat increase chances
            // stats have an antagonistic relationship to each other, if one stat increases it disallows the other from increasing
            // To be implemented later, one user choice point in addition to the three
        /*
            Can be choosen as a player trait or something
            HP type: 5 HP for every 1 MP
            Balanced type: 2 HP for every 1 MP
            MP type: 1 HP for every 1 MP
        */
       /*
            Main player can always get 7 hp and 2 mp
       */
            int numberOfStatIncreases = 3;

            while(numberOfStatIncreases > 0){
                Random random = new Random();
                // Pick a number from 1 to 100
                int roll = random.nextInt(100)+1;
                numberOfStatIncreases--;

                // ugly code need to make a loop at the very least
                int lastRange = 0;
                if(roll <= this.growthRates.strength){
                    this.strength += 1;
                    continue;
                }

                lastRange += this.growthRates.strength;
                if(roll <= this.growthRates.defense + lastRange){
                    this.defense += 1;
                    continue;
                }

                lastRange += this.growthRates.defense;
                if(roll <= this.growthRates.magic + lastRange){
                    this.magic += 1;
                    continue;
                }

                lastRange += this.growthRates.magic;
                if(roll <= this.growthRates.magicDefense  + lastRange){
                    this.magicDefense += 1;
                    continue;
                }

                lastRange += this.growthRates.magicDefense;

                if(roll <= this.growthRates.luck + lastRange){
                    this.luck += 1;
                }
            }

            this.health += 10;
            this.level++;


    }

    public void showStats(){
        System.out.println("name: " + name);
        System.out.println("Level: " + level);
        System.out.println("health: " + health);
        System.out.println("magic: " + magic);

    }

}
