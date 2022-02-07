//for storing data on ambient lighting

class SkyAmbient {
    constructor(ground, horizon, sky, sunIntensity) {
        this.ground = ground;
        this.horizon = horizon;
        this.sky = sky;
        this.sunIntensity = sunIntensity;
    }

    applyAmbient(shader) {
        ////can be used for a dynamic background colour corresponding to the ambient light at viewing angle
        // let up = constrain(y, 0, 1);
        // let down = constrain(-y, 0, 1);
        // let h = createVector(this.horizon.x, this.horizon.y, this.horizon.z);
        // let col = h.lerp(this.ground, down).lerp(this.sky, up);
        // background(col.x * 255, col.y * 255, col.z * 255);

        shader.setUniform("uSunIntensity", [this.sunIntensity]);
        shader.setUniform("uGroundColour", [this.ground.x,this.ground.y,this.ground.z]);
        shader.setUniform("uHorizonColour", [this.horizon.x,this.horizon.y,this.horizon.z]);
        shader.setUniform("uSkyColour", [this.sky.x,this.sky.y,this.sky.z]);
    }
}