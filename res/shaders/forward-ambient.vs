#version 120

attribute vec3 position;
attribute vec2 textCoord;

varying vec2 textCoord0;

uniform mat4 MVP;

void main()
{
	gl_Position = MVP * vec4(position, 1.0);
    textCoord0 = textCoord;
}