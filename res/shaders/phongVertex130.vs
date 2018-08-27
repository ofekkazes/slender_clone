#version 130

attribute vec3 position;
attribute vec2 textCoord;
attribute vec3 normal;

out vec2 textCoord0;
out vec3 normal0;
out vec3 worldPos0;

uniform mat4 transform;
uniform mat4 transformProjected;

void main()
{
    gl_Position = transformProjected * vec4(position, 1.0);
    textCoord0 = textCoord;
    normal0 = (transform * vec4(normal, 0.0)).xyz;
    worldPos0 = (transform * vec4(position, 1.0)).xyz;
}