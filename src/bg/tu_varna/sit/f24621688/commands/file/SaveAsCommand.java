package bg.tu_varna.sit.f24621688.commands.file;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.file.XmlStorage;
import bg.tu_varna.sit.f24621688.session.AppSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Saves the current data to a new file and makes it the active file.
 */
public class SaveAsCommand extends BaseCommand {
    public SaveAsCommand(AppSession session) { super(session); }

    /**
     * @param args the command arguments; {@code args[1]} is the new file path
     * @return a successful result or an error if conditions are not met
     */
    @Override
    public CommandResult execute(String[] args) {
        try {
            if (args.length < 2) return CommandResult.error("Usage: saveas <filename.xml>");
            if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");

            String newFileName = args[1];
            if (!newFileName.toLowerCase().endsWith(".xml"))
                return CommandResult.error("Only .xml files are allowed.");

            String currentPath = getSession().getCurrentFilePath();
            String dir = "";
            if (currentPath.contains("/"))  dir = currentPath.substring(0, currentPath.lastIndexOf('/'));
            else if (currentPath.contains("\\")) dir = currentPath.substring(0, currentPath.lastIndexOf('\\'));

            String newPath = dir.isEmpty() ? newFileName : dir + "/" + newFileName;

            if (!dir.isEmpty() && !Files.exists(Paths.get(dir)))
                Files.createDirectories(Paths.get(dir));

            XmlStorage.saveAllData(getRepository(), newPath);
            getSession().setCurrentFilePath(newPath);
            getSession().setHasUnsavedChanges(false);

            return CommandResult.success("Successfully saved as " + newFileName);

        } catch (IOException e) {
            return CommandResult.error("Error saving file: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "saveas";
    }

    @Override
    public String getUsage() {
        return "saveas <filename.xml>";
    }

    @Override
    public String getDescription() {
        return "Saves data to a new file";
    }
}
