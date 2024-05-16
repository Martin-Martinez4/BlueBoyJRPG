package Item;

import entity.combatants.Combatant;
import main.GamePanel;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemManager {

    GamePanel gamePanel;
    public HashMap<String, Integer> itemAmount = new HashMap<String, Integer>();

    public ItemManager(GamePanel gamePanel){

        this.gamePanel = gamePanel;

        this.addItem(SmallPotion.name);
        this.addItem(SmallPotion.name);
        this.addItem(SmallPotion.name);
        this.addItem(SmallPotion.name);
        this.addItem(SmallPotion.name);
        this.addItem(SmallPotion.name);

        this.addItem(Ether.name);
        this.addItem(Ether.name);
        this.addItem(Ether.name);

        this.addItem(Revive.name);
        this.addItem(Revive.name);
        this.addItem(Revive.name);
    }

    public void addItem(String itemName){

        if(itemAmount.containsKey(itemName)) {
            itemAmount.compute(itemName, (k, oldItemAmount) -> oldItemAmount + 1);

        }else {
                itemAmount.put(itemName, 1);

        }

    }
    public void removeItem(String itemName){
        if(itemAmount.containsKey(itemName)){
            int oldItemAmount = itemAmount.get(itemName);
            if(oldItemAmount == 1){
                itemAmount.remove(itemName);
            }
            else {
                itemAmount.put(itemName, itemAmount.get(itemName) -1);

            }
        }

    }
    public Item getItem(String itemName){
        // Only have three items so a switch is fine
        return switch (itemName) {
            case SmallPotion.name -> new SmallPotion();
            case Ether.name -> new Ether();
            case Revive.name -> new Revive();
            default -> new Item();
        };

    }
    public boolean useItem(String itemName, Combatant target){
        Item itemToUse = getItem(itemName);

        // Will return true if the item was used
        boolean wasItemUsed = itemToUse.use(target);

        if(wasItemUsed){
            // taking away the item
            this.removeItem(itemName);
        }

        return wasItemUsed;

    }

    // Will implement later
    public void useItem(String itemName, ArrayList<Combatant> targetTeam){
        // items that affect more than one target
    }

    public HashMap<String, Integer> getItems(){
        return itemAmount;
    }


}
