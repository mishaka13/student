package bg.tu_varna.sit.f24621688.commands;

import bg.tu_varna.sit.f24621688.contracts.Command;
import bg.tu_varna.sit.f24621688.contracts.DataRepository;
import bg.tu_varna.sit.f24621688.session.AppSession;

public abstract class BaseCommand implements Command {
    private final AppSession session;
    private final DataRepository repository;

    protected BaseCommand(AppSession session) {
        this.session = session;
        this.repository = session.getRepository();
    }

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
