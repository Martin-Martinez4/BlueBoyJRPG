package entity.combatants;

public class Affinities {
    float fire = 1;
    float ice = 1;
    float force = 1;
    float lighting = 1;
    float dark = 1;
    float light = 1;
    float physical = 1;

    public Affinities( float fire, float ice, float force, float lighting, float dark, float light, float physical){
        this.fire = fire;
        this.ice = ice;
        this.force = force;
        this.lighting = lighting;
        this.dark = dark;
        this.light = light;
        this.physical = physical;
    }

    public Affinities(){

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

    public float getLightning() {
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
