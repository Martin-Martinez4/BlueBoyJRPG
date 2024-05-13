package entity.combatants;

import entity.skills.Skill;

public class BlueBoyMage extends Combatant{
    Skill[] basicSkills =  new Skill[]{new Skill(Skill.type.physical, Skill.element.physical, 10)};
    public BlueBoyMage(){
        super(
                "Slime",
                1,
                0,
                new Affinities(1.2f, 1f, 1f, 1f, 1f, 1f, .8f),
                new Resistances(1.2f, 1f, 1f, 1f, 1f, 1f, .8f),
                new GrowthRates(),
                new BaseStats(),
                new Skill[]{new Skill(Skill.type.magic, Skill.element.fire, 10)},
                250,
                ExpGrowthRate.MedSlow,
                100
        );

    }
}
