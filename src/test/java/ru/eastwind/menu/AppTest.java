package ru.eastwind.menu;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ru.eastwind.menu.model.MenuLookup;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    private static final String EXIT = "exit";
    @Mock
    private IOSupport io;
    @Mock
    private MenuLookup menuLookup;
    private App app;
    private InOrder ioOrder;

    @Before
    public void setup() throws IOException {
        app = new App(menuLookup, io);
        ioOrder = Mockito.inOrder(io);
    }

    @Test(timeout = 1000)
    public void trivialExecution() throws Exception {
        Mockito.when(io.readInputString()).thenReturn(EXIT);
        app.run();

        verifyProgramInfo();
        verifyInputMessage();
        verifyExitMessage();
        ioOrder.verifyNoMoreInteractions();
    }

    @Test(timeout = 1000)
    public void foundItems() throws Exception {
        List<String> foundItems = Arrays.asList("beer", "bees");
        mockFoundItems(foundItems);
        mockSearchInput("be");

        app.run();

        verifyProgramInfo();
        verifyInputMessage();
        verifyFoundItems(foundItems);
        verifyInputMessage();
        verifyExitMessage();
    }

    private void mockFoundItems(List<String> foundItems) {
        Mockito.when(menuLookup.lookupMenuItemsBy(Mockito.anyString(), Mockito.anyInt())).thenReturn(foundItems);
    }

    @Test(timeout = 1000)
    public void notFoundItems() throws Exception {
        mockFoundItems(Collections.emptyList());
        mockSearchInput("");

        app.run();

        verifyProgramInfo();
        verifyInputMessage();
        verifyNotFound();
        verifyInputMessage();
        verifyExitMessage();
    }

    private void mockSearchInput(String query) throws IOException {
        Mockito.when(io.readInputString()).thenReturn(query, EXIT);
    }

    private void verifyInputMessage() throws IOException {
        ioOrder.verify(io).output(Mockito.contains("Your input"));
        ioOrder.verify(io).readInputString();
    }

    private void verifyProgramInfo() {
        ioOrder.verify(io).output(Mockito.contains("Mikhail Golubtsov"));
    }

    private void verifyExitMessage() {
        ioOrder.verify(io).output(Mockito.contains("bye bye"));
    }

    private void verifyFoundItems(List<String> items) {
        ioOrder.verify(io).output(Mockito.contains("Found items:"));
        for (String item : items) {
            ioOrder.verify(io).output(item);
        }
    }

    private void verifyNotFound() {
        ioOrder.verify(io).output(Mockito.contains("Nothing is found"));
    }

}
