package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;

public class Sphere extends CircleNew{
    int sectorCount;
    int stackCount;
    Float radiusZ;
    public Sphere(List<ShaderModuleData> shaderModuleDataList,
                  List<Vector3f> vertices, Vector4f color,
                  List<Float> centerPoint, Float radiusX,Float radiusY
            ,Float radiusZ,int pilih){
        super(shaderModuleDataList, vertices, color, centerPoint, radiusX,radiusY);
        this.radiusZ = radiusZ;
        if (pilih == 0){
            Ellipsoid();
        }
        else if (pilih == 1){
            Hyperboloid();
        }
        else if (pilih == 2){
            Hyperboloid2();
        }
        else if (pilih == 3){
            EllipticCone();
        }
        else if(pilih == 4){
            EllipticParaboloid();
        }
        else if(pilih == 5){
            HyperboloidParaboloid();
        }
        else if(pilih == 6){
            createBoxVertices();
        }
        else if(pilih == 7){
            createCylinder();
        }
        else if (pilih == 8){
            createTriangle();
        }
        else if (pilih == 9){
            createCornerlessBox();
        }
        else if(pilih == 10){
            createCylinderStengah();
        }
        else if (pilih == 11){
            createParaboloid();
        }
        else if (pilih == 12){
            createCylinderBerdiri();
        }
        else if(pilih == 13){
            halfEllipsoid();
        }
        setupVAOVBO();
    }

    public ArrayList<Vector3f> createCircles(Vector3f Centre, float radiusZ, int pick){
        ArrayList<Vector3f> temp1 = new ArrayList<>();
        float j = 0;
        float k = 0;
        if(pick == 0){
            j = 90;
            k = 0;
        }
        if(pick == 1){
            j = 0;
            k = -90;
        }
        if(pick == 2){
            j = -90;
            k = -180;
        }
        if(pick == 3){
            j = -180;
            k = -270;
        }

        for (float i = j; i >= k; i = i - 2) {

            x = Centre.x() + (radiusZ/16  * Math.cos(Math.toRadians(i)));
            y = Centre.y() + (radiusZ/16 * Math.sin(Math.toRadians(i)));
            temp1.add(new Vector3f((float)x, (float)y, Centre.z()));
        }


        return (temp1);
    }
    public ArrayList<Vector3f> generateBezierPoints(float firstX, float firstY, float firstZ, float secondX, float secondY, float secondZ, float thirdX, float thirdY, float thirdZ)
    {
        ArrayList<Vector3f> result = new ArrayList<>();
        float newX, newY, newZ;
        for(double i = 0; i <=1; i+= 0.01)
        {
            newX = (float) ((Math.pow((1-i), 2) * firstX) + (2 * (1-i) * i * secondX) + (Math.pow(i, 2) * thirdX));
            newY = (float) ((Math.pow((1-i), 2) * firstY) + (2 * (1-i) * i * secondY) + (Math.pow(i, 2) * thirdY));
            newZ = (float) ((Math.pow((1-i), 2) * firstZ) + (2 * (1-i) * i * secondZ) + (Math.pow(i, 2) * thirdZ));
            result.add(new Vector3f(newX, newY, newZ));
        }
        return result;
    }

