package entity.combatants;

public class GrowthRates {
    public int strength =  20;
    public int defense =  20;
    public int magic =  20;
    public int magicDefense =  20;
    public int luck = 20;

    public GrowthRates(){
        // Keep Base Growth Rates
    }

    public GrowthRates(int strength, int defense, int magic, int magicDefense, int luck ){
        this.strength = strength;
        this.defense = defense;
        this.magic = magic;
        this.magicDefense = magicDefense;
        this.luck = luck;
    }
}
