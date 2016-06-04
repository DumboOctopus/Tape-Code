
import ctpparser.MemoryStack;
import ctpparser.Variable;

import java.io.*;

public class Parser{

    private static Command[] cmds;
    private static boolean noErrors = true;
    private static String nameOfOutFile = "a";

    //todo add -v option
    public static void main(String[] args)  {
        //init cmdToBinMap
        cmds = new Command[]{
              new Command("radrs","0000", "Set Read Address", CommandType.CMD_NUM, "Sets the address of memory that comp will read from. Use alongside trans."),
              new Command("wadrs", "0001", "Set Write Address", CommandType.CMD_NUM, "Sets the address of memory to which the comp will do any writing operations. Used alongside wl and trans"),
              new Command("wl", "0010", "Write Literal", CommandType.CMD_NUM, "Writes the argument to the memory address the comp is currently writing to (set by wadrs)."),
              new Command("trans", "0011", "Transfer", CommandType.CMD, "Copies data from writing address to the reading address. Used alongside radrs and wadrs"),
              new Command("goto", "0101", "Goto Line", CommandType.CMD_NUM, "Goes to the label specified by the argument"),
              new Command("add", "0110", "Add", CommandType.CMD, "Adds the values in memory address 0 and memory address 1"),
              new Command("sub", "0111", "Subtract", CommandType.CMD, "Subtracts the values in memory address 0 and memory address 1"),
              new Command("!", "1000", "NOT", CommandType.CMD, "Preforms a bitwise NOT on the contents of memory address 0"),
              new Command("&", "1001", "AND", CommandType.CMD, "Preforms a bitwise AND using memory address 0 and memory address 1"),
              new Command("|", "1010", "OR", CommandType.CMD, "Preforms a bitwise OR using memory address 0 and memory address 1"),
              new Command("!^", "1011", "XNOR", CommandType.CMD, "Preforms a bitwise XNOR using memory address 0 and memory address 1"),
              new Command("oadd", "1100", "Add Return Overflow", CommandType.CMD, "Adds the values in memory address 0 and memory address 1 and returns 1111 if overflow occurs, 0000 if not"),
              new Command("osub", "1101", "Subtract Return Underflow", CommandType.CMD, "Subtracts the values in memory address 0 and memory address 1 and returns 1111 if underflow occurs, 0000 if not"),
              new Command("setif", "1110", "Set \"If\" Label", CommandType.CMD_NUM, "Sets the Label that represents the end of an if statement. Called before doif"),
              new Command("doif", "1111", "Set \"If\" Address and Execute", CommandType.CMD_NUM, "Set the address of memory that the if will read and executes the if. The if will either jump to the Label (if not 1111) or execute the code inside it (if 1111)")
        };

        if(args.length == 0)
        {
            showUsage();
        } else
        {
            for(String arg: args)
            {
                if(arg.contains("-"))
                {
                    if(!applyOptions(arg)){
                        showError(arg+" is not a valid option");
                        return;
                    }

                    continue;
                }

                File f = new File(arg);
                if(!f.exists()) showError(f.getName() + " cannot be found");


                try {
                    if(f.getName().substring(f.getName().lastIndexOf(".")).equals(".atp"))
                        parseATPFile(f);
                    else
                        showError("File suffix is invalid. Must be .atp");
                    nameOfOutFile = arg.substring(0, arg.indexOf("."));
                } catch (FileNotFoundException e) {
                    showError(f.getName() + " can not be read");
                } catch (IOException e)
                {
                    showError(f.getName() + " had a IO exception occured");
                }
            }
        }
    }

