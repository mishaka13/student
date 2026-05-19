package bg.tu_varna.sit.f24621688.commands.file;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.session.AppSession;

/**
 * Closes the currently open file and clears all data from the repository.
 */
public class CloseCommand extends BaseCommand {
    public CloseCommand(AppSession session) { super(session); }

    /**
     * Refuses to close if there are unsaved changes.
     *
     * @param args the command arguments (none required)
     * @return a successful result or an error if no file is open or changes are unsaved
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen())
            return CommandResult.error("No file is open.");

        if (getSession().hasUnsavedChanges())
            return CommandResult.error("You have unsaved changes! Use 'save' first.");

        String fileName = getFileName(getSession().getCurrentFilePath());
        getSession().closeFile();

        return CommandResult.success("Successfully closed " + fileName);
    }

    @Override
    public String getName() {
        return "close";
    }

    @Override
    public String getUsage() {
        return "close";
    }

    @Override
    public String getDescription() {
        return "Closes the currently open file";
    }
}

