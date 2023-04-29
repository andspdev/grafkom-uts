import Engine.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import java.awt.Robot;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.*;


public class Main {


    private Window window = new Window(800,800,"Adventure Time");



    private ArrayList<Matrix4f> simpanFlepi3 = new ArrayList<>();
    private ArrayList<Object2d> flappy = new ArrayList<>();
    private ArrayList<Object2d> objects = new ArrayList<>();
    private ArrayList<Object2d> flepi2 = new ArrayList<>();
    private ArrayList<Object2d> flepi3 = new ArrayList<>();
    private ArrayList<Object2d> env = new ArrayList<>();
    private ArrayList<Matrix4f> objectstemp = new ArrayList<>();
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();
    Integer counterkaki = 0;
    Integer counterNendang = 0;
    Boolean boolNendang = true;
    Boolean picker = true;
    Boolean nendangBalik = false;
    Integer countTanganFlepi3 = 0;
    Integer countLompatFlepi3 = 0;
    Boolean flepi3up = true;
    Boolean flapmergeRun = false;
    Boolean revert = true;
    Boolean ONETIMER = true;

    double temp = 0;
    double temp2 = 0;
    int count = 1;
    int count2 = 1;
    boolean flag = false;
    boolean flag2 = false;


    int count3 = 100;

