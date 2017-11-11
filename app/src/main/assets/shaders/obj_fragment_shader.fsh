precision mediump float;
uniform vec3 uAmbientLight;
uniform vec3 uSunlight;

varying vec4 vSurfaceNormal;
varying vec4 vSunDirection;

void main() {
    float angleCos = max(0.0, dot(vSurfaceNormal, vSunDirection));
    gl_FragColor = vec4(angleCos * vec3(0.5, 0.5, 0.5).xyz + vec3(0.5, 0.5, 0.5).xyz * uAmbientLight.xyz, 1.0);
}