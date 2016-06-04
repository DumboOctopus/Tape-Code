package ctpparser.test;

import ctpparser.InvalidMemoryAddressException;
import ctpparser.MemoryStack;
import ctpparser.OutOfMemoryException;

/**
 * Created on 6/3/16.
 */
public class TestMemoryStack {
    public static void main(String[] args)
    {
        MemoryStack.createStack(8, 4);
        try {
            System.out.println(MemoryStack.allocate());
            System.out.println(MemoryStack.allocate());
            System.out.println(MemoryStack.allocate());
            System.out.println(MemoryStack.allocate());
            MemoryStack.free(7);
            System.out.println(MemoryStack.allocate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
