package bg.tu_varna.sit.f24621688;

import bg.tu_varna.sit.f24621688.commands.CommandParser;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.contracts.DataRepository;
import bg.tu_varna.sit.f24621688.models.AcademicRegistry; // Преименуван от University
import bg.tu_varna.sit.f24621688.session.AppSession;       // Преименуван от Session

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        DataRepository repository = new AcademicRegistry();
        AppSession session = new AppSession(repository);
        CommandParser parser = new CommandParser(session);

        Scanner scanner = new Scanner(System.in);
        System.out.println("=========================================");
        System.out.println("   Student Information System.....");
        System.out.println("=========================================");
        System.out.println("Type 'help' for available commands.");
        System.out.println("Use 'open <file.xml>' to load data.");
        System.out.println();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            CommandResult result = parser.parseAndExecute(input);
            if (result.getMessage() != null && !result.getMessage().isEmpty()) {
                if (result.isSuccess()) {
                    System.out.println(result.getMessage());
                } else {
                    System.err.println(result.getMessage());
                }
            }
            if (input.trim().equalsIgnoreCase("exit")) {
                break;
            }
        }
        scanner.close();
    }
}
