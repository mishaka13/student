package bg.tu_varna.sit.f24621688.commands;

import bg.tu_varna.sit.f24621688.contracts.Command;
import bg.tu_varna.sit.f24621688.contracts.DataRepository;
import bg.tu_varna.sit.f24621688.session.AppSession;

/**
 * abstract class for all command types that inherit it.
 * Provides shared access to the session and the data repository.
 */
public abstract class BaseCommand implements Command {
    private final AppSession session;
    private final DataRepository repository;

    protected BaseCommand(AppSession session) {
        this.session = session;
        this.repository = session.getRepository();
    }

    /**
     * Extracts only the file name from a full file path.
     * Works with both forward-slash and backslash separators.
     *
     * @param filepath the full file path
     * @return the file name without its directory
     */
    protected String getFileName(String filepath) {
        if (filepath.contains("/"))  return filepath.substring(filepath.lastIndexOf('/') + 1);
        if (filepath.contains("\\")) return filepath.substring(filepath.lastIndexOf('\\') + 1);
        return filepath;
    }

    protected AppSession getSession() {
        return session;
    }

    protected DataRepository getRepository() {
        return repository;
    }
}
