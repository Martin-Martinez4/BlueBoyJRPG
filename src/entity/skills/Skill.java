package entity.skills;

public class Skill {

    static public enum type{
        physical,
        magic
    }

    static public enum element{
        fire,
        ice,
        force,
        lighting,
        dark,
        light,
        physical,
    }

    type type;
    element element;
    int power;

    Skill(type type, element element, int power){
        this.type = type;
        this.element = element;
        this.power = power;
    }

}
