#version 400

uniform mat4 m_transform;
uniform mat4 m_view;

in vec2 pass_texCoord;

layout(location = 0) out vec4 out_color;
void main() {
	out_color = vec4(0, 1, 1, 1);
}