package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Circle extends Object2d {


    double r,cx,cy;
    double x,y,offset;
    int pick;


    public Circle(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                  double r, double cx, double cy, int pick,double offset){
        super(shaderModuleDataList, vertices, color);
        this.r =  r;
        this.cx = cx;
        this.cy = cy;
        this.offset = offset;
        if (pick == 0){
            createCircle();
        }
        else if (pick == 1){
            createElipse();
        }
        else if (pick == 2){
            createCirclePlat();
        }

        setupVAOVBO();




    }

    public void createCircle()
    {
        //clear vertices
        vertices.clear();
//        x = -0.5f;


        for (float i = 180; i >= 90; i = i - 0.01f) {

            x = cx + ((r)  * Math.cos(Math.toRadians(i)));
            y = cy + ((r) * Math.sin(Math.toRadians(i)));
            vertices.add(new Vector3f((float)x, (float)y, 0));
        }




    }

    public void createCirclePlat()
    {
        //clear vertices
        vertices.clear();

        for (float i = 0; i < 360; i+=0.01)
        {
            x = cx + ((r) * Math.cos(Math.toRadians(i)));
            y = cy + ((r) * Math.sin(Math.toRadians(i)));
            vertices.add(new Vector3f((float) x, 0.0f , (float) y));

        }
    }
    public void createElipse()
    {
        //clear vertices
        vertices.clear();

        for (float i = 0; i < 360; i+=0.01)
        {
            x = cx + ((r+0.07) * Math.cos(Math.toRadians(i)));
            y = cy + ((r+offset) * Math.sin(Math.toRadians(i)));
            vertices.add(new Vector3f((float) x, (float) y, -1.0f));

        }

    }

    public void draw(Camera camera, Projection projection){
        drawSetup(camera, projection);

//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawArrays(GL_POLYGON,0,vertices.size());

        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT

    }



}



