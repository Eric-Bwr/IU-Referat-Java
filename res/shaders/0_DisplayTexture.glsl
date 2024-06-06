#shader vertex
#version 330 core

layout (location = 0) in vec2 inPosition;

out vec2 textureCoords;

void main() {
    textureCoords = (inPosition + 1.0) / 2.0;
    gl_Position = vec4(inPosition, 0.0, 1.0);
}

#shader fragment
#version 330 core

in vec2 textureCoords;

uniform sampler2D image;

out vec4 FragColor;

void main() {
    vec3 color = texture(image, textureCoords).rgb;
    FragColor = vec4(color, 1.0);
}