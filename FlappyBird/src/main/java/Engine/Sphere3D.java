package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Sphere3D extends CircleNew{
    int sectorCount;
    int stackCount;
    Float radiusZ;
    public Sphere3D(List<ShaderModuleData> shaderModuleDataList,
                    List<Vector3f> vertices, Vector4f color,
                    List<Float> centerPoint, Float radiusX, Float radiusY
            , Float radiusZ, int sectorCount, int stackCount, int pilih) {
        super(shaderModuleDataList, vertices, color, centerPoint, radiusX,radiusY);
        this.sectorCount = sectorCount;
        this.stackCount = stackCount;
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
        setupVAOVBO();
    }

    public void Ellipsoid(){

        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
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

    public void EllipticParaboloid(){
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = (float)((radiusX * v) * Math.cos(u));
                float z = (float)((radiusY * v) * Math.sin(u));
                float y = (float)(v * v);
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
                float z = (float)(v * v);
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
    public void draw(){
        drawSetup();
        // Draw the vertices
        glLineWidth(1);
        glPointSize(0);
        glDrawArrays(GL_LINE_STRIP, 0, vertices.size());
    }

}