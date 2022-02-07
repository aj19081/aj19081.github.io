
class Scene {
    //list of SceneObjects
    constructor(sceneObjects) {
        this.sceneObjects = sceneObjects;
    }

    render(shader) {
        this.sceneObjects.forEach(obj => {
            obj.render(shader);
        });
    }
}

function sceneLoaded(sceneJSON) {
    let sceneObjects = [];
    //convert SceneObjectData JSON object to more usable js object SceneObject
    sceneJSON.objects.forEach(obj => {
        let pos = createVector(obj.position[0], obj.position[1], obj.position[2]);
        let rot = createVector(obj.rotation[0], obj.rotation[1], obj.rotation[2]);
        let scale = createVector(obj.scale[0], obj.scale[1], obj.scale[2]);
        sceneObjects.push(new SceneObject(loadModel(ModelsPath + obj.model), pos, rot, scale, materialDictionary[obj.materialName]));
    });
    scene = new Scene(sceneObjects);
}