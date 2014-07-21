package ru.eastwind.menu;

import java.io.IOException;
import java.util.List;

public class App {

    private static final String DATA_FILENAME = "data.csv";
    private MenuLookup menuLookup;
    private IOSupport io;
    private int maxFoundItems = 10;

    public App(MenuLookup menuLookup, IOSupport io) {
        this.menuLookup = menuLookup;
        this.io = io;
    }

    public void setMaxFoundItems(int maxFoundItems) {
        this.maxFoundItems = maxFoundItems;
    }

    public void run() throws Exception {
        programInfo();

        while (true) {
            io.output("\nYour input: ");
            String input = io.readInputString();
            if ("exit".equals(input)) {
                io.output("\nbye bye");
                return;
            }
            List<String> menuItems = menuLookup.lookupMenuItemsBy(input, maxFoundItems);
            outputItems(menuItems);
        }
    }

    private void programInfo() {
        io.output("\nThis test program is made by Mikhail Golubtsov for Eastwind company, 2014.\n");
    }

    private void outputItems(List<String> menuItems) {
        if (menuItems.isEmpty()) {
            io.output("\nNothing is found");
            return;
        }
        io.output("\nFound items:");
        for (String menuItem : menuItems) {
            io.output(menuItem);
        }
    }

    public static void main(String[] args) throws Exception {
        newApp(IOSupport.systemIOSupport()).run();
    }

    public static App newApp(IOSupport ioSupport) throws IOException {
        MenuDao menuDao = MenuDao.fromResource(DATA_FILENAME);
        RelevanceStrategy relevanceStrategy = new RelevanceStrategy();
        MenuLookup menuLookup = new MenuLookup(menuDao, relevanceStrategy);
        App app = new App(menuLookup, ioSupport);
        return app;
    }

}