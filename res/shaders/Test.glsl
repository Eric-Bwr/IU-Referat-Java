#shader vertex
#version 330 core

layout (location = 0) in vec2 position;

void main() {
    gl_Position = vec4(position, 0.0, 1.0);
}

#shader fragment
#version 330 core

const vec2 windowSize = vec2(800, 600);

out vec4 FragColor;

void main() {
    vec2 normalizedCoord = gl_FragCoord.xy / windowSize;
    FragColor = vec4(normalizedCoord, 0.0, 1.0);
}