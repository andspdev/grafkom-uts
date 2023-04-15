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
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();


    public void init(){
        window.init();
        GL.createCapabilities();
        camera.setPosition(0,0,2.0f);
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


        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.5f,0.7f,0.35f,9));
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1.0f,1.0f,1.0f,1.0f),
                new ArrayList<>(List.of(0.0f,0.15f,0.175f)), 0.37f,0.27f,0.01f,9));

        // CD Input Spaces
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1.0f,0f,1.0f,1.0f),
                new ArrayList<>(List.of(-0.11f,-0.03f,0.175f)), 0.22f,0.03f,0.01f,9));

        // Tombol bulat
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0.0f,1.0f,0.0f,1.0f),
                new ArrayList<>(List.of(0.13f,-0.03f,0.19f)), 0.02f,0.02f,0.02f,7));



        // Tanda tambah
            //Vertical
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1.0f,0f,1.0f,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.f)), 0.10f,0.03f,0.01f,9));
        flappy.get(4).translateObject(-0.12f,-0.16f,0.175f);
            //Horizontal
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1.0f,0f,1.0f,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.f)), 0.10f,0.03f,0.01f,9));
        flappy.get(5).rotateObject((float)Math.toRadians(90),0.0f,0.0f,1.0f);
        flappy.get(5).translateObject(-0.12f,-0.16f,0.175f);

        // Tombol Segitiga
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1.0f,0f,0f,1.0f),
                new ArrayList<>(List.of(0.065f,-0.16f,0.17f)), 0.06f,0.05f,0.03f,8));


        // Tombol bulat
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0.0f,1.0f,0.0f,1.0f),
                new ArrayList<>(List.of(0.13f,-0.203f,0.18f)), 0.02f,0.02f,0.02f,7));

        // Tombol bulat Merah
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1.0f,0.0f,0.0f,1.0f),
                new ArrayList<>(List.of(0.055f,-0.245f,0.18f)), 0.035f,0.035f,0.02f,7));

        // Tombol bulat (Kotak?)
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0.0f,1.0f,0.0f,1.0f),
                new ArrayList<>(List.of(-0.158f,-0.264f,0.17f)), 0.04f,0.02f,0.02f,9));

        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0.0f,1.0f,0.0f,1.0f),
                new ArrayList<>(List.of(-0.09f,-0.264f,0.17f)), 0.04f,0.02f,0.02f,9));

        // Kaki kiri
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.2f,7));
        flappy.get(11).rotateObject((float)Math.toRadians(90), 1.0f,0.0f,0.0f);
        flappy.get(11).translateObject(-0.10f,-.5f,0.05f);

        // Kaki kanan
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.2f,7));
        flappy.get(12).rotateObject((float)Math.toRadians(90), 1.0f,0.0f,0.0f);
        flappy.get(12).translateObject(0.10f,-.5f,0.05f);

        // Kuku Kiri
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
                new ArrayList<>(List.of(-.10f,-.49f,0.13f)), 0.02f,0.02f,0.08f,7));
        flappy.add(new Sphere(shader, new ArrayList<>(),  new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
                new ArrayList<>(List.of(-.10f,-.49f,0.13f)), 0.02f,0.02f,0.02f,0));
        // Kuku Kanan
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
                new ArrayList<>(List.of(.10f,-.49f,0.13f)), 0.02f,0.02f,0.08f,7));
        flappy.add(new Sphere(shader, new ArrayList<>(),  new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
                new ArrayList<>(List.of(.10f,-.49f,0.13f)), 0.02f,0.02f,0.02f,0));

        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.2f,0.2f,0.2f,7));
        flappy.get(17).rotateObject((float) Math.toRadians(90), 0.0f,1.0f,0.0f);
        flappy.get(17).translateObject(1.0f,0.0f,0.0f);


//        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
//                new ArrayList<>(List.of(0.0f,0.0f,-0.0f)), 0.1f,0.1f,0.4f,7));
//        flappy.get(0).rotateObject((float) Math.toRadians(-45), 1.0f,0f,0f);
//        flappy.get(0).translateObject(0f,0.02f,-0.35f);
//        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
//                new ArrayList<>(List.of(0.0f,0.0f,-0.22f)), 0.1f,0.1f,0.2f,7));








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
        if(window.isKeyPressed(GLFW_KEY_Q))
        {
            for (Object2d i: flappy)
            {
                i.rotateObject(0.01f, 0f, 0f, 1f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_E))
        {
            for (Object2d i: flappy)
            {
                i.rotateObject(-0.01f, 0f, 0f, 1f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_W))
        {
            for (Object2d i: flappy)
            {
                i.rotateObject(0.01f, 1f, 0f, 0f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_S))
        {
            for (Object2d i: flappy)
            {
                i.rotateObject(-0.01f, 1f, 0f, 0f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_A))
        {
            for (Object2d i: flappy)
            {
                i.rotateObject(0.01f, 0f, 1f, 0f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_D))
        {
            for (Object2d i: flappy)
            {
                i.rotateObject(-0.01f, 0f, 1f, 0f);
            }
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
            for (Object2d i: flappy)
            {
                i.translateObject(0f, 0.01f, 0f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_K))
        {
            for (Object2d i: flappy)
            {
                i.translateObject(0f, -0.01f, 0f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_J))
        {
            for (Object2d i: flappy)
            {
                i.translateObject(-0.01f, 0f, 0f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_L))
        {
            for (Object2d i: flappy)
            {
                i.translateObject(0.01f, 0f, 0f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
        {
            camera.moveForward(0.01f);
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT_CONTROL))
        {
            camera.moveBackwards(0.01f);
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