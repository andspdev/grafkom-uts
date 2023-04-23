import Engine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.*;


public class Main {

    private Window window = new Window(800,600,"Hello World");
    private ArrayList<Object2d> flepi2 = new ArrayList<>();
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();

    double temp = 0;
    double temp2 = 0;
    int count = 1;
    int count2 = 1;
    boolean flag = false;
    boolean flag2 = false;

    int count3 = 100;

    List<Integer> starIndex = Arrays.asList(0,5,3,4,5,2,1,5,4,1,5,3);

    public void init(){
        window.init();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        camera.setPosition(0,0,2.0f);

        List<ShaderProgram.ShaderModuleData> shader = Arrays.asList(
                //shaderFile lokasi menyesuaikan objectnya
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.vert"
                                , GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData
                        ("resources/shaders/scene.frag"
                                , GL_FRAGMENT_SHADER)
        );


//        flepi2.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
//                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.5f,0.7f,0.35f,6));

        // Badan 0
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.5f,0.7f,0.25f,0));

        // Kepala awan atas 1
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0.0f,0.55f,0.0f)),0.425f,0.5f,0.4f,0));

        // Kepala awan atas kiri 2
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.315f,0.35f,0.35f,0));

        flepi2.get(2).rotateObject((float) Math.toRadians(45), 0f, 0f, 1f);
        flepi2.get(2).translateObject(-0.375f,0.295f,0.0f);

        // Kepala awan atas kanan 3
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.275f,0.32f,0.35f,0));

        flepi2.get(3).rotateObject((float) Math.toRadians(-45), 0f, 0f, 1f);
        flepi2.get(3).translateObject(0.375f,0.275f,0.0f);

        // badan awan kiri 4
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(-0.375f,-0.2f,0.0f)),0.38f,0.34f,0.35f,0));

        // badan awan kanan 5
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.3625f,0.34f,0.35f,0));

        flepi2.get(5).rotateObject((float) Math.toRadians(-30), 0f, 0f, 1f);
        flepi2.get(5).translateObject(0.350f,-0.2f,0.0f);

        // badan awan bawah kiri 6
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(-0.325f,-0.55f,0.0f)),0.17f,0.1f,0.18f,0));

        // badan awan bawah kanan 7
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0.350f,-0.55f,0.0f)),0.17f,0.1f,0.18f,0));

        // kaki kiri 8
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(-0.145f,-0.65f,0.0f)),0.1f,0.15f,0.15f,0));

        // kaki tengah 9
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0f,-0.665f,0.0f)),0.1f,0.15f,0.15f,0));

        // kaki kanan 10
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0.145f,-0.65f,0.0f)),0.1f,0.15f,0.15f,0));

        // star 11
        flepi2.add(new Star(shader,
                new ArrayList<>(),
                new Vector4f((204f/255f),(204f/255f),0.0f,1.0f),
                starIndex,0f,0f,0.15f));

        flepi2.get(11).rotateObject((float) Math.toRadians(-20), 0f, 0f, 1f);
        flepi2.get(11).translateObject(0f,0.6f,0.4f);

        // alis kiri
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.1f,0.009f,0.715f,9));

        flepi2.get(12).rotateObject((float) Math.toRadians(-5), 0f, 0f, 1f);
        flepi2.get(12).translateObject(-0.18f,0.35f,0.0f);

        // alis kanan
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.1f,0.009f,0.715f,9));

        flepi2.get(13).rotateObject((float) Math.toRadians(5), 0f, 0f, 1f);
        flepi2.get(13).translateObject(0.18f,0.35f,0.0f);

        // mata kiri
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.05f,0.055f,0.715f,9));

        flepi2.get(14).rotateObject((float) Math.toRadians(-5), 0f, 0f, 1f);
        flepi2.get(14).translateObject(-0.18f,0.3f,0.0f);

        // mata kanan
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.05f,0.055f,0.715f,9));

        flepi2.get(15).rotateObject((float) Math.toRadians(5), 0f, 0f, 1f);
        flepi2.get(15).translateObject(0.18f,0.3f,0.0f);

        //mulut
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.259f, 0.242f, 0.260f, 1.0f),
                new ArrayList<>(List.of(0.0f,0.2f,0.3f)),0.125f,0.075f,0.04f,0));

        // badan awan bawah tengahe
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0f,-0.29f,0f)),0.37f,0.37f,0.33f,0));

        // tangan kiri
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.90f, 0.53f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.05f,0.05f,0.45f,7));

        flepi2.get(18).rotateObject((float) Math.toRadians(-90), 1f, 0f, 0f);
        flepi2.get(18).translateObject(-0.3f,0.05f,0.315f);

        // tangan kanan
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.90f, 0.53f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.05f,0.05f,0.45f,7));

        flepi2.get(19).rotateObject((float) Math.toRadians(-90), 1f, 0f, 0f);
        flepi2.get(19).translateObject(0.3f,0.05f,0.315f);

        // bahu kiri 20
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.90f, 0.53f, 0.740f, 1.0f),
                new ArrayList<>(List.of(-0.3f,0.05f,0.3f)),0.075f,0.075f,0.065f,0));

        // bahu kanan 21
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.90f, 0.53f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0.3f,0.05f,0.3f)),0.075f,0.075f,0.065f,0));

        // jari tangan kiri 22 23 24
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.90f, 0.53f, 0.740f, 1.0f),
                new ArrayList<>(List.of(-0.3f,-0.41f,0.33f)),0.015f,0.075f,0.02f,0));

        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.90f, 0.53f, 0.740f, 1.0f),
                new ArrayList<>(List.of(-0.33f,-0.41f,0.33f)),0.015f,0.075f,0.02f,0));

        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.90f, 0.53f, 0.740f, 1.0f),
                new ArrayList<>(List.of(-0.27f,-0.41f,0.33f)),0.015f,0.075f,0.02f,0));

        // jari tangan kanan 25 26 27
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.90f, 0.53f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0.3f,-0.41f,0.33f)),0.015f,0.075f,0.02f,0));

        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.90f, 0.53f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0.33f,-0.41f,0.33f)),0.015f,0.075f,0.02f,0));

        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.90f, 0.53f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0.27f,-0.41f,0.33f)),0.015f,0.075f,0.02f,0));

        // bibir cemberut hitam
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0f, 0f, 0f, 1.0f),
                new ArrayList<>(List.of(0.1f,0.2f,0.32f)),0.095f,0.1f,0.03f,10));
        flepi2.get(28).translateObject(-0.1f, -0.2f, -0.32f);
        flepi2.get(28).rotateObject((float)Math.toRadians(-90f),0f, 0f, 1f );
        flepi2.get(28).translateObject(0.13f,0.2f,0.35f);
        flepi2.get(28).translateObject(0f,0f,0.f);
        // bibir cemberut warna
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0.1175f,0.2f,0.35f)),0.07f,0.1f,0.07f,0));

        // bola
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(1f, 1f, 1f, 1.0f),
                new ArrayList<>(List.of(0.0f,0.55f,0.7f)),0.29f,0.29f,0.29f,0));

        flepi2.get(30).setFlag();
        System.out.println(flepi2.get(30).isFlag());
    }
    public void loop(){
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f,
                    0.0f, 0.0f,
                    0.0f);
            GL.createCapabilities();
            input();

            for(Object2d object: flepi2){
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
            for (Object2d i: flepi2)
            {

                i.rotateObject(0.01f, 0f, 0f, 1f);

            }
        }

        if(window.isKeyPressed(GLFW_KEY_E))
        {
            for (Object2d i: flepi2)
            {

                i.rotateObject(-0.01f, 0f, 0f, 1f);

            }
        }

        if(window.isKeyPressed(GLFW_KEY_W))
        {
            camera.addRotation(0.01f,0.0f);
//            for (Object2d i: flepi2)
//            {
//
//                i.rotateObject(0.01f, 1f, 0f, 0f);
//
//            }
        }

        if(window.isKeyPressed(GLFW_KEY_S))
        {
            camera.addRotation(-0.01f,0);
//            for (Object2d i: flepi2)
//            {
//
//                i.rotateObject(-0.01f, 1f, 0f, 0f);
//
//            }
        }




        if(window.isKeyPressed(GLFW_KEY_A))
        {

            camera.addRotation(0,-0.01f);
//
//            for (Object2d i: flepi2)
//            {
//
//
//                i.rotateObject(0.01f, 0f, 1f, 0f);
//
//            }
        }

        if(window.isKeyPressed(GLFW_KEY_D))
        {
            camera.addRotation(0,0.01f);
//            for (Object2d i: flepi2)
//            {
//
//                i.rotateObject(-0.01f, 0f, 1f, 0f);
//
//            }
        }

        if(window.isKeyPressed(GLFW_KEY_U))
        {
            for (Object2d i: flepi2)
            {

                i.translateObject(0f, 0f, 0.01f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_O))
        {
            for (Object2d i: flepi2)
            {
                i.translateObject(0f, 0f, -0.01f);
            }
        }

        if(window.isKeyPressed(GLFW_KEY_I))
        {

            camera.moveUp(0.01f);
//            for (Object2d i: flepi2)
//            {
//                i.translateObject(0f, 0.01f, 0f);
//            }
        }

        if(window.isKeyPressed(GLFW_KEY_K))
        {
            camera.moveDown(0.01f);
//            for (Object2d i: flepi2)
//            {
//                i.translateObject(0f, -0.01f, 0f);
//            }
        }

        if(window.isKeyPressed(GLFW_KEY_J))
        {

            camera.moveLeft(0.01f);
//            for (Object2d i: flepi2)
//            {
//                i.translateObject(-0.01f, 0f, 0f);
//            }
        }

        if(window.isKeyPressed(GLFW_KEY_L))
        {

            camera.moveRight(0.01f);
//            for (Object2d i: flepi2)
//            {
//
//                i.translateObject(0.01f, 0f, 0f);
//            }
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


        // animasi 1 hidup kaya (ubur-ubur)
        if(window.isKeyPressed(GLFW_KEY_1))
        {
            flag = true;
        }

        if(window.isKeyReleased(GLFW_KEY_1))
        {
            if (flag) {
                System.out.println(temp);
                for (Object2d i : flepi2) {
                    if (count < 0) {
                        i.translateObject(0f, -0.01f, 0f);
                    } else {
                        i.translateObject(0f, 0.01f, 0f);

                    }
                }
                temp += count;
                if (temp > 45) {
                    count *= -1;
                } else if (temp < -45) {
                    count *= -1;
                }
            }
        }

        //animasi lempar bola

        if(window.isKeyPressed(GLFW_KEY_2))
        {
            if (count2 > 0) {
                Vector3f posisi = flepi2.get(18).updateCenterPoint();
                flepi2.get(18).translateObject(-posisi.x, -posisi.y, -posisi.z);
                flepi2.get(22).translateObject(-posisi.x, -posisi.y, -posisi.z);
                flepi2.get(23).translateObject(-posisi.x, -posisi.y, -posisi.z);
                flepi2.get(24).translateObject(-posisi.x, -posisi.y, -posisi.z);
                flepi2.get(19).translateObject(-posisi.x, -posisi.y, -posisi.z);
                flepi2.get(25).translateObject(-posisi.x, -posisi.y, -posisi.z);
                flepi2.get(26).translateObject(-posisi.x, -posisi.y, -posisi.z);
                flepi2.get(27).translateObject(-posisi.x, -posisi.y, -posisi.z);

                flepi2.get(18).rotateObject((float) Math.toRadians(-5f), 1.0f, 0.0f, .0f);
                flepi2.get(22).rotateObject((float) Math.toRadians(-5f), 1.0f, 0.0f, 0f);
                flepi2.get(23).rotateObject((float) Math.toRadians(-5f), 1.0f, 0.0f, 0.0f);
                flepi2.get(24).rotateObject((float) Math.toRadians(-5f), 1.0f, 0.0f, 0.0f);
                flepi2.get(19).rotateObject((float) Math.toRadians(-5f), 1.0f, 0.0f, .0f);
                flepi2.get(25).rotateObject((float) Math.toRadians(-5f), 1.0f, 0.0f, 0f);
                flepi2.get(26).rotateObject((float) Math.toRadians(-5f), 1.0f, 0.0f, 0.0f);
                flepi2.get(27).rotateObject((float) Math.toRadians(-5f), 1.0f, 0.0f, 0.0f);

                flepi2.get(18).translateObject(posisi.x, posisi.y, posisi.z);
                flepi2.get(22).translateObject(posisi.x, posisi.y, posisi.z);
                flepi2.get(23).translateObject(posisi.x, posisi.y, posisi.z);
                flepi2.get(24).translateObject(posisi.x, posisi.y, posisi.z);
                flepi2.get(19).translateObject(posisi.x, posisi.y, posisi.z);
                flepi2.get(25).translateObject(posisi.x, posisi.y, posisi.z);
                flepi2.get(26).translateObject(posisi.x, posisi.y, posisi.z);
                flepi2.get(27).translateObject(posisi.x, posisi.y, posisi.z);
            }
            temp2 += count2;
            System.out.println("temp" + temp2);
            System.out.println("count" + count2);
            if (temp2 > 27) {
                count2 *= 0;
                count3 *= -1;
            }
            if (count3 == -100)
            {
                if(!flepi2.get(30).isFlag()) {
                    flepi2.get(30).setFlag();
                    count3*=-1;
                }

            }
//            else if (temp < -45) {
//                count *= -1;
//            }
            if (count2 <= 28 && flepi2.get(30).isFlag()) {
                System.out.println("masuk");
                if (temp2 > 0) {
                    Vector3f posisi = flepi2.get(18).updateCenterPoint();
                    flepi2.get(18).translateObject(-posisi.x, -posisi.y, -posisi.z);
                    flepi2.get(22).translateObject(-posisi.x, -posisi.y, -posisi.z);
                    flepi2.get(23).translateObject(-posisi.x, -posisi.y, -posisi.z);
                    flepi2.get(24).translateObject(-posisi.x, -posisi.y, -posisi.z);
                    flepi2.get(19).translateObject(-posisi.x, -posisi.y, -posisi.z);
                    flepi2.get(25).translateObject(-posisi.x, -posisi.y, -posisi.z);
                    flepi2.get(26).translateObject(-posisi.x, -posisi.y, -posisi.z);
                    flepi2.get(27).translateObject(-posisi.x, -posisi.y, -posisi.z);

                    flepi2.get(18).rotateObject((float) Math.toRadians(5f), 1.0f, 0.0f, .0f);
                    flepi2.get(22).rotateObject((float) Math.toRadians(5f), 1.0f, 0.0f, 0f);
                    flepi2.get(23).rotateObject((float) Math.toRadians(5f), 1.0f, 0.0f, 0.0f);
                    flepi2.get(24).rotateObject((float) Math.toRadians(5f), 1.0f, 0.0f, 0.0f);
                    flepi2.get(19).rotateObject((float) Math.toRadians(5f), 1.0f, 0.0f, .0f);
                    flepi2.get(25).rotateObject((float) Math.toRadians(5f), 1.0f, 0.0f, 0f);
                    flepi2.get(26).rotateObject((float) Math.toRadians(5f), 1.0f, 0.0f, 0.0f);
                    flepi2.get(27).rotateObject((float) Math.toRadians(5f), 1.0f, 0.0f, 0.0f);

                    flepi2.get(18).translateObject(posisi.x, posisi.y, posisi.z);
                    flepi2.get(22).translateObject(posisi.x, posisi.y, posisi.z);
                    flepi2.get(23).translateObject(posisi.x, posisi.y, posisi.z);
                    flepi2.get(24).translateObject(posisi.x, posisi.y, posisi.z);
                    flepi2.get(19).translateObject(posisi.x, posisi.y, posisi.z);
                    flepi2.get(25).translateObject(posisi.x, posisi.y, posisi.z);
                    flepi2.get(26).translateObject(posisi.x, posisi.y, posisi.z);
                    flepi2.get(27).translateObject(posisi.x, posisi.y, posisi.z);


                }
                temp2 -= 1;
                if (temp2 > -75) {
                    flepi2.get(30).translateObject(0f, -0.01f, 0.02f);
                }

            }
            if (temp2 == -75 && flepi2.get(30).isFlag()){
                System.out.println("masuk2");
                temp2 = 0;
                flepi2.get(30).setFlag();
                System.out.println(flepi2.get(30).isFlag());
            }
//            flepi2.get(30).translateObject(0f,-0.01f,0.02f);

//
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