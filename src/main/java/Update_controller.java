/**
 * Sample Skeleton for 'Update.fxml' Controller Class
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.codehaus.plexus.util.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.ChainingCredentialsProvider;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONObject;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;

public class Update_controller implements Initializable {

    public static String colorBG = "#2f3640";
    public static String color1 = "#4cd137";
    private static String colorWarning = "#c23616";

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

    @FXML // fx:id="btn_exit"
    private JFXButton btn_exit; // Value injected by FXMLLoader

    @FXML // fx:id="progressBar"
    private JFXProgressBar progressBar; // Value injected by FXMLLoader

    @FXML // fx:id="lbl_download"
    private Label lbl_download; // Value injected by FXMLLoader

    @FXML
    void btn_exit_onAction(ActionEvent event) {
System.exit(0);
    }

    @FXML
    void btn_install_onAction(ActionEvent event) {
        if (this.btn_install.getText().equals("Install Update")) {
            this.btn_install.setDisable(true);
            double[] com = new double[]{0.0D};
            (new Thread(() -> {
                int count = 1;

                while(com[0] == 0.0D) {
                    Platform.runLater(() -> {
                        this.lbl_download.setText("Initializing Download" + ".".repeat(count));
                    });
                    ++count;
                    if (count == 15) {
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
                    double[] var10000 = new double[]{0.0D};
                    final boolean[] running = new boolean[]{false};
                    File sourceFile = new File("../app");
                    File destFile = new File("../temp");
                    if (sourceFile.renameTo(destFile)) {
                        System.out.println("Directory renamed successfully");
                    } else {
                        System.out.println("Failed to rename directory");
                    }

                    ((CloneCommand)Git.cloneRepository().setURI("https://github.com/Ealuthwala/RathnapuraLabsJar").setDirectory(Paths.get("../app").toFile()).setCredentialsProvider(new ChainingCredentialsProvider(new CredentialsProvider[]{cp}))).setProgressMonitor(new TextProgressMonitor() {
                        public boolean isCancelled() {
                            boolean cancelled = super.isCancelled();
                            if (cancelled) {
                                File sourceFile = new File("../temp");
                                File destFile = new File("../app");
                                if (sourceFile.renameTo(destFile)) {
                                    System.out.println("resetting : Directory renamed successfully");
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

                        protected void onUpdate(String taskName, int cmp, int totalWork, int pcnt) {
                            super.onUpdate(taskName, cmp, totalWork, pcnt);
                            com[0] = (double)cmp;
                            Platform.runLater(() -> {
                                UpdateController.this.lblProgress.setText(taskName + ":\t\t" + pcnt + "% (" + cmp + "/" + totalWork + ")");
                                UpdateController.this.progressBar.setProgress(Double.valueOf(pcnt + ".0") / 100.0D);
                            });
                        }

                        public void endTask() {
                            running[0] = false;
                            (new Thread(() -> {
                                try {
                                    Thread.sleep(2000L);
                                    if (!running[0] && UpdateController.this.btnInstall.isDisable()) {
                                        Platform.runLater(() -> {
                                            UpdateController.this.btnInstall.setText("Finish");
                                            UpdateController.this.lblProgress.setText("Completed:\t\t100%");
                                            UpdateController.this.btnInstall.setDisable(false);
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
            Process p = Runtime.getRuntime().exec("java -jar ../app/RathnapuraLabs-v1.0.jar");
            System.exit(0);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'Update.fxml'.";
        assert lbl_main != null : "fx:id=\"lbl_main\" was not injected: check your FXML file 'Update.fxml'.";
        assert btn_install != null : "fx:id=\"btn_install\" was not injected: check your FXML file 'Update.fxml'.";
        assert btn_exit != null : "fx:id=\"btn_exit\" was not injected: check your FXML file 'Update.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'Update.fxml'.";
        assert lbl_download != null : "fx:id=\"lbl_download\" was not injected: check your FXML file 'Update.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Update_controller.initialize");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Update_controller.run.inside loop");
                    lbl_download.setText("Downloading Files "+ ". ".repeat(i));
                    if (3 == i) {
                        i = 0;
                    }
                }
            }
        });
        scale(this.pane, false);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.github.com/repos/Ealuthwala/test/releases/latest")).build();
            HttpResponse<String> send = client.send(request, BodyHandlers.ofString());
            JSONObject o = new JSONObject((String)send.body());
            this.lblTitle.setText("Version " + o.get("tag_name") + " is Available to download.");
        } catch (InterruptedException | IOException var7) {
            var7.printStackTrace();
        }

        this.btnInstall.setOnMouseMoved((event) -> {
            this.btnInstall.arm();
        });
        this.btnExit.setOnMouseMoved((event) -> {
            this.btnExit.arm();
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
                Pane p = (Pane)node;
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