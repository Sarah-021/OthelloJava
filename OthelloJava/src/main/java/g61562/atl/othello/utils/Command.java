package g61562.atl.othello.utils;

import g61562.atl.othello.model.Color;

/**
 * The Command interface represents an action that can be executed and undone.
 * Classes implementing this interface must provide methods to execute the command
 * and to undo the effects of the executed command.
 */
public interface Command {

    /**
     * Executes the command, applying its effect.
     */
    void execute();

    /**
     * Undoes the effects of the executed command, reverting it to its previous state.
     * This method should reverse the changes made by the execute() method.
     */
    void unexecute();

    void reexecute();

}
