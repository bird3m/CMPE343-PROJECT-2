import java.util.Stack;

public class UndoManager
{
    private static final Stack<UndoableCommand> stack = new Stack<>();

    // Yeni bir undo komutu ekle
    public static void add(UndoableCommand cmd)
    {
        if (cmd != null)
        {
            stack.push(cmd);
        }
    }

    // Son işlemi geri al
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

    // (Opsiyonel) kaç tane bekleyen undo var görmek istersen
    public static int pendingCount()
    {
        return stack.size();
    }
}
