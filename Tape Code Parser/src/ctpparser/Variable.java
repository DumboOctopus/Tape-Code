package ctpparser;

import java.util.ArrayList;

/**
 * Created on 6/3/16.
 */
public class Variable {
    private static ArrayList<Variable> variables;


    //=============================INSTANCE FIELDS=============================//
    private String name;
    private int adrs;


    private Variable(String name, int adrs)
    {
        this.name = name;
        this.adrs = adrs;
    }

    public String getName() {
        return name;
    }

    public int getAdrs() {
        return adrs;
    }

    //================================UTIL=====================================//
    public static void setUp()
    {
        variables = new ArrayList<>();
    }
    public static Variable createVariable(String name) throws OutOfMemoryException
    {
        variables.add( new Variable(name, MemoryStack.allocate()));
        return variables.get(variables.size() - 1);
    }

    public static Variable get(String name) throws NoSuchVariableException
    {
        for (Variable v: variables) {
            if(v.getName().equals(name)) return v;
        }
        throw new NoSuchVariableException(name + " is not a variable name");
    }

}
