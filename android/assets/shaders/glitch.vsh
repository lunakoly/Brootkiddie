#define PI 3.14159265
precision mediump float;

attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;
uniform float u_time;

varying vec4 v_color;
varying vec2 v_texCoords;


void main()
{
    v_color = a_color;
    v_color.a = v_color.a * (256.0/255.0);
    v_texCoords = a_texCoord0;
    gl_Position =  u_projTrans * a_position;


    float prog = mod(u_time, 1000.0) / 500.0 - 1.0;

    // ABERRATION //
    float activeInterval = abs(v_texCoords.y - prog);

    if (activeInterval < 0.03) {
        activeInterval = (((v_texCoords.y - prog) / 0.03) + 2.57) * PI;   // 1 + pi/2
        float state = sin(activeInterval * 2.0);
        gl_Position.x += state * 0.2;
    }
}