package Item;

import entity.combatants.Combatant;

public class SmallPotion extends Item{

    public static final String name = "Small Potion";
    int healAmount = 15;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean use(Combatant target) {

        if(target.health < target.maxHealth){

            target.health += healAmount;
            return true;
        }

        return false;
    }
}
