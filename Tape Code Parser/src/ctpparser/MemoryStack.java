package ctpparser;

/**
 * Utility class to manage memory allocation
 *
 * always has the form
 * -special no allocat cells-   ---allocatable memory---
 * [][][][][][][][][][][]...    [][][][][][][][][][][][][]...
 */
public class MemoryStack {
    private static boolean[] memoryUsed;
    private static int numSpecialCells;

    public static void createStack(int size, int numSpecialCells)
    {
        MemoryStack.numSpecialCells = numSpecialCells;
        memoryUsed = new boolean[size - numSpecialCells];
        for (int i = 0; i < memoryUsed.length; i++) {
            memoryUsed[i] = false;
        }
    }

    public static int allocate() throws OutOfMemoryException
    {
        for (int i = 0; i < memoryUsed.length; i++) {
            if(!memoryUsed[i])
            {
                memoryUsed[i] = true;
                return i + numSpecialCells;
            }
        }
        throw new OutOfMemoryException("All " + memoryUsed.length + " allocatable memory cells are in use");
    }

    public static void free(int adrs) throws InvalidMemoryAddressException
    {
        if(adrs <= numSpecialCells - 1 ) throw new InvalidMemoryAddressException(adrs + " cannot be freed because it is not allocatable");
        if(adrs >= memoryUsed.length + numSpecialCells) throw new InvalidMemoryAddressException(adrs + " is not valid memory address");
        memoryUsed[adrs - numSpecialCells] = false;
    }
}