    public void createCornerlessBox(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        Integer count = 0;

        ArrayList<Vector3f> berzier1 = generateBezierPoints(centerPoint.get(0) + radiusX/2 - radiusX/16,
                centerPoint.get(1) + radiusY/2, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) + radiusX/2,centerPoint.get(1) + radiusY/2,centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) + radiusX/2,centerPoint.get(1) + radiusY/2 - radiusY/16,centerPoint.get(2) + radiusZ/2);
        ArrayList<Vector3f> berzier2 = generateBezierPoints(centerPoint.get(0) + radiusX/2 - radiusX/16, centerPoint.get(1) + radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) + radiusX/2, centerPoint.get(1) + radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) + radiusX/2 , centerPoint.get(1) + radiusY/2 - radiusY/16, centerPoint.get(2) - radiusZ/2);
        temp.addAll(berzier1);
        temp.addAll(berzier2);

        for(int i = 0; i < berzier1.size(); i++){
            if(count == 0){
                temp.add(berzier1.get(i));
                temp.add(berzier2.get(i));
                count = 1;
            }
            else {
                temp.add(berzier2.get(i));
                temp.add(berzier1.get(i));
                count = 0;
            }
        }
        count = 0;
        temp.add(berzier2.get(berzier2.size()-1));
        temp.add(berzier1.get(berzier1.size() - 1));


        ArrayList<Vector3f> berzier3 =  generateBezierPoints(centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2 + radiusY/16, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) + radiusX/2 - radiusX/16, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2);
        ArrayList<Vector3f> berzier4 =  generateBezierPoints(centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2 + radiusY/16, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) + radiusX/2 - radiusX/16, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2);
        temp.addAll(berzier3);
        temp.addAll(berzier4);

        for(int i = 0; i < berzier3.size(); i++){

            if(count == 0){
                temp.add(berzier4.get(i));
                temp.add(berzier3.get(i));
                count = 1;
            }
            else {
                temp.add(berzier3.get(i));
                temp.add(berzier4.get(i));
                count = 0;
            }
        }
        count = 0;
        temp.add(berzier3.get(berzier3.size() - 1));


        ArrayList<Vector3f> berzier5 = generateBezierPoints(centerPoint.get(0) - radiusX/2  + radiusX/16, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2 + radiusY/16, centerPoint.get(2) + radiusZ/2);
        ArrayList<Vector3f> berzier6 = generateBezierPoints(centerPoint.get(0) - radiusX/2  + radiusX/16, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2 + radiusY/16, centerPoint.get(2) - radiusZ/2);

        temp.addAll(berzier5);
        temp.addAll(berzier6);

        for(int i = 0; i < berzier5.size(); i++){

            if(count == 0){
                temp.add(berzier5.get(i));
                temp.add(berzier6.get(i));
                count = 1;
            }
            else {
                temp.add(berzier6.get(i));
                temp.add(berzier5.get(i));
                count = 0;
            }
        }
        count = 0;
        temp.add(berzier6.get(berzier6.size()- 1));
        ArrayList<Vector3f> berzier7 = generateBezierPoints(centerPoint.get(0) - radiusX/2 , centerPoint.get(1) + radiusY/2 - radiusY/16, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) + radiusY/2, centerPoint.get(2) + radiusZ/2,
                centerPoint.get(0) - radiusX/2 + radiusX/16, centerPoint.get(1) + radiusY/2, centerPoint.get(2) + radiusZ/2);
        ArrayList<Vector3f> berzier8 = generateBezierPoints(centerPoint.get(0) - radiusX/2 , centerPoint.get(1) + radiusY/2 - radiusY/16, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) - radiusX/2, centerPoint.get(1) + radiusY/2, centerPoint.get(2) - radiusZ/2,
                centerPoint.get(0) - radiusX/2 + radiusX/16, centerPoint.get(1) + radiusY/2, centerPoint.get(2) - radiusZ/2);
        temp.addAll(berzier7);
        temp.addAll(berzier8);

        for(int i = 0; i < berzier7.size(); i++){

            if(count == 0){
                temp.add(berzier7.get(i));
                temp.add(berzier8.get(i));
                count = 1;
            }
            else {
                temp.add(berzier8.get(i));
                temp.add(berzier8.get(i));
                count = 0;
            }
        }
        temp.add(berzier8.get(berzier8.size() - 1));
        temp.add(berzier1.get(0));
        temp.add(berzier7.get(berzier7.size() - 1));
        temp.add(berzier2.get(0));
        temp.add(berzier8.get(berzier8.size() - 1));
        temp.add(berzier4.get(0));
        temp.add(berzier1.get(berzier1.size() - 1));
        temp.add(berzier3.get(0));
        temp.add(berzier2.get(berzier2.size()-1));
        temp.add(berzier6.get(0));
        temp.add(berzier8.get(berzier8.size() - 1));
        temp.add(berzier8.get(0));
        temp.add(berzier4.get(berzier4.size() - 1));
        temp.add(berzier2.get(0));
        temp.add(berzier6.get(berzier6.size() - 1));
        temp.add(berzier8.get(0));
        temp.add(berzier5.get(berzier5.size() - 1));
        temp.add(berzier7.get(0));
        temp.add(berzier1.get(berzier1.size() - 1));
        temp.add(berzier5.get(0));
        temp.add(berzier7.get(berzier7.size() - 1));
        temp.add(berzier3.get(0));
        temp.add(berzier5.get(berzier5.size() - 1));
        temp.add(berzier1.get(0));
        temp.add(berzier6.get(berzier6.size() - 1));
        temp.add(berzier4.get(0));
        temp.add(berzier2.get(berzier2.size() - 1));
        temp.add(berzier8.get(0));
        temp.add(berzier8.get(berzier8.size() - 1));
        temp.add(berzier5.get(0));
        temp.add(berzier4.get(berzier4.size() - 1));
        temp.add(berzier6.get(0));
        temp.add(berzier3.get(berzier3.size() - 1));
        temp.add(berzier7.get(0));
        temp.add(berzier1.get(0));
        temp.add(berzier7.get(berzier7.size() - 1));
        temp.add(berzier6.get(0));
        temp.add(berzier5.get(0));
        temp.add(berzier7.get(berzier7.size() - 1));
        temp.add(berzier7.get(0));
        temp.add(berzier5.get(berzier5.size() - 1));




        vertices = temp;
    }
    public void createTriangle(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        temp.add(new Vector3f(centerPoint.get(0), centerPoint.get(1) + radiusY/2, centerPoint.get(2) + radiusZ/2 ));
        temp.add(new Vector3f(centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2));
        temp.add(new Vector3f(centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) + radiusZ/2));
        temp.add(new Vector3f(centerPoint.get(0), centerPoint.get(1) + radiusY/2, centerPoint.get(2) - radiusZ/2 ));
        temp.add(new Vector3f(centerPoint.get(0) + radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2));
        temp.add(new Vector3f(centerPoint.get(0) - radiusX/2, centerPoint.get(1) - radiusY/2, centerPoint.get(2) - radiusZ/2));


        vertices.add(temp.get(0));
        vertices.add(temp.get(1));
        vertices.add(temp.get(2));
        vertices.add(temp.get(0));
        vertices.add(temp.get(3));
        vertices.add(temp.get(4));
        vertices.add(temp.get(5));
        vertices.add(temp.get(3));
        vertices.add(temp.get(1));
        vertices.add(temp.get(5));
        vertices.add(temp.get(2));
        vertices.add(temp.get(4));
        vertices.add(temp.get(0));
        vertices.add(temp.get(1));
        vertices.add(temp.get(3));
        vertices.add(temp.get(2));
        vertices.add(temp.get(0));
        vertices.add(temp.get(5));
        vertices.add(temp.get(4));
        vertices.add(temp.get(1));
    }

    public void createCylinderStengah()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for (double i = 180; i < 360; i+=0.1)
        {
            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
            y = centerPoint.get(1) + radiusY * (float)Math.sin(Math.toRadians(i));

            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2)));
            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2) - radiusZ));
        }
        temp.add(temp.get(1));
        temp.add(temp.get(0));
        temp.add(temp.get(temp.size() - 4));

        vertices = temp;
    }
    public void createCylinder()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        ArrayList<Vector3f> temp1 =  new ArrayList<>();
        ArrayList<Vector3f> temp2 = new ArrayList<>();

