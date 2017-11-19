precision mediump float;
attribute vec4 aPosition;
attribute vec3 aSurfaceNormal;
attribute vec2 aTextureCoord;

uniform vec3 uSunDirection;
uniform mat4 uMVPMatrix;
uniform mat4 uMMatrix;

varying vec4 vPosition;
varying vec4 vSunDirection;
varying vec4 vSurfaceNormal;
varying vec2 vTextureCoord;

void main() {
    vTextureCoord = aTextureCoord;
    gl_Position = uMVPMatrix * aPosition;
    vPosition = aPosition;
    vSurfaceNormal = vec4(normalize(aSurfaceNormal).xyz, 0.0);
    vSunDirection = -normalize(uMMatrix * vec4(uSunDirection.xyz, 0.0));
}