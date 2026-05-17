package bg.tu_varna.sit.f24621688.commands.file;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.file.XmlStorage;
import bg.tu_varna.sit.f24621688.session.AppSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OpenCommand extends BaseCommand {
    public OpenCommand(AppSession session) { super(session); }

    @Override
    public CommandResult execute(String[] args) {
        try {
            if (args.length < 2) return CommandResult.error("Usage: open <filename.xml>");

            String filepath = args[1];
            if (!filepath.toLowerCase().endsWith(".xml"))
                return CommandResult.error("Only .xml files are allowed.");

            if (getSession().isFileOpen()) {
                String cur = getFileName(getSession().getCurrentFilePath());
                return CommandResult.error("Close '" + cur + "' first before opening a new file.");
            }

            String dir = "", fileName = filepath;
            if (filepath.contains("/")) {
                dir = filepath.substring(0, filepath.lastIndexOf('/'));
                fileName = filepath.substring(filepath.lastIndexOf('/') + 1);
            } else if (filepath.contains("\\")) {
                dir = filepath.substring(0, filepath.lastIndexOf('\\'));
                fileName = filepath.substring(filepath.lastIndexOf('\\') + 1);
            }

            XmlStorage.setCurrentDirectory(dir);
            String fullPath = XmlStorage.getFullPath(fileName);

            if (!Files.exists(Paths.get(fullPath))) {
                getRepository().clear();
                getSession().setCurrentFilePath(filepath);
                getSession().setFileOpen(true);
                getSession().setHasUnsavedChanges(false);
                return CommandResult.success("Opened new file: " + fileName + " (use 'save' to create it on disk)");
            }
            XmlStorage.loadAllData(getRepository(), fullPath);
            getSession().setCurrentFilePath(filepath);
            getSession().setFileOpen(true);
            getSession().setHasUnsavedChanges(false);

            return CommandResult.success("Successfully opened " + fileName);

        } catch (IOException e) {
            return CommandResult.error("Error reading file: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "open";
    }

    @Override
    public String getUsage() {
        return "open <filename.xml>";
    }

    @Override
    public String getDescription() {
        return "Opens an XML data file";
    }
}
