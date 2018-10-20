package controller;

import java.io.IOException;

public interface Data {
    /**
     * We save in file "Choices.txt" the user choices
     * @throws IOException
     */
    void writeData() throws IOException;
}
