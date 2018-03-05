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


        // ABERRATION //
    vec2 texAberration = v_texCoords;
    float activeInterval = abs(v_texCoords.y - prog);

    if (activeInterval < 0.01 && mod(u_time, 2768.0) < 400.0) {
        activeInterval = (((v_texCoords.y - prog) / 0.03) + 4.14) * PI;   // 1 + pi/2
        float state = sin(activeInterval * 2.0);
        texAberration.x += state * 0.2;
    }

    if (texAberration.x < -1.0) texAberration.x = 1.0 - texAberration.x;
    else if (texAberration.x > 1.0) texAberration.x = texAberration.x - 1.0;


    gl_FragColor = v_color * texture2D(u_texture, texAberration);
}