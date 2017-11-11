attribute vec4 aPosition;
attribute vec3 aSurfaceNormal;
attribute vec4 aColor;

uniform vec3 uSunDirection;
uniform mat4 uMVPMatrix;
uniform mat4 uMMatrix;

varying vec4 vColor;
varying vec4 vSurfaceNormal;
varying vec4 vSunDirection;


void main() {
    vColor = aColor;
    vSurfaceNormal = vec4(normalize(aSurfaceNormal).xyz, 0.0);
    vSunDirection = normalize(uMMatrix * vec4(uSunDirection.xyz, 0.0));
    vSunDirection.xyz *= -1.0;
    gl_Position = uMVPMatrix * aPosition;
}