package spacemadness.com.lunarconsole.console;

import java.util.ArrayList;
import java.util.List;

import spacemadness.com.lunarconsole.TestCaseEx;

import static spacemadness.com.lunarconsole.console.Console.Options;

public class ConsoleTest extends TestCaseEx implements LunarConsoleListener
{
    public void testLogMessages()
    {
        Console console = createConsole(5);

        console.logMessage("1", "", (byte) 0);
        console.logMessage("2", "", (byte) 0);
        console.logMessage("3", "", (byte) 0);
        console.logMessage("4", "", (byte) 0);
        console.logMessage("5", "", (byte) 0);

        assertResult(console, "1", "2", "3", "4", "5");
        assertResult("add: 1", //
                "add: 2", //
                "add: 3", //
                "add: 4", //
                "add: 5"  //
        );

        console.logMessage("6", "", (byte) 0);
        assertResult(console, "2", "3", "4", "5", "6");
        assertResult(
                "remove: 0,1", //
                "add: 6"       //
        );

        console.logMessage("7", "", (byte) 0);
        assertResult(console, "3", "4", "5", "6", "7");
        assertResult(
                "remove: 0,1", //
                "add: 7"       //
        );

        console.logMessage("8", "", (byte) 0);
        assertResult(console, "4", "5", "6", "7", "8");
        assertResult(
                "remove: 0,1", //
                "add: 8"       //
        );
    }

    public void testLogMessagesFreeBlock()
    {
        Console console = createConsole(5, 3);

        console.logMessage("1", "", (byte) 0);
        console.logMessage("2", "", (byte) 0);
        console.logMessage("3", "", (byte) 0);
        console.logMessage("4", "", (byte) 0);
        console.logMessage("5", "", (byte) 0);

        assertResult(console, "1", "2", "3", "4", "5");
        assertResult("add: 1", //
                "add: 2", //
                "add: 3", //
                "add: 4", //
                "add: 5"  //
        );

        console.logMessage("6", "", (byte) 0);
        assertResult(console, "4", "5", "6");
        assertResult(
                "remove: 0,3", //
                "add: 6"       //
        );

        console.logMessage("7", "", (byte) 0);
        assertResult(console, "4", "5", "6", "7");
        assertResult("add: 7");

        console.logMessage("8", "", (byte) 0);
        assertResult(console, "4", "5", "6", "7", "8");
        assertResult("add: 8");

        console.logMessage("9", "", (byte) 0);
        assertResult(console, "7", "8", "9");
        assertResult(
                "remove: 0,3", //
                "add: 9"       //
        );

        console.logMessage("10", "", (byte) 0);
        assertResult(console, "7", "8", "9", "10");
        assertResult("add: 10");

        console.logMessage("11", "", (byte) 0);
        assertResult(console, "7", "8", "9", "10", "11");
        assertResult("add: 11");

        console.logMessage("12", "", (byte) 0);
        assertResult(console, "10", "11", "12");
        assertResult(
                "remove: 0,3", //
                "add: 12"       //
        );
    }

    public void testClear()
    {
        Console console = createConsole(5);

        console.logMessage("1", "", (byte) 0);
        console.logMessage("2", "", (byte) 0);
        console.logMessage("3", "", (byte) 0);
        console.logMessage("4", "", (byte) 0);
        console.logMessage("5", "", (byte) 0);
        console.clear();

        assertResult(console);
        assertResult("add: 1", "add: 2", "add: 3", "add: 4", "add: 5", "clear");

        console.logMessage("1", "", (byte) 0);
        console.logMessage("2", "", (byte) 0);
        console.logMessage("3", "", (byte) 0);
        console.logMessage("4", "", (byte) 0);
        console.logMessage("5", "", (byte) 0);

        assertResult(console, "1", "2", "3", "4", "5");
        assertResult("add: 1", //
                "add: 2", //
                "add: 3", //
                "add: 4", //
                "add: 5"  //
        );

        console.logMessage("6", "", (byte) 0);
        assertResult(console, "2", "3", "4", "5", "6");
        assertResult(
                "remove: 0,1", //
                "add: 6"       //
        );

        console.logMessage("7", "", (byte) 0);
        assertResult(console, "3", "4", "5", "6", "7");
        assertResult(
                "remove: 0,1", //
                "add: 7"       //
        );

        console.logMessage("8", "", (byte) 0);
        assertResult(console, "4", "5", "6", "7", "8");
        assertResult(
                "remove: 0,1", //
                "add: 8"       //
        );
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers

    private Console createConsole(int capacity)
    {
        return createConsole(capacity, 1);
    }

    private Console createConsole(int capacity, int overflow)
    {
        Options options = new Options(capacity);
        options.setOverflowFreeAmount(overflow);
        Console console = new Console(options);
        console.setConsoleListener(this);
        return console;
    }

    private void assertResult(Console console, String... expected)
    {
        List<String> actual = new ArrayList<>();
        for (int i = 0; i < console.entriesCount(); ++i)
        {
            actual.add(console.entryAtIndex(i).message);
        }

        assertResult(actual, expected);
    }

    @Override
    protected void assertResult(String... expected)
    {
        super.assertResult(expected);
        clearResult();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LunarConsoleListener

    @Override
    public void onAddEntry(Console console, ConsoleEntry entry, boolean filtered)
    {
        addResult("add: " + entry.message);
    }

    @Override
    public void onRemoveEntries(Console console, int start, int length)
    {
        addResult("remove: " + start + "," + length);
    }

    @Override
    public void onClearEntries(Console console)
    {
        addResult("clear");
    }
}