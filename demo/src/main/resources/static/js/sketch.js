//JSON storing material data
const MaterialsPath = "/Assets/materials.json";
//JSON storing 3D positions of rooms for path rendering
const RoomPositionsPath = "/Assets/roomPositions.json";
//JSON storing SceneObject models/materials/transformations
const ScenePath = "/Assets/scene.json";
//Path for assets
const TexturesPath = "/Assets/Textures/";
const ShaderPath = "/Assets/Shaders/";
const ModelsPath = "/Assets/Models/";

//p5.js has arbitrary scale, scaling the whole scene so that everything is more within the clipping range
const ConstantScale = 8;

//preloaded shader
var generalShader;
//ambient object for setting ambient lighting
var ambient;

//maps material name to Material object
var materialDictionary;

//contains all SceneObjects for rendering
var scene;

//called before setup/draw
//guarantees models/shaders/textures are loaded before they are referenced and drawn
function preload() {
    generalShader = loadShader(ShaderPath + 'general.vert', ShaderPath + 'general.frag');
    loadJSON(MaterialsPath, loadMaterials);
    loadJSON(RoomPositionsPath, loadPositions);
}

//called once after preload and before draw
function setup() {
    createCanvas(1600,800, WEBGL);
    cam = createCamera();

    //repeats UV coordinated to tile textures
    textureWrap(REPEAT);

    ambient = new SkyAmbient(
        createVector(0.1,0.05,0.0).mult(2.0),
        createVector(0.35,0.35,0.4).mult(1.0),
        createVector(0.1,0.35,0.5).mult(1.0), 1.0);

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
    directionalLight(255, 255, 255,-1,1,1);
    //current background colour of webpage so canvas blends better
    background(176, 224, 230);
    noStroke();

    ambient.applyAmbient(generalShader);
    push();
    //Models import flipped on X-axis for some reason?
    //maybe something weird with .obj files in p5.js
    scale(-ConstantScale, ConstantScale, ConstantScale);
    //accounting for Blender's flipped Y and Z axis
    rotateY(PI);
    rotateX(PI);

    drawPath();
    scene.render(generalShader);
    pop();
}