package entity.combatants;

import entity.skills.Skill;
import entity.skills.physical.Smack;

public class Slime extends Combatant{

    public Slime(){
        super(
                "Slime",
                1,
                0,
                new Affinities(),
                new Resistances(.8f, .8f, .8f, .8f, .8f, .8f, 1.5f),
                new GrowthRates(),
                new BaseStats(),
                new Skill[]{new Smack()},
                35,
                ExpGrowthRate.MedFast,
                15
               );

    }
}
