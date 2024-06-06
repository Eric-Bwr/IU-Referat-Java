#shader vertex
#version 430 core

layout (location = 0) in vec3 inPosition;
layout (location = 1) in vec2 inTextureCoords;
layout (location = 2) in float inFace;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 transformation;

out vec2 textureCoords;
out float face;

void main() {
    face = inFace;
    textureCoords = inTextureCoords;
    vec4 pos = vec4(inPosition, 1.0);
    gl_Position = projection * view * transformation * pos;
}

#shader fragment
#version 430 core

in vec2 textureCoords;
in float face;

uniform sampler2D textureBottom;
uniform sampler2D textureSide;
uniform sampler2D textureTop;

out vec4 FragColor;

const int FACE_FRONT = 0;
const int FACE_BACK = 1;
const int FACE_LEFT = 2;
const int FACE_RIGHT = 3;
const int FACE_TOP = 4;
const int FACE_BOTTOM = 5;
const vec3 grassColor = vec3(0.2, 0.65, 0.2);

void main() {
    float brightness = 1.0f;
    if (face == FACE_FRONT || face == FACE_BACK || face == FACE_LEFT || face == FACE_RIGHT){
        brightness = 0.65f;
    }
    vec3 color;
    if(face == FACE_TOP){
        color = texture(textureTop, textureCoords).rgb;
        color *= grassColor;
        color *= brightness;
    }else if(face == FACE_BOTTOM){
        color = texture(textureBottom, textureCoords).rgb * brightness;
    }else {
        color = texture(textureSide, textureCoords).rgb * brightness;
    }
    FragColor = vec4(color, 1.0);
}