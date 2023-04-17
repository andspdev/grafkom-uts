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


public class Main {


    private Window window = new Window(800,600,"Hello World");
    private ArrayList<Object2d> flappy = new ArrayList<>();
    private ArrayList<Object2d> objects = new ArrayList<>();
    private ArrayList<Object2d> flepi2 = new ArrayList<>();
    private ArrayList<Object2d> flepi3 = new ArrayList<>();
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();
    Integer counterkaki = 0;
    Integer counterNendang = 0;
    Boolean boolNendang = true;
    Boolean picker = true;
    Boolean nendangBalik = false;

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
        Vector4f primary = new Vector4f((94/255f),(167/255f),(151/255f),1f);


        flappy.add(new Sphere(shader, new ArrayList<>(),  primary,
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.5f,0.7f,0.35f,9));
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(192/255f,255/255f,204/255f,1.0f),
                new ArrayList<>(List.of(0.0f,0.15f,0.175f)), 0.37f,0.27f,0.01f,9));
        // CD Input Spaces
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(24/255f,58/255f,49/255f,1.0f),
                new ArrayList<>(List.of(-.11f,-.03f,0.175f)), 0.22f,0.03f,0.01f,9));

        // Tombol bulat
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(30/255f,5/255f,171/255f,1.0f),
                new ArrayList<>(List.of(.13f,-.03f,.19f)), 0.02f,0.02f,0.02f,7));

        // Tanda tambah
            //Vertical
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(235/255f,202/255f,42/255f,1.0f),
                new ArrayList<>(List.of(-0.12f,-.16f,.175f)), 0.10f,0.03f,0.01f,9));
            //Horizontal
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(235/255f,202/255f,42/255f,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.f)), 0.10f,0.03f,0.01f,9));
        flappy.get(5).rotateObject((float)Math.toRadians(90),0.0f,0.0f,1.0f);
        flappy.get(5).translateObject(-0.12f,-0.16f,0.175f);

        // Tombol Segitiga
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0f,205/255f,222/255f,1.0f),
                new ArrayList<>(List.of(.065f,-.16f,.17f)), 0.06f,0.05f,0.03f,8));
        // Tombol bulat
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0.0f,232/255f,49/255f,1.0f),
                new ArrayList<>(List.of(.13f,-.203f,.18f)), 0.02f,0.02f,0.02f,7));

        // Tombol bulat Merah
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(238/255f,0.0f,92/255f,1.0f),
                new ArrayList<>(List.of(.055f,-.245f,.18f)), 0.035f,0.035f,0.02f,7));

        // Tombol bulat (Kotak?)
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(28/255f,6/255f,173/255f,1.0f),
                new ArrayList<>(List.of(-.158f,-.264f,.17f)), 0.04f,0.02f,0.02f,9));

        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(28/255f,6/255f,173/255f,1.0f),
                new ArrayList<>(List.of(-.09f,-.264f,.17f)), 0.04f,0.02f,0.02f,9));

        // Kaki kiri
        flappy.add(new Sphere(shader, new ArrayList<>(), primary,
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.2f,7));
        flappy.get(11).rotateObject((float)Math.toRadians(-90), 1.0f,0.0f,0.0f);
        flappy.get(11).translateObject(-.10f,-.3f,.05f);

        // Kaki kanan
        flappy.add(new Sphere(shader, new ArrayList<>(), primary,
                new ArrayList<>(List.of(.0f,0.0f,0.0f)), 0.02f,0.02f,0.2f,7));
        flappy.get(12).rotateObject((float)Math.toRadians(90), 1.0f,0.0f,0.0f);
        flappy.get(12).translateObject(.10f,-.5f,.05f);

        // Kuku Kiri
        flappy.add(new Sphere(shader, new ArrayList<>(), primary,
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.08f,7));
        flappy.get(13).translateObject(-.10f,-.485f,.13f);

        flappy.add(new Sphere(shader, new ArrayList<>(),  primary,
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.02f,0));
        flappy.get(14).translateObject(-.10f,-.485f,.13f);


        // Kuku Kanan
        flappy.add(new Sphere(shader, new ArrayList<>(), primary,
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.08f,7));
        flappy.get(15).translateObject(.10f,-.485f,.13f);
        flappy.add(new Sphere(shader, new ArrayList<>(),  primary,
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.02f,0));
        flappy.get(16).translateObject(.10f,-.485f,.13f);
        for(int i = 11; i<=16; i++){
            flappy.get(i).translateObject(.0f,-.040f,0f);
        }
        // Tangan kanan
        flappy.add(new Sphere(shader, new ArrayList<>(), primary,
                new ArrayList<>(List.of(.0f,.0f,.0f)), 0.02f,0.02f,0.07f,7));
        flappy.get(17).rotateObject((float) Math.toRadians(90), 0.0f,1.0f,0.0f);
        flappy.get(17).translateObject(.32f,.0f,.0f);

        flappy.add(new Sphere(shader, new ArrayList<>(), primary, new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.02f,0.02f,0.1f,7));
        flappy.get(18).rotateObject((float) Math.toRadians(-180), 1.0f,0.0f,.0f);

        flappy.get(18).translateObject(.30f,0.0f,0.0f);
        flappy.add(new Sphere(shader, new ArrayList<>(),  primary,
                new ArrayList<>(List.of(.0f,.0f,0.0f)), 0.02f,0.02f,0.02f,0));
        flappy.get(19).translateObject(.30f,0.0f,.0f);
        for(int  i = 18; i <= 19; i++){
            flappy.get(i).translateObject(-0.01f,0f,0f);
        }
        flappy.add(new Sphere(shader, new ArrayList<>(), primary, new ArrayList<>(List.of(.29f,-.0f,.1f)), 0.02f,0.02f,0.02f,0));
        for(int i = 18; i <= 20; i++){
            flappy.get(i).translateObject(0.02f,0f,0f);
        }
        // Tangan Kiri
        flappy.add(new Sphere(shader, new ArrayList<>(), primary,
                new ArrayList<>(List.of(.0f,.0f,.0f)), 0.02f,0.02f,0.07f,7));
        flappy.get(21).rotateObject((float) Math.toRadians(-90), 0.0f,1.0f,0.0f);
        flappy.get(21).translateObject(-.32f,.0f,.0f);
        flappy.add(new Sphere(shader, new ArrayList<>(), primary,
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.02f,0.02f,0.02f,0));
        flappy.get(22).translateObject(-.32f,.0f,.0f);

        flappy.add(new Sphere(shader, new ArrayList<>(), primary, new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.02f,0.02f,0.1f,7));
        flappy.get(23).rotateObject((float) Math.toRadians(-90), 1.0f,0.0f,.0f);

        flappy.get(23).translateObject(-.32f,0.0f,0.0f);

        flappy.add(new Sphere(shader, new ArrayList<>(), primary,
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.02f,0));
        flappy.get(24).translateObject(-.32f,-0.1f,0.0f);


