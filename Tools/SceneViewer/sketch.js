const MaterialsPath = "Assets/materials.json";
const ScenePath = "Assets/scene.json";
const ShaderPath = "../../demo/src/main/resources/static/Assets/Shaders/"

var generalShader;
var ambient;

var materialDictionary;

var scene;

//called before setup/draw
//guarentees models/shaders are loaded before they are referenced
function preload() {
    generalShader = loadShader(ShaderPath + 'general.vert', ShaderPath + 'general.frag');
    loadJSON(MaterialsPath, loadMaterials);
}

//loads all materials into a dictionary to be accessed by SceneObjects
function loadMaterials(materialsJSON) {
    materialDictionary = {};
    materialsJSON.materials.forEach(matData => {
        let tex = matData.hasTexture ? loadImage(matData.texturePath) : null;
        let rTex = matData.hasRoughnessTexture ? loadImage(matData.roughnessPath) : null;
        let col = createVector(matData.colour[0], matData.colour[1], matData.colour[2]);
        materialDictionary[matData.name] = new Material(col, tex, matData.metallic, matData.roughness, rTex);
    });

    loadJSON(ScenePath, sceneLoaded);
}

function sceneLoaded(sceneJSON) {
    let sceneObjects = [];
    sceneJSON.objects.forEach(obj => {
        let pos = createVector(obj.position[0], obj.position[1], obj.position[2]);
        let rot = createVector(obj.rotation[0], obj.rotation[1], obj.rotation[2]);
        let scale = createVector(obj.scale[0], obj.scale[1], obj.scale[2]);
        sceneObjects.push(new SceneObject(loadModel(obj.model), pos, rot, scale, materialDictionary[obj.materialName]));
    });
    scene = new Scene(sceneObjects);
}

//called once after preload and before draw
function setup() {
    createCanvas(1200,800, WEBGL); 
    cam = createCamera();

    ambient = new SkyAmbient(
        createVector(0.1,0.05,0.0).mult(1.0), 
        createVector(0.35,0.35,0.4).mult(1.0), 
        createVector(0.1,0.35,0.5).mult(2.25), 1.0);

    shader(generalShader);
}

//called once per frame
function draw() {
    if (isRotating) {
        cameraMovement();
    }
    render();
}

//sets uniforms and renders the model with the material
function render() {
    directionalLight(255, 255, 255,1,1,-1);
    background(200);
    noStroke();

    ambient.applyAmbient(generalShader);
    scene.render(generalShader);
}