package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_POLYGON;

public class Line extends Object2d{
    public Line(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color) {
        super(shaderModuleDataList, vertices, color);
        setupVAOVBO();

    }

    public void draw(Camera camera, Projection projection){
        drawSetup(camera, projection);
        // Draw the vertices
        glLineWidth(3);

        glPointSize(1);
        glDrawArrays(GL_LINE_STRIP, 0, vertices.size());
        for (Object2d child:childObject){
            child.draw(camera, projection);
        }
    }
}
