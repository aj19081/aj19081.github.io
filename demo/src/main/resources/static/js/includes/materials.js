//contains all the data for a material
//and setting the uniforms
class Material {
    constructor(colour, texture, metallic, roughness, roughnessTexture) {
        this.setColour(colour);
        this.setTexture(texture);
        this.setMetallic(metallic);
        this.setRoughness(roughness);
        this.setRoughnessTexture(roughnessTexture);
    }

    setColour(colour) {
        this.colour = colour;
        this.updateSpecColour();
    }

    setMetallic(metallic) {
        //0 <= metallic <= 1
        //1 = pure metal, 0 = pure dielectric
      console.assert(metallic >= 0 && metallic <= 1)
      this.metallic = metallic;
      this.updateSpecColour();
    }

    setRoughness(roughness) {
        //0 < roughness <= 1
        //0 = very shiny, 1 = very matte
        //roughness of 0 removes specular and shouldn't be used
      console.assert(roughness > 0 && roughness <= 1)
      this.roughness = roughness;
    }

    //for varying roughness via texture
    setRoughnessTexture(tex) {
      this.hasRoughnessTexture = tex != null;
      this.roughnessTexture = tex;
    }

    //dielectrics have white specular highlights
    //metals have coloured specular highlights
    //as an approximation, using normalised albedo colours for specular colours
    updateSpecColour() {
      let div = max([this.colour.x, this.colour.y, this.colour.z]);
      //keep all rgb components between 0-1
      let norm = div <= 0 ? createVector(1, 1, 1) : createVector(this.colour.x, this.colour.y, this.colour.z).div(div);
      this.specColour = createVector(1,1,1).lerp(norm, this.metallic);
    }

    //for varying colour via texture
    setTexture(texture) {
        this.hasTexture = texture != null;
        this.texture = texture;
    }

    //set uniforms
    applyMaterial(shader) {
        shader.setUniform("uColour", [this.colour.x, this.colour.y, this.colour.z]);
        shader.setUniform("uHasTexture", [this.hasTexture ? 1.0 : 0.0]);
        if (this.hasTexture) {
          shader.setUniform("uTexture", this.texture);
        }
        shader.setUniform("uSpecColour", [this.specColour.x, this.specColour.y, this.specColour.z]);
        shader.setUniform("uMetallic", [this.metallic]);
        shader.setUniform("uRoughness", [this.roughness]);
        shader.setUniform("uHasRoughnessTexture", [this.hasRoughnessTexture ? 1.0 : 0.0]);
        if (this.hasRoughnessTexture) {
          shader.setUniform("uRoughnessTexture", this.roughnessTexture);
        }
      }
}
  
//material data for saving to a json file
class MaterialData {
    constructor(name, colour, hasTexture, texturePath, metallic, roughness, hasRoughnessTexture, roughnessTexture) {
      console.assert(metallic >= 0 && metallic <= 1)
      console.assert(roughness > 0 && roughness <= 1)
      this.name = name;
      this.colour = colour;
      this.metallic = metallic;
      this.roughness = roughness;
      this.hasTexture = hasTexture;
      this.texturePath = texturePath;
      this.hasRoughnessTexture = hasRoughnessTexture;
      this.roughnessTexturePath = roughnessTexture;
    }
}

//loads all materials from JSON array into a dictionary to be accessed by SceneObjects
function loadMaterials(materialsJSON) {
    materialDictionary = {};
    materialsJSON.materials.forEach(matData => {
        //function is called from preload so images should be loaded before drawn
        let tex = matData.hasTexture ? loadImage(TexturesPath + matData.texturePath) : null;
        let rTex = matData.hasRoughnessTexture ? loadImage(TexturesPath + matData.roughnessPath) : null;
        let col = createVector(matData.colour[0], matData.colour[1], matData.colour[2]);
        materialDictionary[matData.name] = new Material(col, tex, matData.metallic, matData.roughness, rTex);
    });

    loadJSON(ScenePath, sceneLoaded);
}
  