/**
 * Represents a command that supports undo functionality.
 * Classes implementing this interface are expected to encapsulate an action
 * that can be reversed at a later time.
 * A previous state is required.
 */
public interface UndoableCommand
{
    void undo();
}