//        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
//                new ArrayList<>(List.of(0.0f,0.0f,-0.0f)), 0.1f,0.1f,0.4f,7));
//        flappy.get(0).rotateObject((float) Math.toRadians(-45), 1.0f,0f,0f);
//        flappy.get(0).translateObject(0f,0.02f,-0.35f);
//        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
//                new ArrayList<>(List.of(0.0f,0.0f,-0.22f)), 0.1f,0.1f,0.2f,7));



        flappy.add(new Sphere(shader, new ArrayList<>(), primary,
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.07f,0.07f,0.07f,0));
        flappy.get(25).translateObject(-.10f,-0.53f,0.24f);


       flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0.0f,0.0f,0.0f,1.0f),
               new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.01f,0.01f,0.02f,7));
       flappy.get(26).translateObject(-0.08f,0.2f,0.185f);
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0.0f,0.0f,0.0f,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.01f,0.01f,0.02f,7));
        flappy.get(27).translateObject(0.08f,0.2f,0.185f);
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(153/255f,76/255f,0f,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.01f,0.01f,0.02f,11));
        flappy.get(28).rotateObject((float)Math.toRadians(-90),1.0f,0.0f,0.0f);
        flappy.get(28).translateObject(.31f,0.0f,0.105f);
        flappy.add(new Sphere(shader, new ArrayList<>(), new Vector4f(255/255f,51/255f,153/255f,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.015f,0.015f,0.01f,0 ));
        flappy.get(29).translateObject(.31f,.05f,.105f);




        // Badan Biru
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(0f, (128/255f), (255/255f), 1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.4f,
                0.4f,
                0.5f,
                7)
        );
        objects.get(0).rotateObject((float) Math.toRadians(90), 1f,0f, 0f);

        // Celana Biru Tua
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((77/255f), (106/255f), (199/255f), 1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.4f,
                0.4f,
                0.2f,
                7)
        );
        objects.get(1).rotateObject((float) Math.toRadians(90), 1f,0f, 0f);
        objects.get(1).translateObject(0f, -0.2f, 0f);

        // Kepala Putih
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1, 1, 1, 1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.4f,
                0.4f,
                0.3f,
                7)
        );
        objects.get(2).rotateObject((float) Math.toRadians(90), 1f,0f, 0f);
        objects.get(2).translateObject(0f, 0.505f, 0f);

        // Kepala Putih (Bulat)
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1, 1, 1, 1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.402f,
                0.402f,
                0.402f,
                0)
        );

        objects.get(3).translateObject(0f, 0.75f, 0f);


        // Kaki Biru Kecil (Kanan)
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((77/255f), (106/255f), (199/255f), 1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.04f,
                0.04f,
                0.13f,
                7)
        );
        objects.get(4).rotateObject((float) Math.toRadians(-90), 1f,0f, 0f);
        objects.get(4).translateObject(0.17f, -0.2f, 0f);

        // Kaki Biru Kecil (Bawah Kanan)
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((239/255f), (223/255f), (222/255f), 1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.04f,
                0.04f,
                0.368f,
                7)
        );
        objects.get(5).rotateObject((float) Math.toRadians(-90), 1f,0f, 0f);
        objects.get(5).translateObject(0.17f, -0.342f, 0f);


        // Kaki Biru Kecil (Kiri)
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((77/255f), (106/255f), (199/255f), 1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.04f,
                0.04f,
                0.15f,
                7)
        );
        objects.get(6).rotateObject((float) Math.toRadians(-90), 1f,0f, 0f);
        objects.get(6).translateObject(-0.17f, -0.2f, 0f);


        // Kaki Biru Kecil (Bawah Kiri)
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((239/255f), (223/255f), (222/255f), 1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.04f,
                0.04f,
                0.368f,
                7)
        );

        objects.get(7).rotateObject((float) Math.toRadians(-90), 1f,0f, 0f);
        objects.get(7).translateObject(-0.17f, -0.342f, 0f);


        // Kaki Biru Kecil Bulat (Bawah Kanan)
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((239/255f), (223/255f), (222/255f), 1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.043f,
                0.04f,
                0.08f,
                0)
        );

        objects.get(8).translateObject(0.17f, -0.7f, 0.04f);

        // Kaki Biru Kecil Bulat (Bawah Kiri)
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((239/255f), (223/255f), (222/255f), 1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.043f,
                0.04f,
                0.08f,
                0)
        );

        objects.get(9).translateObject(-0.17f, -0.7f, 0.04f);


        // Telinga Kiri
        objects.add(new Sphere(
                        shader,
                        new ArrayList<>(),
//                new Vector4f(1, 1, 1,1.0f),
                        new Vector4f(1, 1, 1,1.0f),
                        new ArrayList<>(List.of(0f, 0f, 0f)),
                        0.15f,
                        0.15f,
                        0.25f,
                        11)
        );

        objects.get(10).rotateObject((float) Math.toRadians(90), 1f, 0f, 0f);
        objects.get(10).rotateObject((float) Math.toRadians(30), 0f, 0f, 1f);
        objects.get(10).translateObject(-0.9f, 3f, 0f);
        objects.get(10).scaleObject(0.4f, 0.4f, 0.4f);


        // Telinga Kanan
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(1, 1, 1,1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.15f,
                0.15f,
                0.25f,
                11)
        );

        objects.get(11).rotateObject((float) Math.toRadians(90), 1f, 0f, 0f);
        objects.get(11).rotateObject((float) Math.toRadians(-30), 0f, 0f, 1f);
        objects.get(11).translateObject(0.9f, 3f, 0f);
        objects.get(11).scaleObject(0.4f, 0.4f, 0.4f);


        // Wajah
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((239/255f), (223/255f), (222/255f),1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.25f,
                0.2f,
                0.09f,
                7)
        );

        objects.get(12).translateObject(0f, 0.7f, 0.538f);
        objects.get(12).rotateObject((float) Math.toRadians(-10), 1f, 0f, 0f);

        // Mata Kanan
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(0, 0, 0,1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.2f,
                0.2f,
                0.2f,
                0)
        );

        objects.get(13).translateObject(1.2f, 7.6f, 5.42f);
        objects.get(13).rotateObject((float) Math.toRadians(-10), 1f, 0f, 0f);
        objects.get(13).scaleObject(0.1f, 0.1f, 0.1f);


        // Mata Kiri
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(0, 0, 0,1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.2f,
                0.2f,
                0.2f,
                0)
        );

        objects.get(14).translateObject(-1.2f, 7.6f, 5.42f);
        objects.get(14).rotateObject((float) Math.toRadians(-10), 1f, 0f, 0f);
        objects.get(14).scaleObject(0.1f, 0.1f, 0.1f);


        // Mulut
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((164/255f), (109/255f), (125/255f),1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.48f,
                0.4f,
                0.1f,
                10)
        );

        objects.get(15).rotateObject((float) Math.toRadians(-9), 1f, 0f, 0f);
        objects.get(15).translateObject(0f, 2.48f, 1.5f);
        objects.get(15).scaleObject(0.3f, 0.3f, 0.3f);



        // Lidah
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((239/255f), (223/255f), (222/255f),1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.18f,
                0.085f,
                0.09f,
                7)
        );

        objects.get(16).translateObject(0f, 1.9f, 1.95f);
        objects.get(16).rotateObject((float) Math.toRadians(-10), 1f, 0f, 0f);
        objects.get(16).scaleObject(0.3f, 0.3f, 0.3f);


        // Tas Hijau
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((93/255f), (152/255f), (55/255f),1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.44f,
                0.52f,
                0.047f,
                9)
        );

        objects.get(17).translateObject(0f, 0.35f, -.425f);


        // Tas Hijau Dalam
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((150/255f), (188/255f), (143/255f),1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.24f,
                0.32f,
                0.045f,
                9)
        );

        objects.get(18).translateObject(0f, 0.35f, -.4695f);



        // Tangan Kanan
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((166/255f), (221/255f), (241/255f),1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.1f,
                0.1f,
                0.3f,
                7
        ));

        objects.get(19).rotateObject((float) Math.toRadians(90), 0.0f,1.0f,0.0f);
        objects.get(19).translateObject(1.6f,1f,0f);
        objects.get(19).scaleObject(0.3f, .3f, .3f);


        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((239/255f), (223/255f), (222/255f),1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.1f,
                0.1f,
                0.6f,
                7
        ));

        objects.get(20).rotateObject((float) Math.toRadians(-90), 0.0f,1.0f,0.0f);
        objects.get(20).rotateObject((float)Math.toRadians(-90), 0.0f,0.0f,1.0f);
        objects.get(20).translateObject(1.6f,1f,0f);
        objects.get(20).scaleObject(0.3f, .3f, .3f);

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((166/255f), (221/255f), (241/255f),1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.1f,
                0.1f,
                0.1f,
                0
        ));

        objects.get(21).translateObject(1.6f,1f,0f);
        objects.get(21).scaleObject(0.3f, .3f, .3f);

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((239/255f), (223/255f), (222/255f),0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.1f,
                0.1f,
                0.1f,
                0
        ));

        objects.get(22).translateObject(1.6f,0.4f,0f);
        objects.get(22).scaleObject(0.3f, .3f, .3f);
        // End Tangan




        // Tangan Kiri
        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((166/255f), (221/255f), (241/255f),1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.1f,
                0.1f,
                0.3f,
                7
        ));

        objects.get(23).rotateObject((float) Math.toRadians(90), 0.0f,1.0f,0.0f);
        objects.get(23).translateObject(-1.32f,1f,0f);
        objects.get(23).scaleObject(0.3f, .3f, .3f);


        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((239/255f), (223/255f), (222/255f),1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.1f,
                0.1f,
                0.6f,
                7
        ));

        objects.get(24).rotateObject((float) Math.toRadians(-90), 0.0f,1.0f,0.0f);
        objects.get(24).rotateObject((float)Math.toRadians(-90), 0.0f,0.0f,1.0f);
        objects.get(24).translateObject(-1.6f,1f,0f);
        objects.get(24).scaleObject(0.3f, .3f, .3f);

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((166/255f), (221/255f), (241/255f),1.0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.1f,
                0.1f,
                0.1f,
                0
        ));

        objects.get(25).translateObject(-1.6f,1f,0f);
        objects.get(25).scaleObject(0.3f, .3f, .3f);

        objects.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((239/255f), (223/255f), (222/255f),0f),
                new ArrayList<>(List.of(0f, 0f, 0f)),
                0.1f,
                0.1f,
                0.1f,
                0
        ));

        objects.get(26).translateObject(-1.6f,0.4f,0f);
        objects.get(26).scaleObject(0.3f, .3f, .3f);
        // End Tangan

        for (int i = 0; i< flappy.size(); i++){
            flappy.get(i).translateObject(-1.9f,0.0f,0.0f);
        }
        KakiSphere = flappy.get(14).getMatrix();
        kakibola = flappy.get(13).getMatrix();
        kakiKiri = flappy.get(11).getMatrix();
        bolapos = flappy.get(25).getMatrix();
        Timer timer = new Timer();
        int delay = 1400;
        int interval = 1600;

        TimerTask task = new TimerTask()
        {
            public void run()
            {
                if (kedip) {
                    Vector3f mata_kanan = objects.get(13).getMatrix().transformPosition(new Vector3f(0.0f, 0.0f, 0.0f));
                    objects.get(13).translateObject(-mata_kanan.x, -mata_kanan.y, -mata_kanan.z);
                    objects.get(13).scaleObject(9 / 10f, .3f, 9 / 10f);

                    objects.get(13).translateObject(mata_kanan.x, mata_kanan.y, mata_kanan.z);


                    Vector3f mata_kiri = objects.get(14).getMatrix().transformPosition(new Vector3f(0.0f, 0.0f, 0.0f));
                    objects.get(14).translateObject(-mata_kiri.x, -mata_kiri.y, -mata_kiri.z);
                    objects.get(14).scaleObject(9 / 10f, .3f, 9 / 10f);
                    objects.get(14).translateObject(mata_kiri.x, mata_kiri.y, mata_kiri.z);
                    kedip = false;
                }
                else
                {
                    Vector3f mata_kanan = objects.get(13).getMatrix().transformPosition(new Vector3f(0.0f,0.0f,0.0f));
                    objects.get(13).translateObject(-mata_kanan.x, -mata_kanan.y,-mata_kanan.z);
                    objects.get(13).scaleObject(10/9f, 10/3f, 10/9f);
                    objects.get(13).translateObject(mata_kanan.x,mata_kanan.y,mata_kanan.z);

                    Vector3f mata_kiri = objects.get(14).getMatrix().transformPosition(new Vector3f(0.0f,0.0f,0.0f));
                    objects.get(14).translateObject(-mata_kiri.x, -mata_kiri.y,-mata_kiri.z);
                    objects.get(14).scaleObject(10/9f, 10/3f, 10/9f);
                    objects.get(14).translateObject(mata_kiri.x,mata_kiri.y,mata_kiri.z);
                    kedip = true;
                }
            }
        };

        timer.schedule(task, delay, interval);