    public static void parseATPFile(File f) throws FileNotFoundException,IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));

        String line =null;
        StringBuilder builder = new StringBuilder();
        int lineNumber = 1;
        while((line = reader.readLine()) != null)
        {
            try {
                //preprocessing line
                while(line.contains(";")) line = line.substring(0, line.indexOf(";"));
                line = line.replace("\t", "");


                //if its a label
                if(line.replace(" ", "").replace("\n", "").length() == 0)
                {
                    continue;
                }
                else if (line.contains(":")) {
                    String number = getBinaryString(Integer.parseInt(line.substring(0, line.indexOf(":"))));
                    builder.append("10100").append(number);
                } else {
                    //everything has the format:
                    //cmd number
                    //or
                    //cmd
                    String[] arr = line.split(" ");
                    Command currCommand = getCommandFromAssembyName(arr[0]);

                    if(currCommand == null)
                        showError(lineNumber, line, arr[0] + " is not a valid command");
                    else {
                        if (currCommand.getCommandType() == CommandType.CMD_NUM) {
                            //cmd number
                            try {
                                int rawNumber = Integer.parseInt(arr[1]);
                                if (rawNumber < 0 || rawNumber > 15)
                                    showError(lineNumber, line, rawNumber + " is not a valid argument");
                                String number = getBinaryString(Integer.parseInt(arr[1]));
                                //                builder.append("1"+ command + number);
                                builder.append("1" + currCommand.getBinCode() + number);
                            } catch (ArrayIndexOutOfBoundsException ae) {
                                showError(lineNumber, line, arr[0] + " requires a valid integer argument");
                            } catch(NumberFormatException ne) {
                                showError(lineNumber, line, "\'"+arr[1] + "\' is not a vallid integer argument. Be sure only 1 space is between the command and its argument");
                            }
                        } else {

                            builder.append("1" + currCommand.getBinCode() + "0000");
                        }
                    }
                }
                lineNumber ++;
                builder.append("\n");
            } catch(Exception e)
            {
                showError(lineNumber, line, "unknown error has occured. Details: "+ e.getCause() + e.getMessage());

            }

        }
        reader.close();


        if(noErrors) {
            //take line, turn it from english to tape code then add to builder
            //then writer.write(builder.toString(), don't append);
            File outFile = new File(nameOfOutFile+".tp");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            writer.write(builder.toString());
            writer.close();
            System.out.println(outFile.getName() + " created :)");
        }else
        {
            System.out.println("build unsucessful :(");
        }
    }

    public static void parseCTPFile(File f) throws FileNotFoundException,IOException
    {
        //EXTREMMELY COMPLEX THING
        //TODO wait on how alu pin is read so i can construct expressions
        MemoryStack.createStack(16, 4);
        Variable.setUp();

    }

    //-----------------helper
    //can return null
    private static Command getCommandFromAssembyName(String assembyName)
    {
        for(Command cmd: cmds)
        {
            if(cmd.getAssembyName().equals(assembyName)) return cmd;
        }
        return null;
    }

    public static String getBinaryString(int x)
    {
        String out = Integer.toBinaryString(x);
        for(int i = 0, len = out.length(); i < 4- len; i++)
            out = "0" + out;
        return out;
    }

    //UESR output helpers
    private static void showUsage()
    {
        System.out.println("Usage: \tjava -jar tapecode.jar [-options] [files...");
        System.out.println("\t\t(to compile [files...)");
        System.out.println("where options include:");
        System.out.println("\t-name:<name>");
        System.out.println("\t\tset the name of the file produced. Default is [fileName].tp");

        System.out.println("Other details:");
        System.out.println("\t1. All arguments are written in decimal.\n\t\te.g: goto 4");
        System.out.println("\t2. All assembly tape code files must be suffixed with .atp\n\t\te.g: ImmaCoolKID.atp");

        System.out.println("Commands:");
        for(Command cmd: cmds)
        {
            System.out.println("\t"+cmd.getAssembyName()+" (" + cmd.getEnglishName() + "): " + cmd.getDescription() + "\n");
        }
    }

    private static void showError(int lineNum, String code, String message)
    {
        System.out.println("Error occured at line " + lineNum + "" +
                "\n\tcode was: "+code + "" +
                "\n\t"+message );
        noErrors = false;
    }

    private static void showError(String message)
    {
        System.out.println("ERROR: " + message);
        noErrors = false;
    }

    private static void showProgressMessage(String message)
    {
        System.out.println(message);
    }

    //optoins

    private static boolean applyOptions(String arg)
    {
        if(arg.contains("-name:")) {
            nameOfOutFile = arg.replace("-", "");
            nameOfOutFile = nameOfOutFile.substring(nameOfOutFile.indexOf(":") +1);
            if(nameOfOutFile.contains(".")) {
                showError("name of file cannot have . simply include name");
                return false;
            }
            return isFilenameValid(nameOfOutFile);
        }
        else
            return false;
    }


    //by Maxim Mazin on stackoverflow
    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}