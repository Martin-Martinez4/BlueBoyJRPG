package entity.combatants;

import entity.skills.Skill;
import entity.skills.dark.Shade;
import entity.skills.fire.Ember;
import entity.skills.ice.Frost;
import entity.skills.physical.Smack;

public class JackFrost extends Combatant{

    public JackFrost(){
        super("Jack Frost", 1, 0, 200, ExpGrowthRate.MedFast, 25);
        this.skills = new Skill[]{new Frost(), new Shade(), new Smack()};

    }
}
