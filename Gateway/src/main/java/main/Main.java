package main;

import controller.ViewController;
import gateway.Gateway;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {

    public static final Logger LOGGER = LoggerFactory.getLogger(Gateway.class);

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader main = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Main.fxml"));

        Parent root = main.load();

        ViewController viewController = main.getController();
        Model model = new Model(viewController);
        viewController.connect(model);

        primaryStage.setTitle("RGB IOT Gateway");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setResizable(true);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);

        });
        Platform.setImplicitExit(false);

        Platform.runLater(new Runnable(){
            public void run()
            {
                try {
                    model.gateway.startGateway();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] args) throws Exception {
        if(args.length>0){
            if(args[0].equals("debugMode=false")) {
                new Gateway();
            } else if(args[0].equals("debugMode=true")) {
                //to get here start with gradle like:
                //                             run -PappArgs="['debugMode=true']"
                launch(args);
            }
        }else{
//            Main.LOGGER.info("Start in Debug Mode: Gradle => run -PappArgs=\"['debugMode=true']\"");
            new Gateway();
             //MessageBuilder.indexToByteArray(1024);
        }
    }
}
