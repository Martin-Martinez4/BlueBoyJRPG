package entity.combatants;

import entity.skills.Skill;

public class BlueBoyDefault extends Combatant{
    Skill[] basicSkills =  new Skill[]{new Skill(Skill.type.physical, Skill.element.physical, 10)};
    public BlueBoyDefault(){
        super(
                "Slime",
                1,
                0,
                new Affinities(),
                new Resistances(),
                new GrowthRates(),
                new BaseStats(),
                new Skill[]{new Skill(Skill.type.physical, Skill.element.physical, 10)}
        );

    }
}
