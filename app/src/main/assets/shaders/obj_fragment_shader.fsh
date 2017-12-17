precision mediump float;

uniform struct sScene {
    float ambientCoefficient;
    vec3 sunlight;
    vec4 eyePosition;
    float time;
} uScene;

uniform struct sMaterial {
    vec3 ambientLight;
    vec3 diffuseLight;
    vec3 specularLight;
    float shininess;
    float opacity;
} uMaterial;

uniform sampler2D uTexture;
uniform int uUseTexture;

varying vec4 vPosition;
varying vec4 vNormalPosition;
varying vec4 vSurfaceNormal;
varying vec4 vSunDirection;
varying vec2 vTextureCoord;


void main() {
    vec4 tex;

    if (uUseTexture == 1)
        tex = texture2D(uTexture, vTextureCoord);
    else
        tex = vec4(1.0, 1.0, 1.0, 1.0);

    vec3 ambient = uScene.sunlight * uMaterial.ambientLight * uScene.ambientCoefficient;

    float diffuseCoefficient = max(0.0, dot(vSurfaceNormal, -vSunDirection));
    vec3 diffuse = diffuseCoefficient * uScene.sunlight * uMaterial.diffuseLight;

    vec4 directionToCam = normalize(uScene.eyePosition - vPosition);
    float specularCoefficient = pow(max(0.0, dot(directionToCam, reflect(vSunDirection, vSurfaceNormal))), uMaterial.shininess);
    vec3 specular = specularCoefficient * uScene.sunlight * uMaterial.specularLight;

    float distanceToLight = 0.0; // SUN
    float attenuation = 1.0 / (1.0 + distanceToLight * distanceToLight);

    vec3 linearColor = ambient + attenuation * (diffuse + specular);
    gl_FragColor = vec4(linearColor * tex.rgb, uMaterial.opacity);
}