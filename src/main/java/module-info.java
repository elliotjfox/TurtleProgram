module com.example.mandaladrawer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;
    requires reactfx;


    opens com.example.mandaladrawer to javafx.fxml;
    exports com.example.mandaladrawer;
    exports com.example.mandaladrawer.instruction;
    exports com.example.mandaladrawer.parser;
    exports com.example.mandaladrawer.parser.keywords;
    exports com.example.mandaladrawer.builder;
    opens com.example.mandaladrawer.builder to javafx.fxml;
    exports com.example.mandaladrawer.event;
    opens com.example.mandaladrawer.event to javafx.fxml;
}