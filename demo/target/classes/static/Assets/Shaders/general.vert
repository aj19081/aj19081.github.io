precision mediump float;

//set by p5.js
//vertex positions, normals and UV coordinates
attribute vec3 aPosition;
attribute vec3 aNormal;
attribute vec2 aTexCoord;

uniform mat4 uModelViewMatrix;
uniform mat4 uProjectionMatrix;
uniform mat3 uNormalMatrix;

varying vec3 vNormal;
varying vec3 vViewPosition;
varying highp vec2 vTexCoord;

void main(void) {
    vec4 pos = uModelViewMatrix * vec4(aPosition, 1.0);
    vViewPosition = pos.xyz;
    gl_Position = uProjectionMatrix * pos;
    //lighting is calculated in eye space
    //converts normals to eye space
    vNormal = normalize((uNormalMatrix * aNormal).xyz);
    vTexCoord = aTexCoord;
}