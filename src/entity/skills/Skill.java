package entity.skills;

public class Skill {

     public enum type{
        physical,
        magic
    }

    public enum element{
        fire,
        ice,
        force,
        lightning,
        dark,
        light,
        physical,
    }

    String name;
    type type;
    element element;
    int power;
    int mpCost;

   public Skill(String name, type type, element element, int power, int mpCost){
       this.name = name;
        this.type = type;
        this.element = element;
        this.power = power;
        this.mpCost = mpCost;
    }

    public String getName(){return name;}
    public Skill.type getType() {
        return type;
    }

    public Skill.element getElement() {
        return element;
    }

    public int getPower() {
        return power;
    }
    public int getMpCost() {
        return mpCost;
    }
}
