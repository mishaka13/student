package bg.tu_varna.sit.f24621688.session;

import bg.tu_varna.sit.f24621688.contracts.DataRepository;

/**
 * Manages the current working session of the application.
 * Tracks the open file path and whether unsaved changes exist.
 */
public class AppSession {
    private final DataRepository repository;
    private String currentFilePath;
    private boolean fileOpen;
    private boolean hasUnsavedChanges;

    /**
     * Constructs a new {@code AppSession} with the given repository.
     * Initialises with no open file.
     *
     * @param repository the data repository to use; must not be {@code null}
     */
    public AppSession(DataRepository repository) {
        this.repository = repository;
        this.currentFilePath = null;
        this.fileOpen = false;
        this.hasUnsavedChanges = false;
    }

    public DataRepository getRepository() {
        return repository;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String path) {
        this.currentFilePath = path;
    }

    public boolean isFileOpen() {
        return fileOpen;
    }

    public void setFileOpen(boolean open) {
        this.fileOpen = open;
    }

    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    public void setHasUnsavedChanges(boolean val) {
        this.hasUnsavedChanges = val;
    }

    public void closeFile() {
        if (!fileOpen) return;
        repository.clear();
        currentFilePath = null;
        fileOpen = false;
        hasUnsavedChanges = false;
    }
}
