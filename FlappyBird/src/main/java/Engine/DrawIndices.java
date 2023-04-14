package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class DrawIndices extends Object2d {

    double cx,cy;
    List<Integer> index;
    int ibo;

    public DrawIndices(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                       double cx, double cy) {
        super(shaderModuleDataList, vertices, color);
        this.cx = cx;
        this.cy = cy;



        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.listoInt(index),GL_STATIC_DRAW);
        setupVAOVBO();

    }


    public void draw(Camera camera, Projection projection){
        drawSetup(camera, projection);
        System.out.println("Sini");

        glLineWidth(1);
        glPointSize(1);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES,index.size(),GL_UNSIGNED_INT,0);
//        glDrawArrays(GL_LINE_LOOP,0,vertices.size());

        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT

    }
}
