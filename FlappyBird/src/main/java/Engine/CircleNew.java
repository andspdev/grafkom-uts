package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class CircleNew extends Object2d {

    List<Float> centerPoint;
    Float radiusX, radiusY;
    double x,y;

    public CircleNew(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                  List<Float> centerPoint, Float radiusX, Float radiusY){
        super(shaderModuleDataList, vertices, color);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.centerPoint =  centerPoint;
//        this.offset = offset;
////        if (pick == 0){
//            createCircle();
//        }
//        else if (pick == 1){
//            createElipse();
//        }

        setupVAOVBO();



    }

    public void createRectangle(){

        //clear vertices
        vertices.clear();

        for (float i = 45; i < 405; i+=90) {
            x = centerPoint.get(0) + ((radiusX) * Math.cos(Math.toRadians(i)));
            y = centerPoint.get(1) + ((radiusY) * Math.sin(Math.toRadians(i)));
            vertices.add(new Vector3f((float) x, (float) y, 0.0f));
        }



    }

    public void createCircle()
    {
        //clear vertices
        vertices.clear();

        for (float i = 0; i < 360; i+=0.01)
        {
            x = centerPoint.get(0) + ((radiusX-0.03) * Math.cos(Math.toRadians(i)));
            y = centerPoint.get(1) + ((radiusY) * Math.sin(Math.toRadians(i)));
            vertices.add(new Vector3f((float) x, (float) y, 0.0f));

        }
    }
    public void createElipse()
    {
        //clear vertices
        vertices.clear();

        for (float i = 0; i < 360; i+=0.01)
        {
            x = centerPoint.get(0) + ((radiusX+0.07) * Math.cos(Math.toRadians(i)));
            y = centerPoint.get(1) + ((radiusY) * Math.sin(Math.toRadians(i)));
            vertices.add(new Vector3f((float) x, (float) y, 0.0f));

        }
    }

    public void draw(){
        drawSetup();

//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawArrays(GL_TRIANGLE_FAN,0,vertices.size());

        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT

    }



}



