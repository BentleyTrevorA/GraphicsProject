package controllers;

import dataRepresentations.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;

public abstract class LabController {
    protected Vector4f clearColor, currentColor, initialPointColor;
    protected Vector4f firstPointColor, secondPointColor, thirdPointColor;
    protected Set<PointData> pointData; // Used for drawing lines and points in the raster
    protected ArrayList<ConvexShape> convexShapes;
    protected int drawType;
    protected int pointCount, pointRadius;
    protected Vector4f initialPoint, firstPoint, secondPoint, thirdPoint;
    protected Vector4f normal;

    // For drawing
    protected Line line;

    // Matrix Stack
    protected Stack<Matrix4f> matrixStack;
    protected int numModelViewOnStack, numProjectionOnStack;

    // Transformation matrices
    protected Matrix4f modelView, projection, viewport, currentMatrix;
    protected Matrix4f modelViewTranspose;
    protected Vector4f bLView, tRView;

    // Clipping
    protected boolean depthTest, clippingEnabled;
    protected final float lowestZ = 1;

    // Lighting
    protected boolean lightingEnabled, colorMaterialEnabled;
    protected boolean[] lightsEnabled;
    protected Map<Integer, Vector4f> diffuseColors, ambientColors, specularColors, lightPositions;
    protected Vector4f specularSurfaceColor;
    protected float materialShininess;

    // Fog
    protected boolean fogEnabled;
    protected Vector4f fogColor;
    protected float fogMode, fogStart, fogEnd, fogDensity;

    // Normalization
    protected boolean normalize;

    protected final int DISPLAY_WIDTH = 640;
    protected final int DISPLAY_HEIGHT = 480;

    protected int drawMode = 1;
    protected ByteBuffer raster, depthRaster, normalRaster;

    public LabController() {
        initBase();
        init();
    }

    protected void initBase() {
        currentColor = new Vector4f(1, 1, 1, 1);
        clearColor = new Vector4f(0, 0, 0, 0);

        raster = ByteBuffer.allocateDirect(DISPLAY_WIDTH * DISPLAY_HEIGHT * 4 * 3); // * 4 to store Float, * 3 to store r, g, b
        raster.order(ByteOrder.LITTLE_ENDIAN);

        depthRaster = ByteBuffer.allocateDirect(DISPLAY_WIDTH * DISPLAY_HEIGHT * 4);
        depthRaster.order(ByteOrder.LITTLE_ENDIAN);

        // For storing the normal associated with each vertex (x, y, z)
        normalRaster = ByteBuffer.allocateDirect(DISPLAY_WIDTH * DISPLAY_HEIGHT * 4 * 3);
        normalRaster.order(ByteOrder.LITTLE_ENDIAN);

        line = new Line();
        convexShapes = new ArrayList<ConvexShape>();

        modelView = new Matrix4f();
        modelViewTranspose = new Matrix4f();
        modelView.transpose(modelViewTranspose);
        projection = new Matrix4f();
        viewport = new Matrix4f();
        currentMatrix = modelView;

        depthTest = false;
        clippingEnabled = false;

        matrixStack = new Stack<Matrix4f>();
        numModelViewOnStack = 0;
        numProjectionOnStack = 0;

        bLView = new Vector4f(0, 0, 0, 1);
        tRView = new Vector4f(DISPLAY_WIDTH, DISPLAY_HEIGHT, 0, 1);

        lightingEnabled = false;
        colorMaterialEnabled = false;

        lightsEnabled = new boolean[8];
        diffuseColors = new HashMap<Integer, Vector4f>();
        ambientColors = new HashMap<Integer, Vector4f>();
        specularColors = new HashMap<Integer, Vector4f>();
        lightPositions = new HashMap<Integer, Vector4f>();

        for (int i = 0; i < 8; i++) {
            diffuseColors.put(i, new Vector4f());
            ambientColors.put(i, new Vector4f());
            specularColors.put(i, new Vector4f());
            lightPositions.put(i, new Vector4f());
        }

        specularSurfaceColor = new Vector4f();
        materialShininess = 0;

        normal = new Vector4f();
        normalize = false;

        fogEnabled = false;
        fogColor = new Vector4f();
        fogStart = 0;
        fogEnd = 1;
        fogDensity = 1;
    }

    public void setupAfterInitGL() {

    }

    public void init() {
    }

    // This method is called to "resize" the viewport to match the screen.
    // When you first start, have it be in perspective mode.
    public void resizeGL() {
    }

    public void update() {
        Display.update();
    }

