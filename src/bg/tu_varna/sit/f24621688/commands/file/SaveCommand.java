package bg.tu_varna.sit.f24621688.commands.file;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.file.XmlStorage;
import bg.tu_varna.sit.f24621688.session.AppSession;

import java.io.IOException;

/**
 * Saves the current data back to the open file.
 */
public class SaveCommand extends BaseCommand {
    public SaveCommand(AppSession session) { super(session); }

    /**
     * Executes the save command.
     * Resets the unsaved-changes flag on success.
     *
     * @param args the command arguments (none required)
     * @return a successful result or an error if no file is open or  fails
     */
    @Override
    public CommandResult execute(String[] args) {
        try {
            if (!getSession().isFileOpen())
                return CommandResult.error("No file is open. Use 'open' first.");

            String filepath = getSession().getCurrentFilePath();
            XmlStorage.saveAllData(getRepository(), filepath);
            getSession().setHasUnsavedChanges(false);

            return CommandResult.success("Successfully saved " + getFileName(filepath));

        } catch (IOException e) {
            return CommandResult.error("Error saving file: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getUsage() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "Saves data to the current file";
    }
}

