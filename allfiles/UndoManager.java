import java.util.Stack;

/**
 * Manages undoable commands using a simple stack structure.
 */
public class UndoManager
{
    private static final Stack<UndoableCommand> stack = new Stack<>();

    /**
     * Adds a new undo command.
     * @param cmd the command to be stored
     */
    public static void add(UndoableCommand cmd)
    {
        if (cmd != null)
        {
            stack.push(cmd);
        }
    }

    /**
     * Undoes the last stored command.
     */
    public static void undoLast()
    {
        if (stack.isEmpty())
        {
            System.out.println("Nothing to undo.");
            return;
        }

        UndoableCommand cmd = stack.pop();
        cmd.undo();
    }

    /**
     * Returns the number of pending undo operations.
     * @return size of undo stack
     */
    public static int pendingCount()
    {
        return stack.size();
    }
}

