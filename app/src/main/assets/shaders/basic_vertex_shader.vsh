attribute vec4 aPosition;
attribute vec3 aSurfaceNormal;
uniform vec3 uSunDirection;
uniform mat4 uMVPMatrix;
uniform mat4 uMMatrix;

varying vec4 vSurfaceNormal;
varying vec4 vSunDirection;


void main() {
    vSurfaceNormal = uMMatrix * vec4(aSurfaceNormal.xyz, 0.0);
    vSunDirection = vec4(uSunDirection.xyz, 0.0);
    gl_Position = uMVPMatrix * aPosition;
}