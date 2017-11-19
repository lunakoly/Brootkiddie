precision mediump float;
uniform vec3 uAmbientLight;
uniform vec3 uSunPosition;
uniform vec3 uEyePosition;
uniform vec3 uSunlight;
uniform mat4 uMMatrix;
uniform mat4 uEyePositionMatrix;

varying vec4 vSurfaceNormal;
varying vec4 vSunDirection;
varying vec4 vPosition;

void main() {
    float angleCos = max(0.0, dot(vSurfaceNormal, vSunDirection));

    vec4 reflection = reflect(-vSunDirection, vSurfaceNormal);
    vec4 eyeDirection = uMMatrix * uEyePositionMatrix * normalize(vec4(uEyePosition.xyz - vPosition.xyz, 0.0));

    float blink = max(0.0, dot(reflection, eyeDirection));
    blink = pow(blink, 2.0);

    gl_FragColor = vec4(
        angleCos * vec3(0.5, 0.5, 0.5).xyz +
        blink * vec3(0.5, 0.5, 0.5).xyz +
        vec3(0.5, 0.5, 0.5).xyz * uAmbientLight.xyz, 1.0);
}