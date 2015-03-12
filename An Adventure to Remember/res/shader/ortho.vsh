#version 400

uniform mat4 m_transform;
uniform mat4 m_view;

layout(location = 0) in vec2 in_position;
layout(location = 1) in vec2 in_texCoord;

out vec2 pass_texCoord;
void main() {
	gl_Position = m_transform * m_view * vec4(in_position, 0, 1);
	pass_texCoord = in_texCoord;
}