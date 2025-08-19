
public enum CommandData {
    ADD, LIST, EXIT, UPDATE, FILTER, UNKNOWN;

    public static CommandData of(String input) {
        for (CommandData command : CommandData.values()) {
            if (command.name().equals(input)) {
                return command;
            }
        }
        return UNKNOWN;
    }
}