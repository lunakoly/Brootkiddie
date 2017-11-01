precision mediump float;
uniform vec4 uColor;
uniform vec3 uAmbientLight;

void main() {
    vec4 newColor = vec4(uColor.rgb * uAmbientLight.rgb, uColor.a);
    gl_FragColor = newColor;
}