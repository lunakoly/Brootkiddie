precision mediump float;

attribute vec4 aPosition;
varying vec4 vPosition;
varying vec4 vNormalPosition;

attribute vec4 aSurfaceNormal;
varying vec4 vSurfaceNormal;

attribute vec2 aTextureCoord;
varying vec2 vTextureCoord;

uniform vec4 uSunDirection;
varying vec4 vSunDirection;

uniform mat4 uMMatrix;
uniform mat4 uVMatrix;
uniform mat4 uPMatrix;


void main() {
    mat4 mvMat = uVMatrix * uMMatrix;
    mat4 mvpMat = uPMatrix * uVMatrix * uMMatrix;

    vNormalPosition = aPosition;
    gl_Position = mvpMat * aPosition;
    vPosition = gl_Position;

    vSurfaceNormal = aSurfaceNormal;
    vSurfaceNormal.w = 0.0;
    vSurfaceNormal = mvMat * vSurfaceNormal;
    vSunDirection = uVMatrix * normalize(uSunDirection);

    vTextureCoord = aTextureCoord;
}