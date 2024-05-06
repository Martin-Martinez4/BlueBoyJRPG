package entity.combatants;

public class Resistances {
    static public int nullify = 0;
    static public int absorb = -1;
    static public int reflect = 2;

    public float fire = 1;
    public float ice = 1;
    public float force = 1;
    public float lighting = 1;
    public float dark = 1;
    public float light = 1;
    public float physical = 1;

    public Resistances( float fire, float ice, float force, float lighting, float dark, float light, float physical){
        this.fire = fire;
        this.ice = ice;
        this.force = force;
        this.lighting = lighting;
        this.dark = dark;
        this.light = light;
        this.physical = physical;
    }

    public Resistances(){

    }

    public float getFire() {
        return fire;
    }

    public float getIce() {
        return ice;
    }

    public float getForce() {
        return force;
    }

    public float getLighting() {
        return lighting;
    }
    public float getDark() {
        return dark;
    }
    public float getLight() {
        return light;
    }

    public float getPhysical() {
        return physical;
    }


}