    List<Integer> starIndex = Arrays.asList(0,5,3,4,5,2,1,5,4,1,5,3);



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
        camera.setPosition(0,0.85f,4.5f);
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
                new ArrayList<>(List.of(0f,0f,0f)),0.1f,0.009f,0.215f,9));

        flepi2.get(12).rotateObject((float) Math.toRadians(-5), 0f, 0f, 1f);
        flepi2.get(12).translateObject(-0.18f,0.35f,0.22f);

        // alis kanan
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.1f,0.009f,0.215f,9));

        flepi2.get(13).rotateObject((float) Math.toRadians(5), 0f, 0f, 1f);
        flepi2.get(13).translateObject(0.18f,0.35f,0.22f);

        // mata kiri
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.05f,0.055f,0.215f,9));

        flepi2.get(14).rotateObject((float) Math.toRadians(-5), 0f, 0f, 1f);
        flepi2.get(14).translateObject(-0.18f,0.3f,0.22f);

        // mata kanan
        flepi2.add(new Sphere(shader, new ArrayList<>(),new Vector4f((0f),(0f),(0f), 1.0f),
                new ArrayList<>(List.of(0f,0f,0f)),0.05f,0.055f,0.215f,9));

        flepi2.get(15).rotateObject((float) Math.toRadians(5), 0f, 0f, 1f);
        flepi2.get(15).translateObject(0.18f,0.3f,0.22f);

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
        flappy.get(25).translateObject(-.10f,-0.48f,0.24f);


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
                new Vector4f(0f, (128/255f), (255/255f),1.0f),
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
                new Vector4f(0f, (128/255f), (255/255f),1.0f),
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
                new Vector4f(0f, (128/255f), (255/255f),1.0f),
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
                new Vector4f(0f, (128/255f), (255/255f),1.0f),
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

        // Tangan Kiri
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f),
                new ArrayList<>(List.of(.0f,.0f,.0f)), 0.02f,0.02f,0.07f,7));
        flepi3.get(22).rotateObject((float) Math.toRadians(-90), 0.0f,1.0f,0.0f);
        flepi3.get(22).translateObject(-0f,.0f,.4f);
        flepi3.add(new Sphere(shader, new ArrayList<>(),new Vector4f(0,0,1,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.02f,0.02f,0.02f,0));
        flepi3.get(23).translateObject(-.0f,.0f,.4f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f), new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.02f,0.02f,0.1f,7));
        flepi3.get(24).rotateObject((float) Math.toRadians(-90), 1.0f,0.0f,.0f);

        flepi3.get(24).translateObject(-.0f,0.0f,0.4f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1,0,0,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.02f,0));
        flepi3.get(25).translateObject(-.0f,-0.1f,0.4f);
        for (int i = 22; i<= 25; i++){
            flepi3.get(i).scaleObject(1.5f,1.5f,1.5f);
        }
        for (int i = 22; i<= 25; i++){
            flepi3.get(i).translateObject(-0.58f,0.0f,-0.7f);
        }

        // Tangan Kanan
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f),
                new ArrayList<>(List.of(.0f,.0f,.0f)), 0.02f,0.02f,0.07f,7));
        flepi3.get(26).rotateObject((float) Math.toRadians(90), 0.0f,1.0f,0.0f);
        flepi3.get(26).translateObject(-0f,.0f,.4f);
        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),0.02f,0.02f,0.02f,0));
        flepi3.get(27).translateObject(-.0f,.0f,.4f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(0,0,1,1.0f), new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.02f,0.02f,0.1f,7));
        flepi3.get(28).rotateObject((float) Math.toRadians(-90), 1.0f,0.0f,.0f);

        flepi3.get(28).translateObject(-.0f,0.0f,0.4f);

        flepi3.add(new Sphere(shader, new ArrayList<>(), new Vector4f(1,0,0,1.0f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)), 0.02f,0.02f,0.02f,0));
        flepi3.get(29).translateObject(-.0f,-0.1f,0.4f);
        for (int i = 26; i<= 29; i++){
            flepi3.get(i).scaleObject(1.5f,1.5f,1.5f);
        }
        for (int i = 26; i<= 29; i++){
            flepi3.get(i).translateObject(0.58f,0.0f,-0.7f);
        }





        for (int i = 0; i< objects.size(); i++){
            objects.get(i).translateObject(2.0f,0.0f,0.0f);
        }

        for (int i = 0; i< flepi3.size(); i++){
            flepi3.get(i).translateObject(0.0f,2.0f,0.0f);
        }
        // Latar
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


        // Dinding Kiri
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((236/255f),(185/255f),(104/255f),1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.1f,
                1.8f,
                3.5f,
                9
        ));

        env.get(1).translateObject(-1.2f, .95f, 0f);


        // Dinding kanan
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((236/255f),(185/255f),(104/255f),1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                0.1f,
                1.8f,
                3.5f,
                9
        ));

        env.get(2).translateObject(1.2f, .95f, 0f);


        // Dinding Belakang
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((236/255f),(185/255f),(104/255f),1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                2.5f,
                1.8f,
                0.1f,
                9
        ));

        env.get(3).translateObject(0f, .95f, -1.75f);




        // Kursi
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((136/255f),(0/255f),(21/255f),1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                .5f,
                .1f,
                .2f,
                6
        ));
        env.get(4).translateObject(0.1f, .1f, -1.39f);
        env.get(4).rotateObject((float) Math.toRadians(35), 0f, 1f, 0f);


        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((166/255f),(3/255f),(28/255f),1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                .5f,
                .25f,
                .02f,
                6
        ));
        env.get(5).translateObject(0.1f, .175f, -1.5f);
        env.get(5).rotateObject((float) Math.toRadians(35), 0f, 1f, 0f);
        // End Kursi



        // Pohon Cemara
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((136/255f),(0/255f),(21/255f), 1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                .04f,
                .45f,
                .04f,
                12
        ));

        env.get(6).translateObject(.7f, -1.15f, 0f);
        env.get(6).rotateObject((float) Math.toRadians(-90), -1f, 0f, 0f);


        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f((56/255f),(108/255f),(96/255f), 1f),
                new ArrayList<>(List.of(0.0f,0.0f,0.0f)),
                .1f,
                .1f,
                .25f,
                11
        ));

        env.get(7).rotateObject((float) Math.toRadians(90),1f, 0f, 0f);
        env.get(7).translateObject(.7f, 1f, -1.15f);
        // End Pohon Cemara



        // Jalan vertical
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(6/255f,6/255f,6/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.4f,
                0.01f,
                3.5f,
                9
        ));
        env.get(8).translateObject(0f, 0.055f, 0f);


        // Jalan horizontal
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(8/255f,8/255f,8/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                2.4f,
                0.01f,
                0.4f,
                9
        ));
        env.get(9).translateObject(0f, 0.055f, 0f);


        // Border tepi
        env.add(new Line(shader,
                new ArrayList<>(List.of(
                        new Vector3f(-1.15f,.053f,1.75f),
                        new Vector3f(-1.15f,.053f,-1.69f),
                        new Vector3f(-1.15f,1.853f,-1.69f), // Vertical Belakang Kanan
                        new Vector3f(-1.15f,.053f,-1.69f),

                        new Vector3f(1.15f,.053f,-1.69f),
                        new Vector3f(1.15f,1.853f,-1.69f), // Vetical Belakang Kiri
                        new Vector3f(1.15f,.053f,-1.69f),
                        new Vector3f(1.15f,.053f, 1.75f)
                )
                ), new Vector4f(153/255f,76/255f,0f,1f)));
        //



        // Kotak Surat (Tiang)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(110/255f,110/255f,110/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.02f,
                0.23f,
                0.02f,
                9
        ));
        env.get(11).translateObject(0.3f, 0.1f, 0.4f);


        // Kotak Surat (Tempat Surat)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(110/255f,110/255f,110/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.06f,
                0.05f,
                0.095f,
                9
        ));
        env.get(12).translateObject(0.3f, 0.23f, 0.4f);



        // Jalan lurus putih (tengah)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(255f,255f,255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.03f,
                0.01f,
                0.7f,
                9
        ));
        env.get(13).translateObject(0f, 0.06f, 0f);


        // Jalan lurus putih (depan)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(255f,255f,255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.03f,
                0.01f,
                0.7f,
                9
        ));
        env.get(14).translateObject(0f, 0.06f, 1.15f);


        // Jalan lurus putih (belakang)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(255f,255f,255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.03f,
                0.01f,
                0.7f,
                9
        ));
        env.get(15).translateObject(0f, 0.06f, -1.15f);


        // Jalan lurus putih (horizontal - kiri)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(255f,255f,255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.5f,
                0.01f,
                0.03f,
                9
        ));
        env.get(16).translateObject(-0.7f, 0.06f, 0f);


        // Jalan lurus putih (horizontal - kanan)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(255f,255f,255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.5f,
                0.01f,
                0.03f,
                9
        ));
        env.get(17).translateObject(0.7f, 0.06f, 0f);



        // Air Mancur Bawah (1)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(109/255f,109/255f,109/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.27f,
                0.27f,
                0.09f,
                7
        ));

        env.get(18).translateObject(0.65f, 1f, -0.056f);
        env.get(18).rotateObject((float) Math.toRadians(90), 1f, 0f, 0f);


        // Air (1)
        env.add(new Circle(
                shader,
                new ArrayList<>(),
                new Vector4f(5/255f, 136/255f, 252/255f,1f),
                0.23f,
                0,
                0,
                2,
                0
        ));
        env.get(19).translateObject(0.65f, 0.15f, 1f);


        // Air Mancur Bawah (Tiang)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(109/255f,109/255f,109/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.08f,
                0.08f,
                0.15f,
                7
        ));

        env.get(20).translateObject(0.65f, 1f, -0.07f);
        env.get(20).rotateObject((float) Math.toRadians(90), 1f, 0f, 0f);


        // Air Mancur Bawah (2)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(109/255f,109/255f,109/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.14f,
                0.14f,
                0.07f,
                7
        ));

        env.get(21).translateObject(0.65f, 1f, -0.2f);
        env.get(21).rotateObject((float) Math.toRadians(90), 1f, 0f, 0f);


        // Air (2)
        env.add(new Circle(
                shader,
                new ArrayList<>(),
                new Vector4f(5/255f, 136/255f, 252/255f,1f),
                0.1f,
                0,
                0,
                2,
                0
        ));
        env.get(22).translateObject(0.65f, 0.28f, 1f);

        // Air Mancur Bawah (Tiang 2)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(109/255f,109/255f,109/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.03f,
                0.03f,
                0.06f,
                7
        ));

        env.get(23).translateObject(0.65f, 1f, -0.25f);
        env.get(23).rotateObject((float) Math.toRadians(90), 1f, 0f, 0f);

        env.add(new Line(shader, new ArrayList<>(List.of(
                new Vector3f(-0.9f,.20f,1.3f),
                new Vector3f(-0.9f,0.06f,1.3f),
                new Vector3f(-0.9f,0.06f,1.15f),
                new Vector3f(-0.9f,0.20f,1.2f),
                new Vector3f(-0.9f, 0.20f,1.3f),


                new Vector3f(-0.6f,.20f,1.3f),
                new Vector3f(-0.6f,0.06f,1.3f),
                new Vector3f(-0.6f,0.06f,1.15f),
                new Vector3f(-0.6f,0.20f,1.2f),
                new Vector3f(-0.6f, 0.20f,1.3f),

                new Vector3f(-0.6f, 0.06f,1.3f),
                new Vector3f(-0.9f, 0.06f,1.3f)

        )), new Vector4f(0.0f,0.0f,0.0f,1.0f)));

        env.get(24).translateObject(-0.1f,0.0f,0.4f);


        // Alat treadmill
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(109/255f,109/255f,109/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.58f,
                0.04f,
                0.3f,
                9
        ));
        env.get(25).translateObject(0.65f, 0.068f, -0.58f);


        // Alat treadmill (Tiang Kiri)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(53/255f,53/255f,53/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.03f,
                0.3f,
                0.01f,
                9
        ));
        env.get(26).translateObject(0.34f, 0.3f, -0.425f);
        env.get(26).rotateObject((float) Math.toRadians(-22), 0f, 0f, 1f);


        // Alat treadmill (Tiang Kanan)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(53/255f,53/255f,53/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.03f,
                0.3f,
                0.01f,
                9
        ));
        env.get(27).translateObject(0.34f, 0.3f, -0.736f);
        env.get(27).rotateObject((float) Math.toRadians(-22), 0f, 0f, 1f);


        // Alat treadmill (Kotak)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(109/255f,109/255f,109/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.12f,
                0.025f,
                0.3f,
                9
        ));
        env.get(28).translateObject(0.36f, 0.42f, -0.58f);
        env.get(28).rotateObject((float) Math.toRadians(-20), 0f, 0f, 1f);


        // Alat treadmill (kotak - jalan)
        env.add(new Sphere(
                shader,
                new ArrayList<>(),
                new Vector4f(53/255f,53/255f,53/255f,1f),
                new ArrayList<>(List.of(0.0f, 0.0f, 0.0f)),
                0.55f,
                0.004f,
                0.245f,
                9
        ));
        env.get(29).translateObject(0.66f, 0.09f, -0.58f);

        for(Object2d object : flappy){
            object.translateObject(-0.8f,0.7f,2.9f);
        }
        for(Object2d object : flappy){
            object.scaleObject(0.3f,0.3f,0.3f);
        }
        KakiSphere = flappy.get(14).getMatrix();
        kakibola = flappy.get(13).getMatrix();
        kakiKiri = flappy.get(11).getMatrix();
        bolapos = flappy.get(25).getMatrix();

        for (Object2d object : objects){
            object.scaleObject(0.3f,0.3f,0.3f);
        }
        for (Object2d object :  objects){
            object.translateObject(0.0f,0.257f,-0.5f);
        }

        for (int i = 0; i< objects.size(); i++){
            objectstemp.add(objects.get(i).getMatrix());
        }
        for(Object2d object : flepi2){
            object.scaleObject(0.3f,0.3f,0.3f);
        }
        for (Object2d object : flepi2){
            object.translateObject(-0.5f,0.35f,-0.7f);
        }

        for (Object2d object : flepi3){
            object.scaleObject(0.3f,0.3f,0.3f);
        }

        for (Object2d object : flepi3){
            object.rotateObject((float) Math.toRadians(-90),0.0f,1.0f,0.0f);
        }
        for (Object2d object : flepi3){
            object.translateObject(-0.3f,-0.35f,1.4f);
        }
        for (Object2d objects : flepi3){
            simpanFlepi3.add(objects.getMatrix());
        }

        for(Object2d object : objects){
            object.rotateObject((float) Math.toRadians(-90), 0.0f,1.0f,0.0f);
        }
        for (Object2d object : objects){
            object.translateObject(.25f, 0.05f,-1.25f);
            object.scaleObject(.9f,.9f,.9f);
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

            for(Object2d object : env){
                object.draw(camera,projection);
            }

            for(Object2d object: flappy){
                object.draw(camera, projection);
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

//
//        TimerTask task4 = new TimerTask() {
//            @Override
//            public void run() {
//                for (Object2d object : objects){
//                    object.translateObject(0.0f,0.0f,0.001f);
//                }
//            }
//
//
//        };
//
//        TimerTask task3 = new TimerTask() {
//            @Override
//            public void run() {
//                for (int i = 0; i < objects.size(); i++){
//                    objects.get(i).getMatrix().set(objectstemp.get(i));
//                }
//            }
//        };
//
//
//        timer.schedule(task4,0,interval);
//
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                task4.cancel();
//                task3.run();
//                task3.cancel();
//
//            }
//        }, 3000);
        if (window.isKeyPressed(GLFW_KEY_T)){
            for (Object2d object : objects){
                object.translateObject(0.0f,0.0f,0.01f);
            }
        }
        if (window.isKeyPressed(GLFW_KEY_C)){
            for (int i = 0; i < objects.size(); i++){
                objects.get(i).getMatrix().set(objectstemp.get(i));
            }
        }
        if (window.isKeyPressed(GLFW_KEY_G)){
            for (Object2d object : objects){
                Vector3f safe = object.getMatrix().transformPosition(new Vector3f(0.0f,0.0f,0.0f));
                object.translateObject(-safe.x, -safe.y, -safe.z);
                object.rotateObject((float) Math.toRadians(2f), 0.0f,1.0f,0.0f);
                object.translateObject(safe.x,safe.y,safe.z);

            }
        }

        // Lambai tangan kanan
//        if (window.isKeyPressed(GLFW_KEY_T)){
            Vector3f posisi1 = flappy.get(11).getMatrix().transformPosition(new Vector3f(0.0f,0.0f,0.0f));
            flappy.get(11).translateObject(-posisi1.x, -posisi1.y,-posisi1.z);
            flappy.get(13).translateObject(-posisi1.x, -posisi1.y, -posisi1.z);
            flappy.get(14).translateObject(-posisi1.x, -posisi1.y, -posisi1.z);

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
                        flapmergeRun = false;
                        flepi3up = true;
                        revert = false;
                        countLompatFlepi3 = 0;
                        countTanganFlepi3 = 0;
                        counterNendang = 0;
                        for (int i = 0; i< flepi3.size(); i++){
                            flepi3.get(i).getMatrix().set(simpanFlepi3.get(i));
                        }


                    }
                };

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        flappy.get(25).translateObject(0f,0f,0.01f);


                    }
                };
                TimerTask task10 = new TimerTask() {
                    @Override
                    public void run() {
                        if(flapmergeRun) {
                            if (flepi3up) {
                                if (countTanganFlepi3 <= 36) {
                                    Vector3f posJo = flepi3.get(24).getMatrix().transformPosition(new Vector3f(0.0f, 0.0f, 0.0f));
                                    Vector3f posJoKanan = flepi3.get(28).getMatrix().transformPosition(new Vector3f(0.0f, 0.0f, 0.0f));
                                    flepi3.get(24).translateObject(-posJo.x, -posJo.y, -posJo.z);
                                    flepi3.get(25).translateObject(-posJo.x, -posJo.y, -posJo.z);
                                    flepi3.get(28).translateObject(-posJoKanan.x, -posJoKanan.y, -posJoKanan.z);
                                    flepi3.get(29).translateObject(-posJoKanan.x, -posJoKanan.y, -posJoKanan.z);

                                    flepi3.get(24).rotateObject((float) Math.toRadians(-5), .0f, .0f, 1.0f);
                                    flepi3.get(25).rotateObject((float) Math.toRadians(-5), .0f, .0f, 1.0f);
                                    flepi3.get(28).rotateObject((float) Math.toRadians(-5), .0f, .0f, 1.0f);
                                    flepi3.get(29).rotateObject((float) Math.toRadians(-5), .0f, .0f, 1.0f);


                                    flepi3.get(24).translateObject(posJo.x, posJo.y, posJo.z);
                                    flepi3.get(25).translateObject(posJo.x, posJo.y, posJo.z);
                                    flepi3.get(28).translateObject(posJoKanan.x, posJoKanan.y, posJoKanan.z);
                                    flepi3.get(29).translateObject(posJoKanan.x, posJoKanan.y, posJoKanan.z);
                                    countTanganFlepi3++;
                                }

                                if (countTanganFlepi3 >= 36 && countLompatFlepi3 <= 10 && flepi3up) {
                                    for (Object2d objects : flepi3) {
                                        objects.translateObject(0.0f, 0.005f, 0.0f);
                                    }
                                    countLompatFlepi3++;
                                }
                            }

                            if (countLompatFlepi3 >= 10) {
                                flepi3up = false;
                            }

                            if (!flepi3up) {


                                if (countTanganFlepi3 >= 36 && countLompatFlepi3 >= 0) {
                                    for (Object2d objects : flepi3) {
                                        objects.translateObject(0.0f, -0.005f, 0.0f);
                                    }
                                    countLompatFlepi3--;
                                }

                                if (countTanganFlepi3 >= 0 && countLompatFlepi3 <= 0) {
                                    Vector3f posJo = flepi3.get(24).getMatrix().transformPosition(new Vector3f(0.0f, 0.0f, 0.0f));
                                    Vector3f posJoKanan = flepi3.get(28).getMatrix().transformPosition(new Vector3f(0.0f, 0.0f, 0.0f));
                                    flepi3.get(24).translateObject(-posJo.x, -posJo.y, -posJo.z);
                                    flepi3.get(25).translateObject(-posJo.x, -posJo.y, -posJo.z);
                                    flepi3.get(28).translateObject(-posJoKanan.x, -posJoKanan.y, -posJoKanan.z);
                                    flepi3.get(29).translateObject(-posJoKanan.x, -posJoKanan.y, -posJoKanan.z);

                                    flepi3.get(24).rotateObject((float) Math.toRadians(5), .0f, .0f, 1.0f);
                                    flepi3.get(25).rotateObject((float) Math.toRadians(5), .0f, .0f, 1.0f);
                                    flepi3.get(28).rotateObject((float) Math.toRadians(5), .0f, .0f, 1.0f);
                                    flepi3.get(29).rotateObject((float) Math.toRadians(5), .0f, .0f, 1.0f);


                                    flepi3.get(24).translateObject(posJo.x, posJo.y, posJo.z);
                                    flepi3.get(25).translateObject(posJo.x, posJo.y, posJo.z);
                                    flepi3.get(28).translateObject(posJoKanan.x, posJoKanan.y, posJoKanan.z);
                                    flepi3.get(29).translateObject(posJoKanan.x, posJoKanan.y, posJoKanan.z);
                                    countTanganFlepi3--;

                                }

                                if (countTanganFlepi3 <= 0 && countLompatFlepi3 <= 0) {


                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                        task1.run();
                                        task1.cancel();
                                        }
                                    }, 0);

                                }


                            }

                        }
                    }
                };







                timer.schedule(task,0,interval);

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        flapmergeRun = true;
                        task.cancel();
                        if (ONETIMER) {
                            timer.schedule(task10, 0, interval);
                        }
                        ONETIMER = false;

                    }
                }, 2000);







            }

            flappy.get(11).translateObject(posisi1.x,posisi1.y,posisi1.z);
            flappy.get(13).translateObject(posisi1.x,posisi1.y,posisi1.z);
            flappy.get(14).translateObject(posisi1.x,posisi1.y,posisi1.z);


