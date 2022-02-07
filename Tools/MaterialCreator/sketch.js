const MaterialsPath = "Assets/materials.json";
const ShaderPath = "../../demo/src/main/resources/static/Assets/Shaders/"

var testModel;
var generalShader;
var ambient;

var material;

//html inputs
var colorPicker;
var nameInput;
var textureInput;
var roughnessTextureInput;
var metallicSlider;
var roughnessSlider;
var saveButton;

//called before setup/draw
//guarentees models/shaders are loaded before they are referenced
function preload() {
	print(ShaderPath + 'general.vert');
    generalShader = loadShader(ShaderPath + 'general.vert', ShaderPath + 'general.frag');
    testModel = loadModel('Assets/monkey.obj', true);
    material = new Material(createVector(0.7, 0.7, 0.7), null, 1.0, 0.1, null);

    loadUI();
}

//called once after preload and before draw
function setup() {
    setupTests();
    
    createCanvas(1200,800, WEBGL); 
    cam = createCamera();

    ambient = new SkyAmbient(
        createVector(0.1,0.05,0.0).mult(1.0), 
        createVector(0.35,0.35,0.4).mult(1.0), 
        createVector(0.1,0.35,0.5).mult(2.25), 1.0);

    shader(generalShader);
    textureUpdate();
    roughnessTextureUpdate();
    changeRoughness();
    changeColour();
    changeMetallic();
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

    material.applyMaterial(generalShader);
    ambient.applyAmbient(generalShader);
    model(testModel);
}

function setupTests() {
    console.assert(generalShader != undefined)
    console.assert(testModel != undefined)
    console.assert(material != undefined)
}

//create and setup html UI
function loadUI() {
    colorPicker = createColorPicker('#CCCCCC');
    colorPicker.position(0, 800 + 5);
    colorPicker.input(changeColour);

    textureInput = createInput("Marble-Roughness.jpg");
    textureInput.position(0, 800 + 30);
    textureInput.input(textureUpdate);

    roughnessTextureInput = createInput("Marble-Roughness.jpg");
    roughnessTextureInput.position(0, 800 + 45);
    roughnessTextureInput.input(roughnessTextureUpdate);

    material.setTexture(loadImage("Assets/Marble-Roughness.jpg"));

    metallicSlider = createSlider(0.0, 1.0, 0.0, 0.01);
    metallicSlider.position(0, 800 + 60);
    metallicSlider.input(changeMetallic);

    roughnessSlider = createSlider(0.01, 1.0, 0.5, 0.01);
    roughnessSlider.position(0, 800 + 80);
    roughnessSlider.input(changeRoughness);
  
    nameInput = createInput("name");
    nameInput.position(0, 800 + 100);
  
    saveButton = createButton("Save");
    saveButton.position(0, 800 + 125);
    saveButton.mousePressed(saveMaterial);
}

//called on texture input changed
function textureUpdate() {
    loadImage("Assets/" + textureInput.value(), imageLoaded, imageFailed);
}

//successfully loaded texture input
function imageLoaded(tex) {
    material.setTexture(tex);
}

//if failed to load image (e.g. none specified/incorrect path)
//assume material doesn't have a texture
function imageFailed(e) {
    material.setTexture(null);
}

//called on roughness texture input changed
function roughnessTextureUpdate() {
    loadImage("Assets/" + roughnessTextureInput.value(), roughnessImageLoaded, roughnessImageFailed);
}

//successfully loaded texture input
function roughnessImageLoaded(tex) {
    material.setRoughnessTexture(tex);
}

//if failed to load image (e.g. none specified/incorrect path)
//assume material doesn't have a texture
function roughnessImageFailed(e) {
    material.setRoughnessTexture(null);
}

//called on save button pressed
function saveMaterial() {
    loadJSON(MaterialsPath, addMaterial);
}

function changeRoughness() {
    material.setRoughness(roughnessSlider.value());
}

function changeMetallic() {
    material.setMetallic(metallicSlider.value());
}
  
//adds material to the current json and saves the file
//json file then has to be manually replaced in the source 
function addMaterial(loadedMaterials) {
    //initialise new material
    let col = colorPicker.color();
    let name = nameInput.value();
    if (name === "") {
        console.error("Name has no value")
        return
    }
    let mat = new MaterialData(name, 
        [red(col) / 255.0, green(col) / 255.0, blue(col) / 255.0], 
        material.hasTexture, 
        "Assets/" + textureInput.value(), 
        metallicSlider.value(), 
        roughnessSlider.value(),
        material.hasRoughnessTexture,
        "Assets/" + roughnessTextureInput.value());
    
    //if name is the same as a material in json file,
    //overrite material rather than adding a new one
    let overwrite = false;
    for (let i = 0; i < loadedMaterials.materials.length && !overwrite; i++) {
        const element = loadedMaterials.materials[i];
        if (element.name === name) {
            overwrite = true;
            loadedMaterials.materials[i] = mat;
        }
    }

    //if not overrwritten, push the new material on the end
    if (!overwrite) {
        loadedMaterials.materials.push(mat);
    }
    //save to file, has to be manually copied over to source code
    saveJSON(loadedMaterials, "materials.json");
}

//called on colour picker value changed
function changeColour() {
    let col = colorPicker.color();
    material.setColour(createVector(red(col) / 255.0, green(col) / 255.0, blue(col) / 255.0));
}