//        flepi2.add(new Sphere(shader, new ArrayList<>(), new Vector4f((32/255f),(201/255f),(184/255f),1.0f),
//                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.5f,0.7f,0.35f,6));

        // Badan 0
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.795f, 0.328f, 0.820f, 1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.5f,0.7f,0.3f,0));

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
                starIndex,0f,0f,0.20f));

        flepi2.get(11).rotateObject((float) Math.toRadians(-20), 0f, 0f, 1f);
        flepi2.get(11).scaleObject(0.5f,0.5f,0.5f);
        flepi2.get(11).translateObject(0f,0.67f,.38f);


        // alis kiri
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.1f,0.009f,0.415f,9));

        flepi2.get(12).rotateObject((float) Math.toRadians(-5), 0f, 0f, 1f);
        flepi2.get(12).translateObject(-0.18f,0.35f,0.15f);

        // alis kanan
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.1f,0.009f,0.415f,9));

        flepi2.get(13).rotateObject((float) Math.toRadians(5), 0f, 0f, 1f);
        flepi2.get(13).translateObject(0.18f,0.35f,0.15f);

        // mata kiri
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.05f,0.055f,0.415f,9));

        flepi2.get(14).rotateObject((float) Math.toRadians(-5), 0f, 0f, 1f);
        flepi2.get(14).translateObject(-0.18f,0.3f,0.15f);

        // mata kanan
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.05f,0.055f,0.415f,9));

        flepi2.get(15).rotateObject((float) Math.toRadians(5), 0f, 0f, 1f);
        flepi2.get(15).translateObject(0.18f,0.3f,0.15f);

        //mulut
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.259f, 0.242f, 0.260f, 1.0f),
                new ArrayList<>(List.of(0.0f,0.2f,0.315f)),0.125f,0.075f,0.05f,0));

        // badan awan bawah tengahe
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0.724f, 0.429f, 0.740f, 1.0f),
                new ArrayList<>(List.of(0f,-0.29f,0f)),0.37f,0.37f,0.33f,0));


        //badan bawah
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0f,0f,1f,1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.485f,0.485f,0.17f,10));

        //segitiga merah
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(0.05f,-0.42f,0.0f)), 0.08f,0.08f,0.015f,8));
        flepi3.get(1).rotateObject((float) Math.toRadians(90),0f,0f,1f);
        flepi3.get(1).translateObject(+0.03f,-0.015f,0.0f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(0.05f,-0.44f,0.0f)), 0.08f,0.08f,0.015f,8));
        flepi3.get(2).rotateObject((float) Math.toRadians(135),0f,0f,1f);
        flepi3.get(2).translateObject(+0.03f,-0.015f,0.0f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(0.05f,-0.46f,0.0f)), 0.08f,0.08f,0.015f,8));
        flepi3.get(3).rotateObject((float) Math.toRadians(180),0f,0f,1f);
        flepi3.get(3).translateObject(+0.03f,-0.015f,0.0f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(0.05f,-0.48f,0.0f)), 0.08f,0.08f,0.015f,8));
        flepi3.get(4).rotateObject((float) Math.toRadians(220),0f,0f,1f);
        flepi3.get(4).translateObject(+0.03f,-0.015f,0.0f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(0.05f,-0.46f,0.0f)), 0.08f,0.08f,0.015f,8));
        flepi3.get(5).rotateObject((float) Math.toRadians(260),0f,0f,1f);
        flepi3.get(5).translateObject(+0.03f,-0.015f,0.0f);


        //badan atas
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,1f,1f,1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.485f,0.485f,0.17f,10));
        flepi3.get(6).rotateObject((float) Math.toRadians(180),0f,0f,1f);

        //mata kiri
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0f,0f,0f,1f),
                new ArrayList<>(List.of(-0.1f,0.2f,0.0f)), 0.03f,0.03f,0.015f,7));

        //mata kanan
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0f,0f,0f,1f),
                new ArrayList<>(List.of(+0.1f,0.2f,0.0f)), 0.03f,0.03f,0.015f,7));

        //segitiga bagian baju
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,1f,1f,1f),
                new ArrayList<>(List.of(0.0f,+0.1f,0.0f)), 0.3f,0.3f,0.014f,8));
        flepi3.get(9).rotateObject((float) Math.toRadians(180),0f,0f,1f);

        //kancing 1
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0f,0f,0f,1f),
                new ArrayList<>(List.of(0.0f,-0.09f,0.01f)), 0.02f,0.02f,0.05f,7));

        //kancing 2
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0f,0f,0f,1f),
                new ArrayList<>(List.of(0.0f,-0.15f,0.01f)), 0.02f,0.02f,0.05f,7));

        //dasi
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(0.0f,-0.015f,0.014f)), 0.06f,0.06f,0.015f,6));

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.08f,0.08f,0.015f,8));
        flepi3.get(13).rotateObject((float) Math.toRadians(90),0f,0f,1f);
        flepi3.get(13).translateObject(+0.03f,-0.015f,0.0f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.08f,0.08f,0.015f,8));
        flepi3.get(14).rotateObject((float) Math.toRadians(270),0f,0f,1f);
        flepi3.get(14).translateObject(-0.03f,-0.015f,0.0f);

        //mulut
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0f,0f,0f,1f),
                new ArrayList<>(List.of(0.0f,+0.1f,0.002f)), 0.1f,0.005f,0.015f,10));

        // Kaki kiri
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.2f,7));
        flepi3.get(16).rotateObject((float)Math.toRadians(90), 1.0f,0.0f,0.0f);
        flepi3.get(16).translateObject(-0.10f,-0.66f,-0.1f);

        // Kaki kanan
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.2f,7));
        flepi3.get(17).rotateObject((float)Math.toRadians(90), 1.0f,0.0f,0.0f);
        flepi3.get(17).translateObject(0.10f,-0.66f,-0.1f);

        // Kuku Kiri
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(-0.10f,-0.64f,-0.01f)), 0.02f,0.02f,0.08f,7));
        flepi3.add(new Sphere(shader, new ArrayList<>(),  new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(-0.10f,-0.64f,-0.01f)), 0.02f,0.02f,0.02f,0));

        // Kuku Kanan
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(0.10f,-0.64f,-0.01f)), 0.02f,0.02f,0.08f,7));
        flepi3.add(new Sphere(shader, new ArrayList<>(),  new Vector4f(1f,0f,0f,1f),
                new ArrayList<>(List.of(0.10f,-0.64f,-0.01f)), 0.02f,0.02f,0.02f,0));

        // Tangan kanan
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.05f,7));
        flepi3.get(22).rotateObject((float) Math.toRadians(90), 0.0f,1.0f,0.0f);
        flepi3.get(22).translateObject(0.4f,0.0f,0.0f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.1f,7));
        flepi3.get(23).rotateObject((float) Math.toRadians(-90), 0.0f,1.0f,0.0f);
        flepi3.get(23).rotateObject((float)Math.toRadians(-90), 0.0f,1.0f,1.0f);
        flepi3.get(23).translateObject(+0.5f,0.0f,0.0f);


        // Tangan kiri
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.05f,7));
        flepi3.get(24).rotateObject((float) Math.toRadians(90), 0.0f,1.0f,0.0f);
        flepi3.get(24).translateObject(0.4f,0.0f,0.0f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.1f,7));
        flepi3.get(25).rotateObject((float) Math.toRadians(-90), 0.0f,1.0f,0.0f);
        flepi3.get(25).rotateObject((float)Math.toRadians(-90), 0.0f,1.0f,1.0f);
        flepi3.get(25).translateObject(-0.5f,0.0f,0.0f);

        for (int i = 0; i< objects.size(); i++){
            objects.get(i).translateObject(2.0f,0.0f,0.0f);
        }

        for (int i = 0; i< flepi3.size(); i++){
            flepi3.get(i).translateObject(0.0f,2.0f,0.0f);
        }



    }
    public void loop(){
        while (window.isOpen()) {
            window.update();
            glClearColor(153/255f,
                    1f, 1f,
                    1f);
//            glClearColor(0.0f,0.0f,0.0f,0.0f);
            GL.createCapabilities();
            input();

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

        // Lambai tangan kanan
//        if (window.isKeyPressed(GLFW_KEY_T)){
            Vector3f posisi = flappy.get(11).getMatrix().transformPosition(new Vector3f(0.0f,0.0f,0.0f));
            flappy.get(11).translateObject(-posisi.x, -posisi.y,-posisi.z);
            flappy.get(13).translateObject(-posisi.x, -posisi.y, -posisi.z);
            flappy.get(14).translateObject(-posisi.x, -posisi.y, -posisi.z);

            if (counterNendang <= 15 && boolNendang && !nendangBalik){
                flappy.get(11).rotateObject((float)Math.toRadians(5f),1.0f,0.0f,.0f);
                flappy.get(13).rotateObject((float)Math.toRadians(5f),1.0f,0.0f,0f);
                flappy.get(14).rotateObject((float)Math.toRadians(5f),1.0f,0.0f,0.0f);
                counterNendang ++;
            }
            else if(!nendangBalik){
                boolNendang = false;
                flappy.get(11).rotateObject((float)Math.toRadians(-5f),1.0f,0.0f,0.0f);
                flappy.get(13).rotateObject((float)Math.toRadians(-5f),1.0f,0.0f,0.0f);
                flappy.get(14).rotateObject((float)Math.toRadians(-5f), 1.0f,0.0f,0.0f);
                counterNendang--;
            }
            if (counterNendang <=-4 && !nendangBalik){
                nendangBalik = true;
                boolNendang = true;

                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        nendangBalik = false;
                        flappy.get(25).getMatrix().set(bolapos);
                        flappy.get(11).getMatrix().set(kakiKiri);
                        flappy.get(13).getMatrix().set(kakibola);
                        flappy.get(14).getMatrix().set(KakiSphere);
                        counterNendang = 0;
                    }
                };
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        flappy.get(25).translateObject(0f,0f,0.01f);


                    }


                };

                timer.schedule(task,0,interval);

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        task.cancel();
                        task1.run();

                    }
                }, 3000);



            }

            flappy.get(11).translateObject(posisi.x,posisi.y,posisi.z);
            flappy.get(13).translateObject(posisi.x,posisi.y,posisi.z);
            flappy.get(14).translateObject(posisi.x,posisi.y,posisi.z);
