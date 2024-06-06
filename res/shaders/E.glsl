#shader vertex
#version 330 core

layout (location = 0) in vec3 inPosition;
layout (location = 1) in float inFace;

uniform mat4 projection;
uniform mat4 view;

out float face;

void main() {
    face = inFace;
    vec4 pos = vec4(inPosition, 1.0);
    gl_Position = projection * view * pos;
}

#shader fragment
#version 330 core

in float face;

out vec4 FragColor;

void main() {
    vec3 color;
    if (face == 1.0){
        color = vec3(1.0, 0.0, 0.0);
    }else if(face == 2.0){
        color = vec3(0.0, 1.0, 0.0);
    }else if(face == 3.0){
        color = vec3(0.0, 0.0, 1.0);
    }else if(face == 4.0){
        color = vec3(1.0, 1.0, 0.0);
    }else if(face == 5.0){
        color = vec3(0.0, 1.0, 1.0);
    }else if(face == 6.0){
        color = vec3(1.0, 0.0, 1.0);
    }
    FragColor = vec4(color, 1.0);
}