package Item;

import entity.combatants.Combatant;

public class Revive extends Item{
    public static final String name = "Revive";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean use(Combatant target) {

        if(target.health <= 0){

            target.health = target.maxHealth/2;
            return true;
        }

        return false;
    }
}
