#version 330

const int MAX_POINT_LIGHTS = 4;

in vec2 textCoord0;
in vec3 normal0;
in vec3 worldPos0;

out vec4 fragColor;

struct BaseLight
{
    vec3 color;
    float intensity;
};

struct DirectionalLight
{
    BaseLight base;
    vec3 direction;
};

struct Attenuation
{
    float constant;
    float linear;
    float exponent;
};

struct PointLight
{
    BaseLight base;
    Attenuation atten;
    vec3 position;
    float range;
};

uniform vec3 baseColor;
uniform vec3 ambientLight;
uniform sampler2D sampler;
uniform DirectionalLight directionalLight;
uniform float specularIntensity;
uniform float specularPower;
uniform vec3 eyePos;
uniform PointLight pointLights[MAX_POINT_LIGHTS];


vec4 calcLight(BaseLight base, vec3 direction, vec3 normal)
{
    float diffuseFactor = dot(normal, -direction);
    vec4 diffuseColor = vec4(0,0,0,0);

    if(diffuseFactor > 0)
    {
        diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;

        vec3 directionToEye = normalize(eyePos - worldPos0);
        vec3 reflectDirection = normalize(reflect(direction, normal));

        float specularFactor = dot(directionToEye, reflectDirection);
        specularFactor = pow(specularFactor, specularPower);
    
        if(specularFactor > 0)
        {
            specularColor = vec4(base.color, 1.0) * specularIntensity * specularFactor;
        }
    }

    return diffuseColor + specularColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal)
{
	return calcLight(directionlLight.base, -directionalLight.direction, normal);
}

vec4 calcPointLights(PointLight pointLight, vec3 normal)
{
    vec3 lightDirection = worldPos0 - pointLight.position;
    float distanceToPoint = length(lightDirection);

    if(distanceToPoint > pointLight.range)
        return vec4(0,0,0,0);
    
    lightDirection = normalize(lightDirection);

    vec4 color = calcLight(pointLight.base, lightDirection, normal);

    float attenuation = pointLight.atten.constant + 
                        pointLight.atten * distanceToPoint + 
                        pointLight.atten.exponent * distanceToPoint + 
                        0.0001;
    
    return color / attenuation;
}

void main()
{
    vec4 totalLight = vec4(ambientLight,1);
    vec4 color = vec4(baseColor, 1);
    vec4 textureColor = texture(sampler, textCoord0.xy)

    if(textureColor != vec4(0,0,0,0))
        color *= textreColor;

    vec3 normal = normalize(normal0);

    totalLight += calcDirectionalLightdirectionalLight, normal);

    for(int i=0; i < MAX_POINT_LIGHTS; i++)
        if(pointLights[i].base.intensity > 0)
            totalLight += calcPointLight(pointLights[i], normal);

    fragColor = color * totalLight; 
}