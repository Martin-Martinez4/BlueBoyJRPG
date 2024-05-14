package entity.combatants;

import entity.skills.Skill;
import entity.skills.dark.Shade;
import entity.skills.fire.Ember;
import entity.skills.ice.Frost;
import entity.skills.physical.Smack;

public class JackFrost extends Combatant{

    public JackFrost(){
        super(
                "Jack Frost",
                1,
                0,
                new Affinities(.8f, 1.2f, 1f, 1f, 1f, 1f, 1f),
                new Resistances(.8f, 1.2f, 1f, 1f, 1f, 1f, 1f),
                new GrowthRates(),
                new BaseStats(),
                new Skill[]{new Smack()},
                200,
                ExpGrowthRate.MedFast,
                25
        );
        this.skills = new Skill[]{new Frost(), new Shade(), new Smack()};

    }
}
