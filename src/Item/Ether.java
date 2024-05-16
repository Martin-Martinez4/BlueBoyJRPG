package Item;

import entity.combatants.Combatant;

public class Ether extends Item{
    public static final String name = "Ether";
    int restoreAmount = 15;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean use(Combatant target) {
        if(target.magicPower < target.maxMagicPower  && target.health > 0){

            if(target.maxMagicPower - target.magicPower <= restoreAmount){
                target.magicPower = target.maxMagicPower;

                return true;
            }else{

                target.magicPower += restoreAmount;
                return true;
            }

        }

        return false;
    }
}
