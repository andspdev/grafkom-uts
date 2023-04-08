import Engine.Object;
import Engine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.*;


public class Main {


    private Window window = new Window(800,600,"Hello World");
    private ArrayList<Object2d> flappy = new ArrayList<>();

    public void init(){
        window.init();
        GL.createCapabilities();

        List<ShaderProgram.ShaderModuleData> shader = Arrays.asList(
                //shaderFile lokasi menyesuaikan objectnya
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.vert"
                                , GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.frag"
                                , GL_FRAGMENT_SHADER)
        );

//        flappy.add(new Sphere(shader,new ArrayList<>(),
//                new Vector4f((1f),(226f/255f),(74f/255f),1.0f),
//                new ArrayList<Float>(List.of(0.0f,0.0f,0.0f)),0.5f,0.5f,0.3f,100,50,0));
//        flappy.get(0).getChildObject().add(new Circle(shader,new ArrayList<>(), new Vector4f(1.0f,0.0f,0.0f,1.0f),
//                0.2f,0.0f,0.0f,0,0.0f));

        flappy.add(new Circle(shader, new ArrayList<>(),new Vector4f(1.0f,0.0f,0.0f,1.0f),
                0.5,0.0f,0.0f,0,0.0f));
        flappy.get(0).rotateObject(0.5f,1.0f,0.0f,0.0f);


    }
    public void loop(){
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f,
                    0.0f, 0.0f,
                    0.0f);
            GL.createCapabilities();
            input();

            for(Object2d object: flappy){
                object.draw();
            }


            // Restore state
            glDisableVertexAttribArray(0);

            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public void input(){

        List<ShaderProgram.ShaderModuleData> shader = Arrays.asList(
                //shaderFile lokasi menyesuaikan objectnya
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.vert"
                                , GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.frag"
                                , GL_FRAGMENT_SHADER)
        );
        if(window.isKeyPressed(GLFW_KEY_W)){
            flappy.get(0).rotateObject(0.1f,0f,1.0f,0f);
        }
        if(window.isKeyPressed(GLFW_KEY_S)){
            flappy.get(0).rotateObject(0.1f,1.0f,0.0f,0.0f);
        }
        if(window.isKeyPressed(GLFW_KEY_A)){
            flappy.get(0).rotateObject(0.1f,0.0f,0.0f,1.0f);
        }


        if(window.getMouseInput().isLeftButtonPressed()){
            Vector2f pos = window.getMouseInput().getCurrentPos();
//            System.out.println("x : "+ pos.x + " y : "+pos.y);
            pos.x = (pos.x - (window.getWidth())/2.0f)/(window.getWidth()/2.0f);
            pos.y = (pos.y - (window.getHeight())/2.0f)/(-window.getHeight()/2.0f);
            if((!(pos.x > 1 || pos.x < -0.97)&&!(pos.y >0.97 || pos.y < -1))){
                System.out.println("x : "+ pos.x + " y : "+pos.y);

            }

        }
        else if(window.getMouseInput().isleftButtonRelease()){

        }
    }
    public void run() {

        init();
        loop();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public static void main(String[] args) {
        new Main().run();
    }
}