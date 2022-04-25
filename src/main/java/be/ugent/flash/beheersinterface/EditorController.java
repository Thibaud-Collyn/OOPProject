package be.ugent.flash.beheersinterface;

import java.io.File;

public class EditorController extends StartScreenController {
    private File selectedDB;
    public EditorController(File selectedDB) {
        this.selectedDB = selectedDB;
    }
}
