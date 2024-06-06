#shader vertex
#version 330 core

layout (location = 0) in vec2 inPosition;

uniform mat4 projection;
uniform mat4 view;

void main() {
    vec4 pos = vec4(inPosition, 0.0, 1.0);
    gl_Position = projection * view * pos;
}

#shader fragment
#version 330 core

uniform vec2 windowSize;

out vec4 FragColor;

void main() {
    vec2 normalizedCoord = gl_FragCoord.xy / windowSize;
    FragColor = vec4(normalizedCoord, 0.0, 1.0);
}