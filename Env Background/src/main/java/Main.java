import Engine.Object;
import Engine.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.*;


public class Main
{
    private Window window = new Window(800,800,"Hello World");
    private ArrayList<Object2d> flappy = new ArrayList<>();
    private ArrayList<Object2d> objects = new ArrayList<>();
    private ArrayList<Object2d> flepi2 = new ArrayList<>();
    private ArrayList<Object2d> flepi3 = new ArrayList<>();
    private ArrayList<Object2d> env = new ArrayList<>();


    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();

    List<Integer> starIndex = Arrays.asList(0,3,3,1,1,4,4,2,2,0);


    private int countKakiKiriJalan = 0;
    private boolean kaki_kiri_jalan = true;

    boolean kedip = true;


    private int counterTangan = 0;
    private boolean putar_tangan = true;


    Timer timer = new Timer();
    int delay = 0;
    int interval = 20;

    Matrix4f bolapos = new Matrix4f();
    Matrix4f kakiKiri = new Matrix4f();
    Matrix4f kakibola = new Matrix4f();
    Matrix4f KakiSphere = new Matrix4f();

    public void init(){

        window.init();
        GL.createCapabilities();
        camera.setPosition(0,0,6.0f);
        glEnable(GL_DEPTH_TEST);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glDepthMask(true);
        glDepthFunc(GL_LEQUAL);
        glDepthRange(0.0f, 0.5f);

        List<ShaderProgram.ShaderModuleData> shader = Arrays.asList(
                //shaderFile lokasi menyesuaikan objectnya
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.vert"
                                , GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.frag"
                                , GL_FRAGMENT_SHADER)
        );

        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((81/255f),(158/255f),(86/255f),1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                2.5f,
                0.1f,
                3.5f,
                9
        ));


        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((236/255f),(185/255f),(104/255f),1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.1f,
                1.5f,
                3.5f,
                9
        ));

        env.get(1).translateObject(-1.2f, .8f, 0f);


        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((236/255f),(185/255f),(104/255f),1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.1f,
                1.5f,
                3.5f,
                9
        ));

        env.get(2).translateObject(1.2f, .8f, 0f);


        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((236/255f),(185/255f),(104/255f),1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                2.5f,
                1.5f,
                0.1f,
                9
        ));

        env.get(3).translateObject(0f, .8f, -1.2f);

    }


    public void loop(){
        while (window.isOpen()) {
            window.update();
//            glClearColor(153/255f,
//                    1f, 1f,
//                    1f);

            glClearColor(168/255f, 215/255f, 245/255f, 1f);
//            glClearColor(0.0f,0.0f,0.0f,0.0f);
            GL.createCapabilities();
            input();

            for (Object2d object: env)
            {
                object.draw(camera, projection);
            }

            for(Object2d object: flappy){
                object. draw(camera, projection);
            }
            for (Object2d object: objects){
                object.draw(camera,projection);
            }
            for (Object2d object: flepi2){
                object.draw(camera,projection);
            }
            for (Object2d object: flepi3){
                object.draw(camera, projection);
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


        if(window.isKeyPressed(GLFW_KEY_A))
        {

            camera.addRotation(0,-0.01f);
        }

        if(window.isKeyPressed(GLFW_KEY_D))
        {
            camera.addRotation(0,0.01f);
        }

        if(window.isKeyPressed(GLFW_KEY_U))
        {
            for (Object2d i: flappy)
            {

                i.translateObject(0f, 0f, 0.01f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_O))
        {
            for (Object2d i: flappy)
            {
                i.translateObject(0f, 0f, -0.01f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_I))
        {
            camera.moveUp(0.01f);
        }

        if(window.isKeyPressed(GLFW_KEY_K))
        {
            camera.moveDown(0.01f);
        }

        if(window.isKeyPressed(GLFW_KEY_J))
        {

            camera.moveLeft(0.01f);
        }

        if(window.isKeyPressed(GLFW_KEY_L))
        {

            camera.moveRight(0.01f);
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
        {
            camera.moveForward(0.01f);
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT_CONTROL))
        {
            camera.moveBackwards(0.01f);
            System.out.println(camera.getPosition());
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