//holds all the data/functionality required to render a single model
class SceneObject {
    constructor(model, position, rotation, scale, material) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.material = material;
    }

    //applies material, transformations and renders model
    render(shader) {
        strokeWeight(0);
        this.material.applyMaterial(shader);

        //push matrix, perform translations then pop
        //so that transformations only apply to this SceneObject
        push();
        translate(this.position);
        rotateZ(this.rotation.z);
        rotateY(this.rotation.y);
        rotateX(this.rotation.x);
        scale(this.scale);
        model(this.model);
        pop();
    }
}

//data for JSON
class SceneObjectData {
    constructor(modelPath, position, rotation, scale, materialName) {
        this.model = modelPath;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.material = materialName;
    }
}