//        }


        if (window.isKeyPressed(GLFW_KEY_SPACE)){
            Vector3f pos = flappy.get(18).getMatrix().transformPosition(new Vector3f(0.0f,0.0f,0.0f));
            System.out.println(pos.x);
            System.out.println(pos.y);
            System.out.println(pos.z);
            flappy.get(18).translateObject(-pos.x, -pos.y,-pos.z);
            flappy.get(20).translateObject(-pos.x,-pos.y,-pos.z);

            if(counterkaki <= 18 && picker){
                flappy.get(20).rotateObject((float) Math.toRadians(5f), 1.0f,0.0f,0.0f);
                flappy.get(18).rotateObject((float)Math.toRadians(5f),1.0f,0f,.0f);
                counterkaki++;
            }
            else{
                picker = false;
                flappy.get(20).rotateObject((float)Math.toRadians(-5f),1.0f,0.0f,0.0f);
                flappy.get(18).rotateObject((float)Math.toRadians(-5f),1.0f,0f,.0f);
                counterkaki--;
            }
            if (counterkaki <= -0 ){
                picker = true;
            }

            flappy.get(18).translateObject(pos.x,pos.y,pos.z);
            flappy.get(20).translateObject(pos.x,pos.y,pos.z);

        }

        // Animasi Kaki
        // Kaki Kiri
        Vector3f kaki_kiri_atas = objects.get(6).getMatrix().transformPosition(new Vector3f(0.0f, 0.0f, 0.0f));
        objects.get(6).translateObject(-kaki_kiri_atas.x, -kaki_kiri_atas.y, -kaki_kiri_atas.z);
        objects.get(7).translateObject(-kaki_kiri_atas.x, -kaki_kiri_atas.y, -kaki_kiri_atas.z);
        objects.get(9).translateObject(-kaki_kiri_atas.x, -kaki_kiri_atas.y, -kaki_kiri_atas.z);


        // Kaki kanan
        Vector3f kaki_kanan_atas = objects.get(4).getMatrix().transformPosition(new Vector3f(0.0f, 0.0f, 0.0f));
        objects.get(4).translateObject(-kaki_kanan_atas.x, -kaki_kanan_atas.y, -kaki_kanan_atas.z);
        objects.get(5).translateObject(-kaki_kanan_atas.x, -kaki_kanan_atas.y, -kaki_kanan_atas.z);
        objects.get(8).translateObject(-kaki_kanan_atas.x, -kaki_kanan_atas.y, -kaki_kanan_atas.z);


        if (countKakiKiriJalan < 8 && kaki_kiri_jalan)
        {
            // Kaki Kiri
            objects.get(6).rotateObject((float) Math.toRadians(2), 1f, 0f, 0f);
            objects.get(7).rotateObject((float) Math.toRadians(2), 1f, 0f, 0f);
            objects.get(9).rotateObject((float) Math.toRadians(2), 1f, 0f, 0f);


            // Kaki Kanan
            objects.get(4).rotateObject((float) Math.toRadians(-2), 1f, 0f, 0f);
            objects.get(5).rotateObject((float) Math.toRadians(-2), 1f, 0f, 0f);
            objects.get(8).rotateObject((float) Math.toRadians(-2), 1f, 0f, 0f);

            countKakiKiriJalan++;
        }
        else
        {
            kaki_kiri_jalan = false;

            // Kaki Kiri
            objects.get(6).rotateObject((float) Math.toRadians(-2), 1f, 0f, 0f);
            objects.get(7).rotateObject((float) Math.toRadians(-2), 1f, 0f, 0f);
            objects.get(9).rotateObject((float) Math.toRadians(-2), 1f, 0f, 0f);

            // Kaki Kanan
            objects.get(4).rotateObject((float) Math.toRadians(2), 1f, 0f, 0f);
            objects.get(5).rotateObject((float) Math.toRadians(2), 1f, 0f, 0f);
            objects.get(8).rotateObject((float) Math.toRadians(2), 1f, 0f, 0f);

            countKakiKiriJalan--;
        }

        if (countKakiKiriJalan < -17)
            kaki_kiri_jalan = true;

        // Kaki kiri
        objects.get(6).translateObject(kaki_kiri_atas.x, kaki_kiri_atas.y, kaki_kiri_atas.z);
        objects.get(7).translateObject(kaki_kiri_atas.x, kaki_kiri_atas.y, kaki_kiri_atas.z);
        objects.get(9).translateObject(kaki_kiri_atas.x, kaki_kiri_atas.y, kaki_kiri_atas.z);

        // Kaki kanan
        objects.get(4).translateObject(kaki_kanan_atas.x, kaki_kanan_atas.y, kaki_kanan_atas.z);
        objects.get(5).translateObject(kaki_kanan_atas.x, kaki_kanan_atas.y, kaki_kanan_atas.z);
        objects.get(8).translateObject(kaki_kanan_atas.x, kaki_kanan_atas.y, kaki_kanan_atas.z);
        // End Animasi



        // Animasi Tangan

        // Tangan Kanan
        Vector3f tangan_kanan = objects.get(20).getMatrix().transformPosition(new Vector3f(0.0f,0.0f,0.0f));
        objects.get(20).translateObject(-tangan_kanan.x, -tangan_kanan.y,-tangan_kanan.z);
        objects.get(22).translateObject(-tangan_kanan.x, -tangan_kanan.y,-tangan_kanan.z);

        // Tangan Kiri
        Vector3f tangan_kiri = objects.get(24).getMatrix().transformPosition(new Vector3f(0.0f,0.0f,0.0f));
        objects.get(24).translateObject(-tangan_kiri.x, -tangan_kiri.y,-tangan_kiri.z);
        objects.get(26).translateObject(-tangan_kiri.x, -tangan_kiri.y,-tangan_kiri.z);


        if(counterTangan <= 25 && putar_tangan){
            // Tangan Kanan
            objects.get(20).rotateObject((float)Math.toRadians(-2f),1f,.0f,0f);
            objects.get(22).rotateObject((float)Math.toRadians(-2f),1f,0f,0f);

            // Tangan Kiri
            objects.get(24).rotateObject((float)Math.toRadians(2f),1f,0f,0f);
            objects.get(26).rotateObject((float)Math.toRadians(2f),1f,0f,0f);

            counterTangan++;
        }
        else{
            putar_tangan = false;

            // Tangan Kanan
            objects.get(20).rotateObject((float)Math.toRadians(2f),1f,.0f,0f);
            objects.get(22).rotateObject((float)Math.toRadians(2f),1f,0f,0f);

            // Tangan Kiri
            objects.get(24).rotateObject((float)Math.toRadians(-2f),1f,0f,0f);
            objects.get(26).rotateObject((float)Math.toRadians(-2f),1f,0f,0f);

            counterTangan--;
        }
        if (counterTangan <= 0){
            putar_tangan = true;
        }

        // Tangan Kanan
        objects.get(20).translateObject(tangan_kanan.x,tangan_kanan.y,tangan_kanan.z);
        objects.get(22).translateObject(tangan_kanan.x,tangan_kanan.y,tangan_kanan.z);

        // Tangan kiri
        objects.get(24).translateObject(tangan_kiri.x,tangan_kiri.y,tangan_kiri.z);
        objects.get(26).translateObject(tangan_kiri.x,tangan_kiri.y,tangan_kiri.z);

        // End Animasi

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
            camera.addRotation(0.01f,0.0f);
//            for (Object2d i: flappy)
//            {
//
//                i.rotateObject(0.01f, 1f, 0f, 0f);
//
//            }
        }

        if(window.isKeyPressed(GLFW_KEY_S))
        {
            camera.addRotation(-0.01f,0);
//            for (Object2d i: flappy)
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
//            for (Object2d i: flappy)
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
//            for (Object2d i: flappy)
//            {
//
//                i.rotateObject(-0.01f, 0f, 1f, 0f);
//
//            }
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
//            for (Object2d i: flappy)
//            {
//                i.translateObject(0f, 0.01f, 0f);
//            }
        }

        if(window.isKeyPressed(GLFW_KEY_K))
        {
            camera.moveDown(0.01f);
//            for (Object2d i: flappy)
//            {
//                i.translateObject(0f, -0.01f, 0f);
//            }
        }

        if(window.isKeyPressed(GLFW_KEY_J))
        {

            camera.moveLeft(0.01f);
//            for (Object2d i: flappy)
//            {
//                i.translateObject(-0.01f, 0f, 0f);
//            }
        }

        if(window.isKeyPressed(GLFW_KEY_L))
        {

            camera.moveRight(0.01f);
//            for (Object2d i: flappy)
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