//        for (double i = 0; i < 360; i+=0.1)
//        {
//            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
//            y = centerPoint.get(1) + radiusY * (float)Math.sin(Math.toRadians(i));
//
//            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2)));
//            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2) - radiusZ));
//        }

        for (double i = 0; i < 360; i+=1){
            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
            y = centerPoint.get(1) + radiusY * (float)Math.sin(Math.toRadians(i));

            temp1.add(new Vector3f((float)x, (float)y, centerPoint.get(2)));
        }

        for (double i = 0; i < 360; i+=1){
            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
            y = centerPoint.get(1) + radiusY * (float)Math.sin(Math.toRadians(i));

            temp2.add(new Vector3f((float)x, (float)y, centerPoint.get(2) - radiusZ));
        }

        temp.addAll(temp1);
        temp.addAll(temp2);

        Integer count = 0;

        for(int i = 0; i < temp1.size(); i++){
            if(count == 0){
                temp.add(temp1.get(i));
                temp.add(temp2.get(i));
                count = 1;
            }
            else {
                temp.add(temp1.get(i));
                temp.add(temp2.get(i));
                count = 0;
            }
        }
        temp.add(temp2.get(0));
        temp.add(temp1.get(0));
        temp.add(temp1.get(temp1.size() - 1));


        vertices = temp;
    }

    public void createCylinderBerdiri()
    {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        ArrayList<Vector3f> temp1 =  new ArrayList<>();
        ArrayList<Vector3f> temp2 = new ArrayList<>();

//        for (double i = 0; i < 360; i+=0.1)
//        {
//            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
//            y = centerPoint.get(1) + radiusY * (float)Math.sin(Math.toRadians(i));
//
//            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2)));
//            temp.add(new Vector3f((float)x, (float)y, centerPoint.get(2) - radiusZ));
//        }

        for (double i = 0; i < 360; i+=1){
            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
            y = centerPoint.get(2) + radiusZ * (float)Math.sin(Math.toRadians(i));

            temp1.add(new Vector3f((float)x, (float)y, centerPoint.get(1)));
        }

        for (double i = 0; i < 360; i+=1){
            x = centerPoint.get(0) + radiusX * (float)Math.cos(Math.toRadians(i));
            y = centerPoint.get(2) + radiusZ * (float)Math.sin(Math.toRadians(i));

            temp2.add(new Vector3f((float)x, (float)y, centerPoint.get(1) - radiusY));
        }

        temp.addAll(temp1);
        temp.addAll(temp2);

        Integer count = 0;

        for(int i = 0; i < temp1.size(); i++){
            if(count == 0){
                temp.add(temp1.get(i));
                temp.add(temp2.get(i));
                count = 1;
            }
            else {
                temp.add(temp1.get(i));
                temp.add(temp2.get(i));
                count = 0;
            }
        }
        temp.add(temp2.get(0));
        temp.add(temp1.get(0));
        temp.add(temp1.get(temp1.size() - 1));


        vertices = temp;
    }


    public void Ellipsoid(){

        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/90){
                float x = radiusX * (float)(Math.cos(v) * Math.cos(u));
                float y = radiusY * (float)(Math.cos(v) * Math.sin(u));
                float z = radiusZ * (float)(Math.sin(v));
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }



    public void Hyperboloid(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = radiusX * (float)((1/Math.cos(v)) * Math.cos(u));
                float z = radiusY * (float)((1/Math.cos(v)) * Math.sin(u));
                float y = radiusZ * (float)(Math.tan(v));
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }

    public void Hyperboloid2(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI/2; u<= Math.PI/2; u+=Math.PI/60){
                float x = radiusX * (float)(Math.tan(v) * Math.cos(u));
                float z = radiusY * (float)(Math.tan(v) * Math.sin(u));
                float y = radiusZ * (float)((1/Math.cos(v)));
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }

            for(double u = Math.PI/2; u<= (3 * (Math.PI/2)); u+=Math.PI/60){
                float x = radiusX * (float)(Math.tan(v) * Math.cos(u));
                float z = radiusY * (float)(Math.tan(v) * Math.sin(u));
                float y = radiusZ * (float)((1/Math.cos(v)));
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }

        vertices=temp;
    }

    public void EllipticCone(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float)((radiusX * v) * Math.cos(u));
                float z = (float)((radiusY * v) * Math.sin(u));
                float y = (float)(radiusZ * v);
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }

    public void halfEllipsoid(){

        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI/80; u+=Math.PI/90){
                float x = radiusX * (float)(Math.cos(v) * Math.cos(u));
                float y = radiusY * (float)(Math.cos(v) * Math.sin(u));
                float z = radiusZ * (float)(Math.sin(v));
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }


    public void EllipticParaboloid(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float)((radiusX * v) * Math.cos(u));
                float z = (float)((radiusZ * v) * Math.sin(u));
                float y = (float)(radiusY * v * v);
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }

    public void createParaboloid(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float)((radiusX * v) * Math.cos(u));
                float y = (float)((radiusY * v) * Math.sin(u));
                float z = (float)(radiusZ * v * v);
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }


    public void HyperboloidParaboloid(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float)((radiusX * v) * Math.tan(u));
                float y = (float)((radiusY * v) * (1/Math.cos(u)));
                float z = (float)(radiusZ* v * v);
                temp.add(new Vector3f(x + centerPoint.get(0),y + centerPoint.get(1),z + centerPoint.get(2)));
            }
        }
        vertices=temp;
    }
    public void createBoxVertices()
    {
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //TITIK 1
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 2
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 3
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 4
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 5
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 6
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 7
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 8
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();

        vertices.clear();
        //kotak1
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        //kotak2
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(5));
        //kotak3
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(4));
        //kotak4
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(0));
        //kotak5
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        //kotak6
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
    }
    public void draw(Camera camera, Projection projection){
        if(this.flag) {
            drawSetup(camera, projection);
            // Draw the vertices
            glLineWidth(1);
            glPointSize(2);
            glDrawArrays(GL_POLYGON, 0, vertices.size());
            for (Object2d child : childObject) {
                child.draw(camera, projection);
            }
        }
    }

    @Override
    public void update(float x, float y, float z) {
        centerPoint.set(0,x);
        centerPoint.set(1,y);
        centerPoint.set(2,z);
    }

}