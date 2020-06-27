/**
 * Emulating {@code org.junit.rules.ExpectedException} with the command pattern.
 */
public class Main {

    private  interface Thrower {
        void throwIt();
    }

    private static class ThrowsArrayIndexOutOfBoundsException implements  Thrower {
        @Override
        public void throwIt() {
            Object[] array = new Object[2];
            Object temp = array[3]; // Should throw an instance of ArrayIndexOutOfBoundsException.
        }
    }

    private static class ThrowsNullPointerException implements  Thrower {
        @Override
        public void throwIt() {
            Object o = null;
            o.hashCode();   // Should throw an instance of NullPointerException.
        }
    }

    private static boolean expectThrowable(Class<?> expectedClass, Thrower methodThatThrows) {
        Throwable exc = null;
        try {
            methodThatThrows.throwIt();
        } catch(Throwable thrown){
            exc = thrown;
        }
        return (exc != null && exc.getClass() == expectedClass);
    }


    public static void main(String[] args) {
        System.out.println(expectThrowable(ArrayIndexOutOfBoundsException.class, new ThrowsArrayIndexOutOfBoundsException())); // true
        System.out.println(expectThrowable(AssertionError.class, new ThrowsArrayIndexOutOfBoundsException())); // false
        System.out.println(expectThrowable(NullPointerException.class, new ThrowsNullPointerException())); // true
        System.out.println(expectThrowable(AssertionError.class, new ThrowsNullPointerException())); // false
        System.out.println(expectThrowable(Throwable.class, new ThrowsNullPointerException())); // false, too generic
        System.out.println(expectThrowable(RuntimeException.class, new ThrowsNullPointerException())); // false, also too generic
    }


}
