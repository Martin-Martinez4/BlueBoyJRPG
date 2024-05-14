package entity.combatants;

import entity.skills.Skill;
import entity.skills.fire.Ember;

public class BlueBoyMage extends Combatant{
//    Skill[] basicSkills =  new Skill[]{new Skill(Skill.type.physical, Skill.element.physical, 10)};
    public BlueBoyMage(){
        super(
                "Blue Mage",
                1,
                0,
                new Affinities(1.2f, 1f, 1f, 1f, 1f, 1f, .8f),
                new Resistances(1.2f, 1f, 1f, 1f, 1f, 1f, .8f),
                new GrowthRates(),
                new BaseStats(),
                new Skill[]{new Ember()},
                250,
                ExpGrowthRate.MedSlow,
                100
        );

    }
}
