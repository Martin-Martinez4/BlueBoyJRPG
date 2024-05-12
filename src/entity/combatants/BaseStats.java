package entity.combatants;

public class BaseStats {
    int maxHealth = 30;
    int maxMagicPower = 15;
    int strength = 7;
    int defense = 6;
    int magic = 7;
    int magicDefense = 6;
    int luck = 4;

    public BaseStats(){

    }

    public BaseStats(int maxHealth, int maxMagicPower, int strength, int defense, int magic, int magicDefense, int luck){
        this.maxHealth = maxHealth;
        this.maxMagicPower = maxMagicPower;
        this.strength = strength;
        this.defense = defense;
        this.magic = magic;
        this.magicDefense = magicDefense;
        this.luck = luck;

    }
}
