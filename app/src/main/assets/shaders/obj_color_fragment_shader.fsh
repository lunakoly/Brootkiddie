precision mediump float;
uniform vec3 uAmbientLight;
uniform vec3 uSunlight;

varying vec4 vSurfaceNormal;
varying vec4 vSunDirection;
varying vec4 vColor;

void main() {
    float angleCos = max(0.0, dot(vSurfaceNormal, vSunDirection));
    gl_FragColor = vec4(angleCos * vColor.xyz + vColor.xyz * uAmbientLight.xyz, 1.0);
}