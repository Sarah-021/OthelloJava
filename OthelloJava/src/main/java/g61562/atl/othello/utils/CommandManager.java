package g61562.atl.othello.utils;


import java.util.Stack;

/**
 * The CommandManager class manages the execution, undoing, and redoing of commands.
 * It maintains separate stacks for undo and redo operations.
 */
public class CommandManager {

    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    /**
     * Adds a command to be executed. Executes the command and adds it to the undo stack.
     *
     * @param command The command to be executed.
     */
    public void add(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    /**
     * Undoes the most recent command by retrieving it from the undo stack,
     * unexecuting it, and adding it to the redo stack.
     */
    public void undo() {
        if (!undoStack.empty()) {
            Command command = undoStack.pop();
            command.unexecute();
            redoStack.push(command);
        }
    }

    /**
     * Redoes the most recently undone command by retrieving it from the redo stack,
     * executing it, and adding it back to the undo stack.
     */
    public void redo() {
        if (!redoStack.empty()) {
            Command command = redoStack.pop();
            command.reexecute();
            undoStack.push(command);
        }
    }
}
