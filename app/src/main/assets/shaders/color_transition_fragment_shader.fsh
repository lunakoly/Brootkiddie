precision mediump float;
varying vec4 vColor;
uniform vec3 uAmbientLight;

void main() {
    vec4 newColor = vec4(vColor.rgb * uAmbientLight.rgb, vColor.a);
    gl_FragColor = newColor;
}