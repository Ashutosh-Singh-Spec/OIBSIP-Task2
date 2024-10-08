package onlineexam;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Dashboard extends JFrame {
    private AtomicReference<String> username;
    private final Map<String, String> userDatabase;

    public Dashboard(String initialUsername, Map<String, String> userDatabase) {
        this.username = new AtomicReference<>(initialUsername);
        this.userDatabase = userDatabase;

        setTitle("Dashboard - Welcome, " + username.get());
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton updateProfileButton = new JButton("Update Profile");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(updateProfileButton, gbc);

        JButton changePasswordButton = new JButton("Change Password");
        gbc.gridy = 1;
        mainPanel.add(changePasswordButton, gbc);

        JButton startExamButton = new JButton("Start Exam");
        gbc.gridy = 2;
        mainPanel.add(startExamButton, gbc);

        JButton logoutButton = new JButton("Logout");
        gbc.gridy = 3;
        mainPanel.add(logoutButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Action listeners using AtomicReference's get method
        updateProfileButton.addActionListener(e -> {
            ProfileUpdate profileUpdate = new ProfileUpdate(username.get(), userDatabase);
            profileUpdate.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Update the current username after profile update
                    username.set(profileUpdate.getUpdatedUsername());
                    setTitle("Dashboard - Welcome, " + username.get());
                }
            });
        });

        changePasswordButton.addActionListener(e -> new ChangePassword(username.get(), userDatabase));
        startExamButton.addActionListener(e -> new ExamSession(username.get(), userDatabase));
        logoutButton.addActionListener(e -> logout());

        setVisible(true);
    }

    private void logout() {
        dispose();
        new LogoutSession();
    }
}
