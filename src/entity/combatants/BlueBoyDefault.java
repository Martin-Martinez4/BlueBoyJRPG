package entity.combatants;

import entity.skills.Skill;
import entity.skills.physical.Smack;

public class BlueBoyDefault extends Combatant{
    public BlueBoyDefault(){
        super(
                "Blue Boy",
                1,
                0,
                new Affinities(),
                new Resistances(),
                new GrowthRates(),
                new BaseStats(),
                new Skill[]{new Smack()},
                250,
                ExpGrowthRate.MedFast,
                100

        );

    }
}
