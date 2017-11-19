precision mediump float;
uniform mat4 uEyePositionMatrix;
uniform vec3 uAmbientLight;
uniform vec3 uEyePosition;
uniform vec3 uSunlight;
uniform mat4 uMMatrix;

varying vec4 vSurfaceNormal;
varying vec4 vSunDirection;
varying vec4 vColor;
varying vec4 vPosition;

void main() {
    float angleCos = max(0.0, dot(vSurfaceNormal, vSunDirection));

    vec4 reflection = reflect(-vSunDirection, vSurfaceNormal);
    vec4 eyeDirection = uMMatrix * uEyePositionMatrix * normalize(vec4(uEyePosition.xyz - vPosition.xyz, 0.0));

    float blink = max(0.0, dot(reflection, eyeDirection));
    blink = pow(blink, 2.0);

    gl_FragColor = vec4(
        angleCos * vColor.xyz * uSunlight.xyz +
        blink * vColor.xyz * uSunlight.xyz +
        vColor.xyz * uSunlight.xyz * uAmbientLight.xyz, vColor.a);
}