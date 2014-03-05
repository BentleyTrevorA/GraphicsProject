import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Stack;

public class DataStructures
{
    // Read and write matrices and vectors to simple arrays of floats do matrices in column major format. Thus,
    public void runAll() {
        Vector4f a = new Vector4f(5,4,3,2);
        Vector4f b = new Vector4f(6,7,8,9);
        System.out.println("Using Vectors");
        System.out.println(a);
        System.out.println(b);

        Matrix4f matrixA = new Matrix4f();
        ByteBuffer stuff = ByteBuffer.allocateDirect(16 * 4); // IMPORTANT: Have to multiply stuff size by 4 to fit floats
        stuff.order(ByteOrder.LITTLE_ENDIAN);
        for(int i=1; i<17; i++) {
            stuff.putFloat((float) i);
        }
        stuff.rewind();
        matrixA.load(stuff.asFloatBuffer());

        multiplyVectorByNumber(a, 3);
        addAndSubtractVectors(a, b);
        dotProductVectors(a, b);
        multiplyMatrices(a);
        invertMatrix(matrixA);
        stackMatrix(matrixA);
//        crossProduct(a, b, a);
    }

    private void multiplyVectorByNumber(Vector4f vector, float num) {
        System.out.println("\nMultiplying by " + num);
        System.out.println(vector.scale(num));
        vector.scale(1/num);
    }

    private void addAndSubtractVectors(Vector4f a, Vector4f b){
        Vector4f c = new Vector4f();

        Vector4f.add(a,b,c);
        System.out.println("\nAdding");
        System.out.println(c);

        Vector4f.sub(b,a,c);
        System.out.println("\nSubtracting");
        System.out.println(c);
    }

    private void dotProductVectors(Vector4f a, Vector4f b) {
        System.out.println("\nDot product: " + Vector4f.dot(a, b));
    }

    private void multiplyMatrices(Vector4f vector) {
        Matrix4f a = new Matrix4f();
        ByteBuffer stuff = ByteBuffer.allocateDirect(16 * 4); // IMPORTANT: Have to multiply stuff size by 4 to fit floats
        stuff.order(ByteOrder.LITTLE_ENDIAN);
        for(int i=0; i<16; i++) {
            stuff.putFloat((float) i);
        }
        stuff.rewind();
        a.load(stuff.asFloatBuffer());

        Matrix4f b = new Matrix4f(a);
        Matrix4f c = new Matrix4f();
        Matrix4f.mul(a, b, c);
        System.out.println("\nMultiply Matrix by Matrix");
        System.out.println(c);


        Vector4f result = new Vector4f();
        Matrix4f.transform(a,vector,result);
        System.out.println("\nMultiply Matrix by vector");
        System.out.println(a);
        System.out.println(vector);
        System.out.println("Result");
        System.out.println(result);
    }

    private void invertMatrix(Matrix4f matrix) {
        System.out.println("\nInvert Matrix");

        System.out.println(matrix.invert());
    }

    private void stackMatrix(Matrix4f matrix) {
        Stack<Matrix4f> matrixStack = new Stack<Matrix4f>();
        matrixStack.push(matrix);
        matrixStack.pop();
    }

    private void crossProduct(Vector3f a, Vector3f b){
//        Vector3f.cross(a)
    }
}
