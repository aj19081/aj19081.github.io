precision mediump float;
#define PI 3.1415926535897932384626433832795

//set by p5.js
//shader only supports one directional light
uniform vec3 uLightingDirection[5];
uniform vec3 uDirectionalDiffuseColors[5];

//p5.js clamps directional colour to maximum of 1
//allows for stronger lights
uniform float uSunIntensity;

//skybox settings
uniform vec3 uGroundColour;
uniform vec3 uHorizonColour;
uniform vec3 uSkyColour;

uniform mat4 uViewMatrix;

//material paramaters
uniform vec3 uColour;
//1 if texture, 0 otherwise
uniform float uHasTexture;
uniform sampler2D uTexture;
uniform vec3 uSpecColour;
uniform float uMetallic;
uniform float uRoughness;
//1 if texture, 0 otherwise
uniform float uHasRoughnessTexture;
uniform sampler2D uRoughnessTexture;

//set in general.vert
varying vec3 vNormal;
varying vec2 vTexCoord;
varying vec3 vViewPosition;

//using structs to make passing parameters cleaner
struct Material {
    vec3 albedo;
    vec3 specColour;
    float metallic;
    float roughness;
    vec3 normal;
};

struct Light {
    vec3 colour;
    vec3 direction;
};

//samples a skybox with a linear mix of a 3 colour gradient
//TODO replace with a environment map for more detailed reflections
vec3 skybox(vec3 dir) {
    return dir.y > 0.0 ? mix(uHorizonColour, uSkyColour, dir.y) : mix(uHorizonColour, uGroundColour, -dir.y);
}

//returns ambient colour, including metallic calculations
//diffuse reflection is from the vertex normal
//metallic/specular reflection from the view reflected with normal surface
vec3 ambient(Material mat, float fresnel, vec3 view) {
    vec3 ref = reflect(view, mat.normal);
    vec3 difAmb = skybox(-mat.normal) * mat.albedo;
    vec3 refAmb = skybox(-ref) * mat.specColour;
    vec3 amb = mix(difAmb * mix(1.0, (1.0 - fresnel), mat.metallic), refAmb * fresnel, mat.metallic);
    return amb;
}

//beckmann distribution used in cook-torrence specular calculation
//TODO replace with the GGX distribution
//https://graphicscompendium.com/gamedev/15-pbr
float beckmann(vec3 nrm, vec3 halfway, float roughness) {
    float cosa = max(dot(nrm, halfway), 0.001) ;
    float cosa2 = cosa * cosa;

    float r2 = roughness * roughness;

    float t = (1.0 - cosa2) / cosa2;

    return exp(-t / r2) / (PI * r2 * cosa2 * cosa2);
}

//fast approximation of fresnel equation
//r0 is arbitary value from 0-1
//r0 can be precalculated with IOR
//https://en.wikipedia.org/wiki/Schlick%27s_approximation
float schlick(vec3 nrm, vec3 view, float r0) {
    float c0 = dot(nrm, view);
    float c = 1.0 - c0;
    float c2 = c * c;

    return r0 + (1.0 - r0) * c2 * c2 * c;
}

//PBR specular component
//uses a distribution function (beckmann) to calculate the probability of light bouncing in view direction
//https://en.wikipedia.org/wiki/Specular_highlight#Cook–Torrance_model
float cookTorrence(vec3 view, vec3 nrm, vec3 halfway, vec3 lightDir, float fresnel, float roughness) {
    float d = beckmann(nrm, halfway, roughness);
    float hn = dot(halfway, nrm);
    float hndvh = hn / dot(view, halfway);
    float vn = dot(view, nrm);
    float nl = dot(lightDir, nrm);
    float g = min(1.0, min(2.0 * hndvh * vn, 2.0 * hndvh * nl));
    //fresnel as the amount of reflected light depends on the viewing angle
    return d * fresnel * g / (PI * vn * nl);
}

//return specular colour, out specular component for energy conservation
vec3 spec(Material mat, Light light, vec3 view, float fresnel, out float specComponent) {
    //halfway value commonly used as a optimisation
    vec3 halfway = normalize(light.direction - view);
    specComponent = cookTorrence(view, mat.normal, halfway, light.direction, fresnel, mat.roughness);
    return (specComponent * light.colour) * mat.specColour;
}

//simple lambert diffuse
vec3 lambertDiffuse(Material mat, Light light) {
    vec3 l = light.colour * max(0.0, dot(light.direction, mat.normal));
    //metallic objects have no diffuse component
    return l * mat.albedo * (1.0 - mat.metallic);
}

//all lighting/shading calculations done here
vec3 lighting(Material mat, Light light, vec3 view, float fresnel) {
    vec3 diffuseCol = lambertDiffuse(mat, light);
    float specComponent;
    vec3 spec = (spec(mat, light, view, fresnel, specComponent));
    //1.0 - specComponent for energy conservation
    return diffuseCol * (1.0 - specComponent) + spec + ambient(mat, fresnel, view);
}


void main(void) {
    //lighting calculated in eye space
    vec3 lightDir = (uViewMatrix * vec4(-uLightingDirection[0], 0.0)).xyz;
    Light light = Light(uDirectionalDiffuseColors[0] * uSunIntensity, lightDir);

    //surface normals
    vec3 nrm = normalize(vNormal);
    //view direction
    vec3 view = normalize(vViewPosition);

    //couldn't pass a uniform boolean so using floats for uHasTexture
    vec3 albedo = mix(vec3(1.0,1.0,1.0), texture2D(uTexture, vTexCoord).xyz, uHasTexture) * uColour;

    float roughness = uHasRoughnessTexture == 1.0 ? texture2D(uRoughnessTexture, vTexCoord).x * uRoughness : uRoughness;

    Material mat = Material(albedo, uSpecColour, uMetallic, roughness, nrm);

    //fresnel adjusts the amount of reflected light depending on viewing angle
    //0.4 is an abritary r0, ideally would be adjusted for each material for realism
    float fresnel = (schlick(nrm, -view, 0.4));
    gl_FragColor = vec4(lighting(mat, light, view, fresnel), 1.0);
}