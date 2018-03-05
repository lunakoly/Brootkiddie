#define PI 3.14159265
precision mediump float;

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float u_time;


float rand(vec2 co) {
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}


void main() {
    float prog = u_time / 5000.0 - 1.0;

        // NOIZE //
    vec2 seed = vec2(v_texCoords.x + prog, v_texCoords.y + prog);
    vec4 noizedColor = vec4(
        v_color.r * rand(seed),
        v_color.g * rand(seed),
        v_color.b * rand(seed),
        1.0);

    vec4 tex = texture2D(u_texture, v_texCoords);
    tex.r += 1.0 * rand(vec2(prog, prog));
    tex.g += 1.0 * rand(vec2(prog + 1.0, prog - 1.0));
    tex.b += 1.0 * rand(vec2(prog + 1.0, prog - 1.0));

    gl_FragColor = noizedColor * tex;
}