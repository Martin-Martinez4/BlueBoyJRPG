package Item;

import entity.combatants.Combatant;

import java.util.ArrayList;

public class Item {

    public static String name = "Item";

    public boolean use(Combatant target){
        System.out.println("Use item");
        return true;
    }

    public boolean use(ArrayList<Combatant> teamTarget){
        System.out.println("Use item on a team");
        return true;
    }

    public String getName() {
        return name;
    }
}
