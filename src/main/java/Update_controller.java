/**
 * Sample Skeleton for 'Update.fxml' Controller Class
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import org.codehaus.plexus.util.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.ChainingCredentialsProvider;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.util.concurrent.atomic.AtomicInteger;

public class Update_controller implements Initializable {

    AtomicInteger progress = new AtomicInteger(1);
    final boolean[] running = new boolean[]{false};

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="pane"
    private AnchorPane pane; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_main"
    private Label lbl_main; // Value injected by FXMLLoader

    @FXML // fx:id="btn_install"
    private JFXButton btn_install; // Value injected by FXMLLoader

    @FXML // fx:id="progressBar"
    private JFXProgressBar progressBar; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_download"
    private Label lbl_download; // Value injected by FXMLLoader

    @FXML
    void btn_install_onAction(ActionEvent event) {
        if (this.btn_install.getText().equals("Install Update")) {
            this.btn_install.setDisable(true);
            (new Thread(() -> {
                int count = 0;
                while (6 > count) {
                    int finalCount = count;
                    if (100 == progress.get()) {
                        Platform.runLater(() -> {
                            this.lbl_download.setText("Installing Update " + ".".repeat(finalCount));
                        });
                        if (!running[0] && Update_controller.this.btn_install.isDisable()) {
                            Platform.runLater(() -> {
                                Update_controller.this.btn_install.setText("Finish");
                                Update_controller.this.lbl_download.setText("Completed:\t\t100%");
                                Update_controller.this.btn_install.setDisable(false);
                            });
                            try {
                                FileUtils.cleanDirectory("../temp");
                                FileUtils.deleteDirectory("../temp");
                            } catch (IOException var3) {
                                var3.printStackTrace();
                            }
                            count = 6;
                        }
                    } else {
                        Platform.runLater(() -> {
                            this.lbl_download.setText("Downloading Files " + ".".repeat(finalCount));
                            Update_controller.this.progressBar.setProgress(progress.getAndIncrement() / 100.0D);
                        });
                    }
                    ++count;
                    if (count == 5) {
                        count = 0;
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException var5) {
                        var5.printStackTrace();
                    }
                }
            })).start();
            CredentialsProvider cp = new UsernamePasswordCredentialsProvider("e_aluthwala@hotmail.com", "i2killyou");
            (new Thread(() -> {
                try {
                    File sourceFile = new File("../app");
                    File destFile = new File("../temp");
                    destFile.delete();
                    if (sourceFile.renameTo(destFile)) {
                        System.out.println("Directory renamed successfully");
                    } else {
                        System.out.println("Failed to rename directory");
                    }

                    ((CloneCommand) Git.cloneRepository().setURI("https://github.com/dark-sl/RathnapuraLabs_exe-uploader").setDirectory(Paths.get("../app").toFile()).setCredentialsProvider(new ChainingCredentialsProvider(new CredentialsProvider[]{cp}))).setProgressMonitor(new TextProgressMonitor() {
                        public boolean isCancelled() {
                            boolean cancelled = super.isCancelled();
                            if (cancelled) {
                                File sourceFile = new File("../temp");
                                File destFile = new File("../app");
                                if (sourceFile.renameTo(destFile)) {
                                    lbl_main.setText("resetting : Directory renamed successfully");
                                } else {
                                    System.out.println("resetting : Failed to rename directory");
                                }
                            }
                            return cancelled;
                        }

                        public void beginTask(String title, int work) {
                            super.beginTask(title, work);
                            running[0] = true;
                            Update_controller.this.btn_install.setDisable(true);
                        }

                        public void endTask() {
                            running[0] = false;
                            (new Thread(() -> {
                                try {
                                    Thread.sleep(2000L);
                                    if (!running[0] && Update_controller.this.btn_install.isDisable() && 100 == progress.get()) {
                                        Platform.runLater(() -> {
                                            Update_controller.this.btn_install.setText("Finish");
                                            Update_controller.this.lbl_download.setText("Completed:\t\t100%");
                                            Update_controller.this.btn_install.setDisable(false);
                                        });
                                        try {
                                            FileUtils.cleanDirectory("../temp");
                                            FileUtils.deleteDirectory("../temp");
                                        } catch (IOException var3) {
                                            var3.printStackTrace();
                                        }
                                    }
                                } catch (InterruptedException var4) {
                                    var4.printStackTrace();
                                }

                            })).start();
                        }
                    }).call();
                } catch (GitAPIException var7) {
                    var7.printStackTrace();
                }

            })).start();
        } else {
            try {
                Process p = Runtime.getRuntime().exec("java -jar ../app/RathnapuraLabs.exe");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'Update.fxml'.";
        assert lbl_main != null : "fx:id=\"lbl_main\" was not injected: check your FXML file 'Update.fxml'.";
        assert btn_install != null : "fx:id=\"btn_install\" was not injected: check your FXML file 'Update.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'Update.fxml'.";
        assert lbl_download != null : "fx:id=\"lbl_download\" was not injected: check your FXML file 'Update.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scale(this.pane, false);

//        try {
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.github.com/repos/Ealuthwala/test/releases/latest")).build();
//            HttpResponse<String> send = client.send(request, BodyHandlers.ofString());
//            JSONObject o = new JSONObject((String)send.body());
//            this.lbl_main.setText("Version " + o.get("tag_name") + " is Available to download.");
//        } catch (InterruptedException | IOException var7) {
//            var7.printStackTrace();
//        }

        this.btn_install.setOnMouseMoved((event) -> {
            this.btn_install.arm();
        });
    }

    public void scale(Node node, boolean isFullScreen) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double x = screenBounds.getWidth();
        double y = screenBounds.getHeight();
        double ratioX = x / 1366.0D;
        double ratioY = y / 768.0D;
        Scale scale = new Scale(ratioX, ratioY, 0.0D, 0.0D);
        if (!isFullScreen) {
            double ratio;
            if (ratioX > ratioY) {
                ratio = ratioY;
            } else {
                ratio = ratioX;
            }

            scale = new Scale(ratio, ratio, 0.0D, 0.0D);
            if (node instanceof Pane) {
                Pane p = (Pane) node;
                p.setPrefSize(p.getPrefWidth() * ratioX, p.getPrefHeight() * ratioY);
            }
        }

        if (!(node instanceof Pane)) {
            node.setLayoutX(node.getLayoutX() * ratioX);
            node.setLayoutY(node.getLayoutY() * ratioY);
        }

        node.getTransforms().add(scale);
    }
}