package entity.skills;

public class Skill {

    static public enum type{
        physical,
        magic
    }

    public enum element{
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

   public Skill(type type, element element, int power){
        this.type = type;
        this.element = element;
        this.power = power;
    }

    public Skill.type getType() {
        return type;
    }

    public Skill.element getElement() {
        return element;
    }

    public int getPower() {
        return power;
    }
}
