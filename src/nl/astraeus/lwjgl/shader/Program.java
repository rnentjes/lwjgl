package nl.astraeus.lwjgl.shader;

import nl.astraeus.lwjgl.util.IOUtil;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;

/**
 * User: rnentjes
 * Date: 5/11/13
 * Time: 12:15 PM
 */
public class Program {

    private int vertex;
    private int fragment;

    private int program;

    public Program(String name) {
        vertex = getShader("shaders/"+name+".vert", GL20.GL_VERTEX_SHADER);
        fragment = getShader("shaders/"+name+".frag", GL20.GL_FRAGMENT_SHADER);

        if (vertex == 0) {
            throw new IllegalStateException("Vertex shader source 'shaders/"+name+".vert' not found!");
        }

        if (fragment == 0) {
            throw new IllegalStateException("Vertex shader source 'shaders/"+name+".frag' not found!");
        }

        program = GL20.glCreateProgram();

        GL20.glAttachShader(program, vertex);
        GL20.glAttachShader(program, fragment);

        GL20.glLinkProgram(program);
    }

    private int getShader(String filename, int type) {
        int result = 0;
        File file = new File(filename);

        String source = IOUtil.loadFile(file);

        if (source != null) {
            result = GL20.glCreateShader(type);
            GL20.glShaderSource(result, source);
            GL20.glCompileShader(result);
        }

        return result;
    }
    public int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        return shaderID;
    }

    public void bind() {

    }

    private void fetchAttributes () {
        int numberOfAttributes = GL20.glGetProgrami(program, GL20.GL_ACTIVE_ATTRIBUTES);
        IntBuffer params = IntBuffer.allocate(numberOfAttributes);
        params.clear();
        GL20.glGetProgram(program, GL20.GL_ACTIVE_ATTRIBUTES, params);

        int numAttributes = params.get(0);
/*
        attributeNames = new String[numAttributes];

        for (int i = 0; i < numAttributes; i++) {
            params.clear();
            params.put(0, 1);
            type.clear();
            String name = Gdx.gl20.glGetActiveAttrib(program, i, params, type);
            int location = Gdx.gl20.glGetAttribLocation(program, name);
            attributes.put(name, location);
            attributeTypes.put(name, type.get(0));
            attributeSizes.put(name, params.get(0));
            attributeNames[i] = name;
        }*/
    }

    /** @param name the name of the attribute
     * @return whether the attribute is available in the shader */
    public boolean hasAttribute (String name) {
        return false; //attributes.containsKey(name);
    }


}
