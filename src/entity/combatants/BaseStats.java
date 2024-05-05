package entity.combatants;

public class BaseStats {
    int health = 30;
    int strength = 7;
    int defense = 6;
    int magic = 7;
    int magicDefense = 6;
    int luck = 4;

    public BaseStats(){

    }

    public BaseStats(int health, int strength, int defense, int magic, int magicDefense, int luck){
        this.health = health;
        this.strength = strength;
        this.defense = defense;
        this.magic = magic;
        this.magicDefense = magicDefense;
        this.luck = luck;

    }
}