package bg.tu_varna.sit.f24621688.commands.other;

import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.contracts.Command;

import java.util.Map;

/**
 * Executes the help command.
 * Prints all supported commands and their syntax.
 */
public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * Executes the help command.
     *
     * @param args not used
     * @return the full help menu
     */
    @Override
    public CommandResult execute(String[] args) {
        String msg = "\n=========================================\n"
                + "  Student Information System - Commands\n"
                + "=========================================\n\n"
                + "FILE:\n"
                + "  open <file.xml>      Opens an XML file\n"
                + "  save                 Saves current file\n"
                + "  saveas <file.xml>    Saves as new file\n"
                + "  close                Closes current file\n\n"
                + "SPECIALTY:\n"
                + "  addspecialty \"<name>\" [minCredits]   Add specialty\n"
                + "  listspecialties                      List all specialties\n"
                + "  removespecialty \"<name>\"             Remove specialty\n\n"
                + "DISCIPLINE:\n"
                + "  adddiscipline \"<name>\" <M|E> <credits> <year>  Add discipline\n"
                + "  listdisciplines                                  List disciplines\n"
                + "  removediscipline \"<name>\"                       Remove discipline\n\n"
                + "STUDENT:\n"
                + "  enroll <fn> \"<program>\" <group> \"<name>\"  Enroll student\n"
                + "  print <fn>                                Print student info\n"
                + "  printall \"<program>\" <year>               Print all in program\n"
                + "  advance <fn>                              Advance to next year\n"
                + "  graduate <fn>                             Graduate student\n"
                + "  interrupt <fn>                            Suspend student\n"
                + "  resume <fn>                               Resume student\n"
                + "  change <fn> <program|group|year> <val>   Change student data\n\n"
                + "GRADE:\n"
                + "  enrollin <fn> \"<discipline>\"            Enroll in discipline\n"
                + "  addgrade <fn> \"<discipline>\" <grade>    Add grade\n"
                + "  protocol \"<discipline>\"                 Show protocol\n"
                + "  report <fn>                             Show academic report\n\n"
                + "OTHER:\n"
                + "  help    This message\n"
                + "  exit    Exit program\n"
                + "=========================================\n";

        return CommandResult.success(msg);
    }
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getUsage() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Shows available commands";
    }
}
