package examples;

public class MathTask {

    public static void main(String[] args) {
        int i1 = 5;
        int i2 = 11;

        double d1 = 5.5;
        double d2 = 1.3;

        long l = 20;

        double result;

        System.out.println(d2%i1);
        System.out.println(i2/d1);

        result = i2/d1 + d2%i1 - l;

        System.out.println(result);

    }

}
