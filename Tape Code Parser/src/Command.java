/**
 * TODO: later use this instead of map for more helpful error messages
 */
public class Command {
    private String assembyName;
    private String binCode;
    private String englishName;
    private CommandType commandType;
    private String description;

    //=============================CONSTRUCTOR==================================//

    public Command(String assembyName, String binCode, String englishName, CommandType commandType, String description) {
        this.assembyName = assembyName;
        this.binCode = binCode;
        this.englishName = englishName;
        this.commandType = commandType;
        this.description = description;
    }


    //=====================================GETTERS================================//


    public String getBinCode() {
        return binCode;
    }

    public String getAssembyName() {
        return assembyName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getDescription() {
        return description;
    }
}
