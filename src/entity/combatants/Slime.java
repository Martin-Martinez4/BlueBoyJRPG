package entity.combatants;

import entity.skills.Skill;

public class Slime extends Combatant{

    Skill[] basicSkills =  new Skill[]{new Skill(Skill.type.physical, Skill.element.physical, 10)};
    public Slime(){
        super(
                "Slime",
                1,
                0,
                new Affinities(),
                new Resistances(.8f, .8f, .8f, .8f, .8f, .8f, 1.5f),
                new GrowthRates(),
                new BaseStats(),
                new Skill[]{new Skill(Skill.type.physical, Skill.element.physical, 10)},
                35,
                ExpGrowthRate.MedFast
               );

    }
}