    //This is called every frame, and should be responsible for keyboard updates.
    public void updateKeyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            // OpenGL implementation
            drawMode = 1;
            render();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
            // my implementation
            drawMode = 2;
            render();
        }
    }

    //This method is the one that actually draws to the screen.
    public void render() {
        if (drawMode == 2) {
            // Save the old state so that you can set it back after you draw\
            boolean depthWasEnabled = glIsEnabled(GL_DEPTH_TEST);
            glDisable(GL_DEPTH_TEST);

            glMatrixMode(GL_MODELVIEW);
            glPushMatrix();
            glLoadIdentity();

            glMatrixMode(GL_PROJECTION);
            glPushMatrix();
            glLoadIdentity();

            // Draw the array of pixels ( This is where you draw the values you have stored in the array 'raster ')
            glRasterPos2f(-1, -1);
            glDrawPixels(DISPLAY_WIDTH, DISPLAY_HEIGHT, GL_RGB, GL_FLOAT, raster);

            // Set the state back to what it was
            glPopMatrix();
            glMatrixMode(GL_MODELVIEW);
            glPopMatrix();

            if (depthWasEnabled)
                glEnable(GL_DEPTH_TEST);
        } else {
            Display.update();
        }
        glFlush();
    }

    public void setPixel(int x, int y, float z, float r, float g, float b) {
        int zBasePos = ((y * DISPLAY_WIDTH) + x); // Instead of DISPLAY_WIDTH this used to be 640
        int basePos = zBasePos * 3;

        try {
            if (withinViewport(x, y)) {
                Vector4f pixelColor = new Vector4f(r, g, b, 1);

                float depth = depthRaster.asFloatBuffer().get(zBasePos);
                // -Z goes into the display
                if (!depthTest) {
                    // Put the pixel into the raster with the calculated color
                    raster.asFloatBuffer().put(basePos, pixelColor.x);
                    raster.asFloatBuffer().put(basePos + 1, pixelColor.y);
                    raster.asFloatBuffer().put(basePos + 2, pixelColor.z);
                }
                // DEPTH TESTING ENABLED
                else if (z >= -1 && z <= 1) {
                    if (depth == lowestZ || z == lowestZ || depth >= z) {
                        raster.asFloatBuffer().put(basePos, pixelColor.x);
                        raster.asFloatBuffer().put(basePos + 1, pixelColor.y);
                        raster.asFloatBuffer().put(basePos + 2, pixelColor.z);
                        depthRaster.asFloatBuffer().put(zBasePos, z);

                        Vector4f normal = modelToScreenNormal();
                        normalRaster.asFloatBuffer().put(basePos, normal.x);
                        normalRaster.asFloatBuffer().put(basePos + 1, normal.y);
                        normalRaster.asFloatBuffer().put(basePos + 2, normal.z);
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("X: " + x);
            System.err.println("Y: " + y);
            System.err.println("Z: " + z);
            System.err.println("basePos: " + basePos);
            throw e;
        }
    }

    private Vector4f calculateLight(Vector4f position, Vector4f posNormal) {
        Vector4f light = new Vector4f();

        // Add light from each of the 8 lights IF they are enabled
        for (int i = 0; i < 8; i++) {
            if (lightsEnabled[i]) {
                // light += l_a[i] + max(0, n.dotProduct((l_p[i] - p).normalize())) * l_d[i]

                // (l_p[i] - p).normalize()
                Vector4f normalizedInternals = new Vector4f();
                Vector4f.sub(lightPositions.get(i), position, normalizedInternals);
                normalizedInternals.normalise(normalizedInternals);

                // max(0, n.dotProduct(normalizedInternals)
                float max = Math.max(0, Vector4f.dot(posNormal, normalizedInternals));

                Vector4f diffuse = diffuseColors.get(i);
                Vector4f ambient = ambientColors.get(i);

                light.x += ambient.x + max * diffuse.x;
                light.y += ambient.y + max * diffuse.y;
                light.z += ambient.z + max * diffuse.z;
            }
        }

        //specColor += max(0, pow((n dot halfway), h)) * v_s <elementwise -times > l_s;

        return light;
    }

    private Vector4f calculateSpecularLightColor(Vector4f position, Vector4f posNormal) {
        Vector4f color = new Vector4f();

        // Add light from each of the 8 lights IF they are enabled
        for (int i = 0; i < 8; i++) {
            if (lightsEnabled[i]) {
                Vector4f unitLengthPosition = new Vector4f();
                Vector4f.sub(lightPositions.get(i), position, unitLengthPosition);
                unitLengthPosition.normalise(unitLengthPosition);

                if (Vector4f.dot(posNormal, unitLengthPosition) > 0) {
                    Vector4f halfway = new Vector4f();
                    Vector4f.add(unitLengthPosition, new Vector4f(0, 0, 1, 0), halfway);
                    halfway.normalise(halfway);

                    //specColor += max(0, pow((n dot halfway), h)) * v_s <elementwise -times > l_s;
                    // v_s = specularSurfaceColor
                    // l_s = specularLightColor
                    float max = (float) Math.max(0, Math.pow(Vector4f.dot(posNormal, halfway), materialShininess));

                    Vector4f specularLight = specularColors.get(i);
                    Vector4f elementWiseTimes = new Vector4f(specularSurfaceColor.x * specularLight.x * max,
                            specularSurfaceColor.y * specularLight.y * max,
                            specularSurfaceColor.z * specularLight.z * max,
                            specularSurfaceColor.w * specularLight.w * max);

                    Vector4f.add(color, elementWiseTimes, color);
                }
            }
        }

        return color;
    }

    private Vector4f calculateNewColor(Vector4f position) {
        // Change the color based off the lighting that is on
        Vector4f newColor = calculateColorChangeFromLight(position);

        // Change the color based off of the fog settings
        newColor = calculateColorChangeFromFog(position, newColor);

        return newColor;
    }

    public Vector4f calculateColorChangeFromLight(Vector4f position) {
        if (lightingEnabled) {
            // Get the normal
            Vector4f posNormal = modelToScreenNormal();
            Vector4f light = calculateLight(position, posNormal);
            Vector4f specularLight = calculateSpecularLightColor(position, posNormal);

            // Currently only does ambient and diffuse
            if (colorMaterialEnabled) {
                // newColor = light < elementwise - times > oldColor + specular
                // NOTE: may add .2 .2 .2 if desired
                Vector4f lightWithoutSpecular = new Vector4f(light.x * currentColor.x, light.y * currentColor.y, light.z * currentColor.z, light.w * currentColor.w);
                return Vector4f.add(lightWithoutSpecular, specularLight, new Vector4f());
            } else {
                // newColor = light < elementwise − times > (.8, .8, .8, 1) + (.2, .2, .2, 0) + specColor
                // light < elementwise - times > {.8, .8, .8, 1) MEANS:
                // {light.x * .8, light.y * .8, light.z * .8, light.w * 1}
                Vector4f lightWithoutSpecular = new Vector4f(light.x * .8f + .2f, light.y * .8f + .2f, light.z * .8f + .2f, 0);
                return Vector4f.add(lightWithoutSpecular, specularLight, new Vector4f());
            }
        }

        return new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1);
    }

    private Vector4f calculateColorChangeFromFog(Vector4f position, Vector4f pixelColor) {
        if (fogEnabled) {
            float g = 0;

            switch ((int) fogMode) {
                case GL_LINEAR:
                    g = fogEnd - Math.abs(position.z);
                    g /= (fogEnd - fogStart);
                    break;
                case GL_EXP:
                    g = (float) Math.exp(-1 * (fogDensity * Math.abs(position.z)));
                    break;
                case GL_EXP2:
                    double inner = fogDensity * Math.abs(position.z);
                    g = (float) Math.exp(-1 * inner * inner);
                    break;
                default:
                    break;
            }

            // g * currentColor + (1 - g) * fogColor
            // http://msdn.microsoft.com/en-us/library/windows/desktop/bb324452(v=vs.85).aspx
            Vector4f pixelColorTimesG = new Vector4f(pixelColor.x * g, pixelColor.y * g, pixelColor.z * g, 0);
            float g2 = 1 - g;
            Vector4f fogColorTimesG = new Vector4f(fogColor.x * g2, fogColor.y * g2, fogColor.z * g2, 0);

            Vector4f result = new Vector4f();
            Vector4f.add(pixelColorTimesG, fogColorTimesG, result);
            return result;
        }

        return pixelColor;
    }

    public boolean withinViewport(int x, int y) {
        return !(x < bLView.x || x >= tRView.x || y < bLView.y || y >= tRView.y);
    }

    /* *********************************************************
    *                   MY GL FUNCTIONS - LAB 2
    * *********************************************************/
    protected void doGLClearColor(float r, float g, float b, float a) {
        clearColor = new Vector4f(r, g, b, a);
    }

    protected void doGLClear(int mode) {
        if (mode == GL_COLOR_BUFFER_BIT) {
            for (int x = (int) bLView.x; x < (int) tRView.x; x++) {
                for (int y = (int) bLView.y; y < (int) tRView.y; y++) {
                    setPixel(x, y, lowestZ, clearColor.x, clearColor.y, clearColor.z);
                }
            }
        }
        if (mode == GL_DEPTH_BUFFER_BIT) {
            depthTest = false;
        }
    }

    protected void doGLBegin(int mode) {
        drawType = mode;
        pointData = new HashSet<PointData>();
        convexShapes = new ArrayList<ConvexShape>();
        pointCount = 0;

        if (mode == GL_LINES || mode == GL_LINE_STRIP || mode == GL_LINE_LOOP) {
            firstPoint = null;
        }
        if (mode == GL_TRIANGLES || mode == GL_TRIANGLE_STRIP || mode == GL_TRIANGLE_FAN) {
            firstPoint = null;
            secondPoint = null;
        }
    }

    protected void doGLVertex2i(int x, int y) {
        if (drawType == GL_POINTS) {
            if (pointRadius == 0)
                setPixel(x, y, lowestZ, currentColor.x, currentColor.y, currentColor.z);
            else {
                convexShapes.add(new BigPoint(x, y, pointRadius, currentColor));
            }
        }
        if (drawType == GL_LINES) {
            doGLLines(x, y);
        }
        if (drawType == GL_LINE_STRIP) {
            doGLLineStrip(x, y);
        }
        if (drawType == GL_LINE_LOOP) {
            doGLLineLoop(x, y);
        }
        if (drawType == GL_TRIANGLES || drawType == GL_TRIANGLE_STRIP || drawType == GL_TRIANGLE_FAN || drawType == GL_POLYGON) {
            doGLTriangles(x, y);
        }
        if (drawType == GL_QUADS || drawType == GL_QUAD_STRIP) {
            doGLQuads(x, y);
        }
    }

    protected void doGLEnd() {
        if (drawType == GL_LINE_STRIP) {
            drawPoints();
        }
        if (drawType == GL_LINE_LOOP) {
            closeLoop();
            drawPoints();
        }
        // GL_TRIANGLES(4), GL_TRIANGLE_STRIP(5), GL_TRIANGLE_FAN(6),
        // GL_QUADS(7), GL_QUAD_STRIP(8), or GL_POLYGON(9)
        if ((drawType >= 4 && drawType <= 9) || (drawType == GL_POINTS && pointRadius > 0)) {
            for (ConvexShape shape : convexShapes) {
                shape.fill(this);
            }
            firstPoint = null;
            secondPoint = null;
        }
        drawType = -1;
    }

    protected void doGLColor3f(float r, float g, float b) {
        currentColor.setX(r);
        currentColor.setY(g);
        currentColor.setZ(b);
    }

    protected void doGLPointSize(int size) {
        pointRadius = size;
    }

    protected void doGLLines(int x, int y) {
        doGLLines3D(x, y, 1, 1);
    }

    protected void doGLLines3D(float x, float y, float z, float w) {
        if (firstPoint == null) {
            firstPoint = modelToScreen(x, y, z, w);
            firstPointColor = new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1);
        } else {
            pointData = line.plotLineData(firstPoint, modelToScreen(x, y, z, w), firstPointColor, currentColor);
            Iterator iter = pointData.iterator();
            while (iter.hasNext()) {
                PointData current = (PointData) iter.next();
                setPixel(current.x, current.y, current.z, current.r, current.g, current.b);
            }
            firstPoint = null;
        }
    }

    protected void doGLLineStrip(int x, int y) {
        doGLLineStrip3D(x, y, 1);
    }

    protected void doGLLineStrip3D(float x, float y, float z) {
        if (firstPoint == null) {
            firstPoint = new Vector4f(x, y, z, 1);
        } else {
            secondPoint = new Vector4f(x, y, z, 1);
            pointData = line.plotLineData(firstPoint, secondPoint, firstPointColor, currentColor, pointData);
            firstPoint = secondPoint;
        }
        firstPointColor = new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1);
    }

    protected void doGLLineLoop(int x, int y) {
        doGLLineLoop3D(x, y, 1);
    }

    protected void doGLLineLoop3D(float x, float y, float z) {
        if (firstPoint == null) {
            firstPoint = new Vector4f(x, y, z, 1);
            initialPoint = new Vector4f(x, y, z, 1);
            initialPointColor = new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1);
        } else {
            secondPoint = new Vector4f(x, y, z, 1);
            pointData = line.plotLineData(firstPoint, secondPoint, firstPointColor, currentColor, pointData);
            firstPoint = secondPoint;
        }
        firstPointColor = new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1);
    }

    protected void closeLoop() {
        pointData = line.plotLineData(firstPoint, initialPoint, firstPointColor, initialPointColor, pointData);
    }

    protected void doGLTriangles(int x, int y) {
        doGLTriangles3D(x, y, 1, 1);
    }

    protected void doGLTriangles3D(float x, float y, float z, float w) {
        pointCount++;

        Vector4f point = modelToScreen(x, y, z, w);
        Vector4f pointBeforeP = modelToModelView(x, y, z, w);

        if (firstPoint == null) {
            firstPoint = point;
            firstPointColor = calculateNewColor(pointBeforeP);
        } else if (secondPoint == null) {
            secondPoint = point;
            secondPointColor = calculateNewColor(pointBeforeP);
        } else {
            Triangle triangle = new Triangle();

            if (drawType == GL_TRIANGLES) {
                drawNormalTriangle(point, pointBeforeP);
            } else if (drawType == GL_TRIANGLE_STRIP) {
                drawTriangleStrip(point, pointBeforeP);

            } else if (drawType == GL_TRIANGLE_FAN || drawType == GL_POLYGON) {
                drawTriangleFan(point, pointBeforeP);
            }
        }
    }

    private void drawNormalTriangle(Vector4f point, Vector4f pointBeforeP) {
        Triangle triangle = new Triangle();

        triangle.addPoint(firstPoint, firstPointColor);
        triangle.addPoint(secondPoint, secondPointColor);
        triangle.addPoint(point, calculateNewColor(pointBeforeP));
        convexShapes.add(triangle);

        // For awesome triangles - comment these two lines out
        firstPoint = null;
        secondPoint = null;
    }

    private void drawTriangleStrip(Vector4f point, Vector4f pointBeforeP) {
        Triangle triangle = new Triangle();

        // For even n, vertices n + 1, n, and n + 2 define triangle n.
        if (pointCount % 2 == 0) {
            triangle.addPoint(secondPoint, secondPointColor);
            triangle.addPoint(firstPoint, firstPointColor);
        }
        // For odd n, vertices n, n + 1, and n + 2 define triangle n.
        else {
            triangle.addPoint(firstPoint, firstPointColor);
            triangle.addPoint(secondPoint, secondPointColor);
        }
        triangle.addPoint(point, calculateNewColor(pointBeforeP));
        convexShapes.add(triangle);

        firstPoint = secondPoint;
        firstPointColor = secondPointColor;
        secondPoint = point;
        secondPointColor = calculateNewColor(pointBeforeP);
    }

    private void drawTriangleFan(Vector4f point, Vector4f pointBeforeP) {
        Triangle triangle = new Triangle();

        triangle.addPoint(firstPoint, firstPointColor);
        triangle.addPoint(secondPoint, secondPointColor);
        triangle.addPoint(point, calculateNewColor(pointBeforeP));
        convexShapes.add(triangle);

        secondPoint = point;
        secondPointColor = calculateNewColor(pointBeforeP);
    }

    protected void doGLQuads(int x, int y) {
        doGLQuads3D(x, y, 1);
    }

    /**
     * GL QUADS:
     * A quadrilateral is dened for every four vertices 4n-3, 4n-2, 4n-1, 4n;
     * each quadrilateral can be rendered directly or split into two triangles.
     * You can assume the quad vertices are specied in an order that make them convex.
     * EX: 4, 3, 2, 1
     * <p/>
     * GL QUAD STRIP:
     * A connected group of quadrilaterals, with one dened for every two vertices beyond the first two
     * (if the rst point is called 1, not 0, then for all n, the points 2n-1, 2n, 2n+2, 2n+1 make a quad);
     * each quadrilateral can be rendered directly or split into two triangles.
     * You can assume the quad strip vertices are given in an order that makes them convex.
     * Note that the order in which vertices are used to construct a quadrilateral from strip
     * data is different from that used with independent data.
     * EX: 1 2 4 3
     */
    protected void doGLQuads3D(float x, float y, float z) {
        pointCount++;

        if (firstPoint == null) {
            firstPoint = new Vector4f(x, y, z, 1);
            firstPointColor = new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1);
        } else if (secondPoint == null) {
            secondPoint = new Vector4f(x, y, z, 1);
            secondPointColor = new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1);
        } else if (thirdPoint == null) {
            thirdPoint = new Vector4f(x, y, z, 1);
            thirdPointColor = new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1);
        } else {
            Quad quad = new Quad();
            quad.addPoint(firstPoint, firstPointColor);
            quad.addPoint(secondPoint, secondPointColor);

            if (drawType == GL_QUADS) {
                quad.addPoint(thirdPoint, thirdPointColor);
                quad.addPoint(new Vector4f(x, y, z, 1), new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1));
                convexShapes.add(quad);

                firstPoint = null;
                secondPoint = null;
                thirdPoint = null;
            } else if (drawType == GL_QUAD_STRIP) {
                // EX: 1 2 4 3
                quad.addPoint(new Vector4f(x, y, z, 1), new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1));
                quad.addPoint(thirdPoint, thirdPointColor);
                convexShapes.add(quad);

                firstPoint = thirdPoint;
                firstPointColor = thirdPointColor;
                secondPoint = new Vector4f(x, y, z, 1);
                secondPointColor = new Vector4f(currentColor.x, currentColor.y, currentColor.z, 1);
                thirdPoint = null;
                thirdPointColor = null;
            }
        }
    }

    // set all pixel values listed in pointData data structure
    protected void drawPoints() {
        for (PointData current : pointData) {
            setPixel(current.x, current.y, current.z, current.r, current.g, current.b);
        }
    }

    /* *********************************************************
    *                   MY GL FUNCTIONS - LAB 3
    * *********************************************************/

    protected void doGLEnable(int property) {
        if (property == GL_DEPTH_TEST) {
            depthTest = true;
        }
        if (property == GL_MAX_CLIP_PLANES) {
            clippingEnabled = true;
        }
        if (property == GL_NORMALIZE) {
            normalize = true;
        }
        if (property == GL_LIGHTING) {
            lightingEnabled = true;
        }
        if (property == GL_COLOR_MATERIAL) {
            colorMaterialEnabled = true;
        }
        if (property >= GL_LIGHT0 && property <= GL_LIGHT7) {
            int lightPos = property - GL_LIGHT0;
            lightsEnabled[lightPos] = true;
        }
        if (property == GL_FOG) {
            fogEnabled = true;
        }
    }

    protected void doGLDisable(int property) {
        if (property == GL_DEPTH_TEST) {
            depthTest = false;
        }
        if (property == GL_MAX_CLIP_PLANES) {
            clippingEnabled = false;
        }
        if (property == GL_NORMALIZE) {
            normalize = false;
        }
        if (property == GL_LIGHTING) {
            lightingEnabled = false;
        }
        if (property == GL_COLOR_MATERIAL) {
            colorMaterialEnabled = false;
        }
        if (property >= GL_LIGHT0 && property <= GL_LIGHT7) {
            int lightPos = property - GL_LIGHT0;
            lightsEnabled[lightPos] = false;
        }
        if (property == GL_FOG) {
            fogEnabled = false;
        }
    }

    /**
     * Rather than separate calls to change different matrices, OpenGL has only one set of matrix modification calls
     * which change whichever matrix was last specified by a call to glMatrixMode.
     */
    protected void doGLMatrixMode(int mode) {
        if (mode == GL_MODELVIEW) {
            currentMatrix = modelView;
        }
        if (mode == GL_PROJECTION) {
            currentMatrix = projection;
        }
    }

    // Specifies the active viewport that is, the rectangle of pixels OpenGL should render to.
    protected void doGLViewport(int x, int y, int width, int height) {
        viewport.m00 = .5f * width;
        viewport.m11 = .5f * height;
        viewport.m30 = viewport.m00 + x;
        viewport.m31 = viewport.m11 + y;

        bLView = modelToScreen(-1, -1, 0, 1);
        tRView = modelToScreen(1, 1, 0, 1);

//        testWithinView(width, height);
//        System.out.println(viewport.toString());
    }

    // OpenGL only guarantees the ability to push 2 projection and 32 modelview matrices
    protected void doGLPushMatrix() {
        if (currentMatrix == modelView && numModelViewOnStack < 32) {
            Matrix4f temp = new Matrix4f(modelView);
            matrixStack.push(temp);
            numModelViewOnStack++;
        } else if (currentMatrix == projection && numProjectionOnStack < 2) {
            Matrix4f temp = new Matrix4f(projection);
            matrixStack.push(temp);
            numProjectionOnStack++;
        }
    }

    protected void doGLPopMatrix() {
        if (currentMatrix == modelView && numModelViewOnStack > 0) {
            modelView.load(matrixStack.pop());
            modelView.transpose(modelViewTranspose);
            numModelViewOnStack--;
        } else if (currentMatrix == projection && numProjectionOnStack > 0) {
            projection.load(matrixStack.pop());
            numProjectionOnStack--;
        }
    }

    protected void doGLVertex2f(float x, float y) {
        if (drawType == GL_LINES) {
            doGLLines3D(x, y, 1, 1);
        }
    }

    protected void doGLVertex3f(double x, double y, double z) {
        doGLVertex3f((float) x, (float) y, (float) z);
    }

    // Specifies the 4-vector point (x,y,z,1)
    protected void doGLVertex3f(float x, float y, float z) {
        doGLVertex4f(x, y, z, 1);
    }

    // Specifies the 4-vector point (x,y,z,w)
    protected void doGLVertex4f(float x, float y, float z, float w) {
        if (drawType == GL_LINE) {
            if (clippingEnabled) {

            } else {
                doGLLines3D(x, y, z, w);
            }
        }
        if (drawType == GL_TRIANGLES) {
            doGLTriangles3D(x, y, z, w);
        }
    }

    // Set current Matrix to be Identity
    protected void doGLLoadIdentity() {
        if (currentMatrix == modelView) {
            modelView = new Matrix4f();
            currentMatrix = modelView;
            modelView.transpose(modelViewTranspose);
        } else if (currentMatrix == projection) {
            projection = new Matrix4f();
            currentMatrix = projection;
        }
    }

    // Set M to be the designated matrix
    protected void doGLLoadMatrixd(float[] matrix) {
        ByteBuffer matrixBuffer = ByteBuffer.allocateDirect(16 * 4);
        matrixBuffer.order(ByteOrder.LITTLE_ENDIAN);

        for (int i = 0; i < matrix.length; i++) {
            double value = matrix[i];
            float fValue = (float) value;
            matrixBuffer.asFloatBuffer().put(i, fValue);
        }

        if (currentMatrix == modelView) {
            modelView.load(matrixBuffer.asFloatBuffer());
            modelView.transpose(modelViewTranspose);
//            System.out.println(modelView.toString());
        } else if (currentMatrix == projection) {
            projection.load(matrixBuffer.asFloatBuffer());
        }
    }

    // Set M to be M multiplied by designated matrix
    protected void doGLMultMatrixf(float[] matrix) {
        Matrix4f tempMatrix = new Matrix4f();

        ByteBuffer matrixBuffer = ByteBuffer.allocateDirect(16 * 4);
        matrixBuffer.order(ByteOrder.LITTLE_ENDIAN);

        for (int i = 0; i < matrix.length; i++) {
            double value = matrix[i];
            float fValue = (float) value;
            matrixBuffer.asFloatBuffer().put(i, fValue);
        }

        tempMatrix.load(matrixBuffer.asFloatBuffer());
//        System.out.println("TEMP:\n" + tempMatrix.toString());

        if (currentMatrix == modelView) {
            Matrix4f.mul(modelView, tempMatrix, modelView);
            modelView.transpose(modelViewTranspose);
//            System.out.println("NEW MODELVIEW:\n" + modelView.toString());
        } else if (currentMatrix == projection) {
            Matrix4f.mul(projection, tempMatrix, projection);
        }
    }

    protected void doGLRotatef(float angDegrees, float x, float y, float z) {
        float angleRadians = (float) Math.toRadians(angDegrees);
        float cosine = (float) Math.cos(angleRadians);
        float sine = (float) Math.sin(angleRadians);

        if (x == 1) {
            float[] rotate = {
                    1, 0, 0, 0,
                    0, cosine, sine, 0,
                    0, -1 * sine, cosine, 0,
                    0, 0, 0, 1};
            doGLMultMatrixf(rotate);
        } else if (y == 1) {
            float[] rotate = {
                    cosine, 0, -1 * sine, 0,
                    0, 1, 0, 0,
                    sine, 0, cosine, 0,
                    0, 0, 0, 1};
            doGLMultMatrixf(rotate);
        } else if (z == 1) {
            float[] rotate = {
                    cosine, sine, 0, 0,
                    -1 * sine, cosine, 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1};
            doGLMultMatrixf(rotate);
        }

    }

    protected void doGLTranslatef(float tx, float ty, float tz) {
        float[] translate = {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                tx, ty, tz, 1};

        doGLMultMatrixf(translate);
    }

    protected void doGLScalef(float sx, float sy, float sz) {
        float[] scale = {
                sx, 0, 0, 0,
                0, sy, 0, 0,
                0, 0, sz, 0,
                0, 0, 0, 1};

        doGLMultMatrixf(scale);
    }

    // translates and scales so that the given box becomes [-1, 1] in each direction.
    protected void doGLOrtho(float left, float right, float bottom, float top, float near, float far) {
        float a = 2.0f / (right - left);
        float b = 2.0f / (top - bottom);
        float c = -2.0f / (far - near);

        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(far + near) / (far - near);

        float[] ortho = {
                a, 0, 0, 0,
                0, b, 0, 0,
                0, 0, c, 0,
                tx, ty, tz, 1
        };

        doGLMultMatrixf(ortho);
    }

    // http://cs.fit.edu/~wds/classes/cse5255/thesis/shear/shear.html
    protected void shear(float sxy, float sxz, float syx, float syz, float szx, float szy) {
        float[] shear = {
                1, syx, szx, 0,
                sxy, 1, szy, 0,
                sxz, syz, 1, 0,
                0, 0, 0, 1};

        if (drawMode == 1) {
            // Add to the stack
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(16 * 4);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

            FloatBuffer shearBuffer = byteBuffer.asFloatBuffer().put(shear, 0, 16);
            shearBuffer.rewind();

            glMultMatrix(shearBuffer);
        } else {
            doGLMultMatrixf(shear);
        }
    }

    /**
     * This method, from the GLU library which extends OpenGL and is included with virtually every
     * OpenGL distribution, is essentially your "camera" method.
     * It defines a camera which has it's lens at (ex,ey,ez),
     * is looking toward (cx,cy,cz),
     * and is oriented so that it's up vector is as close to (ux,uy,uz) as it can be.
     * <p/>
     * Thus, you are to modify the currently active matrix with a rotation matrix such that
     * points previously at (ex,ey,ez) are moved to (0,0,0);
     * points previously along the line between (ex,ey,ez) and (cx,cy,cz) are moved to (0,0,-d)
     * where d is their former distance from
     * (ex,ey,ez), etc.
     *
     * @param ex - cameraEyeX
     * @param ey - cameraEyeY
     * @param ez - cameraEyeZ
     * @param cx - lookAtX
     * @param cy - lookAtY
     * @param cz - lookAtZ
     * @param ux - upVectorX
     * @param uy - upVectorY
     * @param uz - upVectorZ
     */
    protected void doGluLookAt(float ex, float ey, float ez, float cx, float cy, float cz, float ux, float uy, float uz) {
        // http://www.gamedev.net/topic/383042-what-does-glulookat-exactly-do-for-us/
        // http://www.khronos.org/message_boards/showthread.php/4991-The-Solution-for-gluLookAt()-Function!!!!
        Vector3f viewingVector = new Vector3f();
        Vector3f upVector = new Vector3f();

        // calculating the viewing vector - Where the camera is looking
        viewingVector.x = cx - ex;
        viewingVector.y = cy - ey;
        viewingVector.z = cz - ez;

        // Up Vector
        upVector.x = ux;
        upVector.y = uy;
        upVector.z = uz;

        float vectorMagnitude = (float) Math.sqrt(viewingVector.x * viewingVector.x + viewingVector.y * viewingVector.y + viewingVector.z * viewingVector.z);
        float upVectorMagnitude = (float) Math.sqrt(upVector.x * upVector.x + upVector.y * upVector.y + upVector.z * upVector.z);

        // normalizing the viewing vector
        if (vectorMagnitude != 0) {
            viewingVector.x = viewingVector.x / vectorMagnitude;
            viewingVector.y = viewingVector.y / vectorMagnitude;
            viewingVector.z = viewingVector.z / vectorMagnitude;
        }

        // normalising the up vector. no need for this here if you have your
        // up vector already normalised, which is mostly the case.
        if (upVectorMagnitude != 0) {
            upVector.x = upVector.x / upVectorMagnitude;
            upVector.y = upVector.y / upVectorMagnitude;
            upVector.z = upVector.z / upVectorMagnitude;
        }

        // Side Vector = lookVector x upVector
        Vector3f sideVector = new Vector3f();

        // sideVector x lookVector = UpVector (guaranteed axes are orthogonal)
        Vector3f recalculatedUpVector = new Vector3f();

        Vector3f.cross(viewingVector, upVector, sideVector);
        Vector3f.cross(sideVector, viewingVector, recalculatedUpVector);

        float[] M = {
                sideVector.x, sideVector.y, sideVector.z, 0,
                recalculatedUpVector.x, recalculatedUpVector.y, recalculatedUpVector.z, 0,
                -viewingVector.x, -viewingVector.y, -viewingVector.z, 1};

        doGLMultMatrixf(M);
        doGLTranslatef(-ex, -ey, -ez);
    }

    protected Vector4f modelToScreen(float x, float y, float z, float w) {
        Vector4f screenPoint = new Vector4f();
        Matrix4f modelViewThenProjection = new Matrix4f();

        // P * M
        Matrix4f.mul(projection, modelView, modelViewThenProjection);

        // P * M * point
        Matrix4f.transform(modelViewThenProjection, new Vector4f(x, y, z, w), screenPoint);

        // 1/w * screenPoint
        screenPoint.x = screenPoint.x / screenPoint.w;
        screenPoint.y = screenPoint.y / screenPoint.w;
        screenPoint.z = screenPoint.z / screenPoint.w;
        screenPoint.w = 1;

        // V * screenPoint
        Matrix4f.transform(viewport, screenPoint, screenPoint);

        return screenPoint;
    }

    private Vector4f modelToModelView(float x, float y, float z, float w) {
        Vector4f screenPoint = new Vector4f();

        // M * point
        Matrix4f.transform(modelView, new Vector4f(x, y, z, w), screenPoint);

        return screenPoint;
    }

    protected Vector4f modelToScreenNormal() {
        Vector4f newNormal = new Vector4f();
        Matrix4f.transform(modelViewTranspose, normal, newNormal);

        if (normalize) {
            return new Vector4f(newNormal.x / newNormal.length(), newNormal.y / newNormal.length(), newNormal.z / newNormal.length(), 0);
        }

        return newNormal;
    }

    protected Vector4f modelToProjection(float x, float y, float z, float w) {
        Vector4f projectionPoint = new Vector4f();
        Matrix4f modelViewThenProjection = new Matrix4f();

        // P * M
        Matrix4f.mul(projection, modelView, modelViewThenProjection);

        // P * M * point
        Matrix4f.transform(modelViewThenProjection, new Vector4f(x, y, z, w), projectionPoint);

        return projectionPoint;
    }

    protected Vector4f projectionToScreen(float x, float y, float z, float w) {
        Vector4f screenPoint = new Vector4f(x, y, z, w);

        // 1/w * screenPoint
        screenPoint.x = screenPoint.x / screenPoint.w;
        screenPoint.y = screenPoint.y / screenPoint.w;
        screenPoint.z = screenPoint.z / screenPoint.w;
        screenPoint.w = 1;

        // V * screenPoint
        Matrix4f.transform(viewport, screenPoint, screenPoint);

        return screenPoint;
    }

    // Get point (assuming w = 1) back to point where it was just multiplied by M
    protected Vector4f ScreenToModelview(float x, float y, float z) {
        Vector4f point = new Vector4f();
        Matrix4f inverse = new Matrix4f();

        Matrix4f invertedProjection = new Matrix4f();
        Matrix4f invertedViewport = new Matrix4f();

        Matrix4f.invert(projection, invertedProjection);
        Matrix4f.invert(viewport, invertedProjection);

        // P-1 * v^-1 - This assumes that w = 1
        Matrix4f.mul(invertedProjection, invertedViewport, inverse);

        Matrix4f.transform(inverse, new Vector4f(x, y, z, 1), point);

        return point;
    }

    protected void doGLFrustumD(double left, double right, double bottom, double top, double near, double far) {
        doGLFrustumF((float) left, (float) right, (float) bottom, (float) top, (float) near, (float) far);
    }

    // http://msdn.microsoft.com/en-us/library/dd373537(v=VS.85).aspx
    protected void doGLFrustumF(float left, float right, float bottom, float top, float near, float far) {
        /* [A 0  B 0]
         * [0 C  D 0]
         * [0 0  E F]
         * [0 0 -1 0]
         */

        float A = (2 * near) / (right - left);
        float B = (right + left) / (right - left);
        float C = (2 * near) / (top - bottom);
        float D = (top + bottom) / (top - bottom);
        float E = -1 * (far + near) / (far - near);
        float F = (-2 * far * near) / (far - near);

        float[] frustrumMatrix = {
                A, 0, 0, 0,
                0, C, 0, 0,
                B, D, E, -1,
                0, 0, F, 0};

        doGLMultMatrixf(frustrumMatrix);
    }

    // http://www.opengl.org/wiki/GluPerspective_code
    protected void doGluPerspective(float fov_y, float aspect, float near, float far) {
        float ymax = near * (float) Math.tan(fov_y * Math.PI / 360.0);

        //ymin = -ymax;
        //xmin = -ymax * aspectRatio;
        float xmax = ymax * aspect;
        doGLFrustumF(-xmax, xmax, -ymax, ymax, near, far);
    }

    protected void doGLNormal3f(float x, float y, float z) {
        normal = new Vector4f(x, y, z, 0);
    }

    protected void doGLLight(int light, int type, float[] value) {
        if (light >= GL_LIGHT0 && light <= GL_LIGHT7) {
            int lightIndex = light - GL_LIGHT0;
            switch (type) {
                case GL_DIFFUSE:
                    diffuseColors.put(lightIndex, new Vector4f(value[0], value[1], value[2], value[3]));
                    break;
                case GL_AMBIENT:
                    ambientColors.put(lightIndex, new Vector4f(value[0], value[1], value[2], value[3]));
                    break;
                case GL_SPECULAR:
                    specularColors.put(lightIndex, new Vector4f(value[0], value[1], value[2], value[3]));
                    break;
                case GL_POSITION:
                    lightPositions.put(lightIndex, new Vector4f(value[0], value[1], value[2], value[3]));
                    break;
            }
        }
    }

    protected void doGLFog(int parameter, float[] value) {
        if (parameter == GL_FOG_COLOR) {
            fogColor = new Vector4f(value[0], value[1], value[2], value[3]);
        }
    }

    protected void doGLFogf(int parameter, float value) {
        switch (parameter) {
            case GL_FOG_MODE:
                fogMode = value;
                break;
            case GL_FOG_START:
                fogStart = value;
                break;
            case GL_FOG_END:
                fogEnd = value;
                break;
            case GL_FOG_DENSITY:
                fogDensity = value;
                break;
            default:
                break;
        }
    }

    protected void doGLMaterial(int face, int parameter, float[] value) {
        if (face == GL_FRONT_AND_BACK) {
            if (parameter == GL_SPECULAR) {
                specularSurfaceColor = new Vector4f(value[0], value[1], value[2], value[3]);
            }
        }
    }

    protected void doGLMaterialf(int face, int parameter, float value) {
        if (face == GL_FRONT_AND_BACK) {
            if (parameter == GL_SHININESS) {
                materialShininess = value;
            }
        }
    }
}