//        }



        if (window.isKeyPressed(GLFW_KEY_SPACE)){
            Vector3f pos = flappy.get(18).getMatrix().transformPosition(new Vector3f(0.0f,0.0f,0.0f));
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
            objects.get(6).rotateObject((float) Math.toRadians(2), 0f, 0f, 1f);
            objects.get(7).rotateObject((float) Math.toRadians(2), 0f, 0f, 1f);
            objects.get(9).rotateObject((float) Math.toRadians(2), 0f, 0f, 1f);


            // Kaki Kanan
            objects.get(4).rotateObject((float) Math.toRadians(-2), 0f, 0f, 1f);
            objects.get(5).rotateObject((float) Math.toRadians(-2), 0f, 0f, 1f);
            objects.get(8).rotateObject((float) Math.toRadians(-2), 0f, 0f, 1f);

            countKakiKiriJalan++;
        }
        else
        {
            kaki_kiri_jalan = false;

            // Kaki Kiri
            objects.get(6).rotateObject((float) Math.toRadians(-2), 0f, 0f, 1f);
            objects.get(7).rotateObject((float) Math.toRadians(-2), 0f, 0f, 1f);
            objects.get(9).rotateObject((float) Math.toRadians(-2), 0f, 0f, 1f);

            // Kaki Kanan
            objects.get(4).rotateObject((float) Math.toRadians(2), 0f, 0f, 1f);
            objects.get(5).rotateObject((float) Math.toRadians(2), 0f, 0f, 1f);
            objects.get(8).rotateObject((float) Math.toRadians(2), 0f, 0f, 1f);

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
            objects.get(20).rotateObject((float)Math.toRadians(-2f),0f, 0f, 1f);
            objects.get(22).rotateObject((float)Math.toRadians(-2f),0f, 0f, 1f);

            // Tangan Kiri
            objects.get(24).rotateObject((float)Math.toRadians(2f),0f, 0f, 1f);
            objects.get(26).rotateObject((float)Math.toRadians(2f),0f, 0f, 1f);

            counterTangan++;
        }
        else{
            putar_tangan = false;

            // Tangan Kanan
            objects.get(20).rotateObject((float)Math.toRadians(2f),0f, 0f, 1f);
            objects.get(22).rotateObject((float)Math.toRadians(2f),0f, 0f, 1f);

            // Tangan Kiri
            objects.get(24).rotateObject((float)Math.toRadians(-2f),0f, 0f, 1f);
            objects.get(26).rotateObject((float)Math.toRadians(-2f),0f, 0f, 1f);

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

        // Animasi Steven H
        // animasi 1 hidup kaya (ubur-ubur)
        if(window.isKeyPressed(GLFW_KEY_1))
        {
            flag = true;
        }

        if(window.isKeyReleased(GLFW_KEY_1))
        {
            if (flag) {
                for (Object2d i : flepi2) {
                    if (count < 0) {
                        i.translateObject(0f, -0.0008f, 0f);
                    } else {
                        i.translateObject(0f, 0.0008f, 0f);

                    }
                }
                temp += count;
                if (temp > 15) {
                    count *= -1;
                } else if (temp < 0) {
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
                temp2 = 0;
                flepi2.get(30).setFlag();
            }
//            flepi2.get(30).translateObject(0f,-0.01f,0.02f);
//

        }

//        if(window.isKeyPressed(GLFW_KEY_Q))
//        {
//            for (Object2d i: flappy)
//            {
//
//                i.rotateObject(0.01f, 0f, 0f, 1f);
//
//            }
//        }
//
//        if(window.isKeyPressed(GLFW_KEY_E))
//        {
//            for (Object2d i: flappy)
//            {
//
//                i.rotateObject(-0.01f, 0f, 0f, 1f);
//
//            }
//        }

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

        }

        if(window.getMouseInput().isLeftButtonPressed()){
            Vector2f pos = window.getMouseInput().getCurrentPos();

            pos.x = (pos.x - (window.getWidth())/2.0f)/(window.getWidth()/2.0f);
            pos.y = (pos.y - (window.getHeight())/2.0f)/(-window.getHeight()/2.0f);
            if((!(pos.x > 1 || pos.x < -0.97)&&!(pos.y >0.97 || pos.y < -1))){


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