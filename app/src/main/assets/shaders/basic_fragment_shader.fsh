precision mediump float;
uniform vec4 uColor;
uniform vec3 uAmbientLight;
uniform vec3 uSunlight;

varying vec4 vSurfaceNormal;
varying vec4 vSunDirection;

void main() {
    float angleCos = max(0.0, dot(vSurfaceNormal, vSunDirection));
    //vec3 m = angleCos * uSunlight.xyz * uColor.xyz * uAmbientLight.xyz;
    //vec4 newColor = vec4(m.xyz, uColor.a);
    vec4 newColor = vec4(angleCos * uColor.xyz + uColor.xyz * uAmbientLight.xyz, uColor.a);
    //vec4 newColor = vec4(vSurfaceNormal.xyz, uColor.a);
    gl_FragColor = newColor;
}