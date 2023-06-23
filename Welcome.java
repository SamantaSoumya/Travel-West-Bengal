import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Welcome extends JFrame {
    public Welcome() {
        displayWelcomeScreen();
        setSize(800, 700);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addNavbar();
        addFooter();
        addLeftSection();
        addRightSection();
    }

    private void addNavbar() {
        JPanel navbar = new JPanel();
        navbar.setBackground(Color.LIGHT_GRAY);
        navbar.setPreferredSize(new Dimension(getWidth(), 40));
        navbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        navbar.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("Travel West Bengal");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        navbar.add(titleLabel);

        JButton homeButton = new JButton("Home");
        homeButton.setFont(homeButton.getFont().deriveFont(Font.PLAIN, 14f));
        homeButton.setBackground(Color.LIGHT_GRAY);
        homeButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                homeButton.setBackground(Color.DARK_GRAY);
            }

            public void mouseReleased(MouseEvent e) {
                homeButton.setBackground(Color.LIGHT_GRAY);
            }
        });
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartProgram();
            }
        });
        navbar.add(homeButton);

        JButton supportButton = new JButton("Support");
        supportButton.setFont(supportButton.getFont().deriveFont(Font.PLAIN, 14f));
        supportButton.setBackground(Color.LIGHT_GRAY);
        supportButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                supportButton.setBackground(Color.DARK_GRAY);
            }

            public void mouseReleased(MouseEvent e) {
                supportButton.setBackground(Color.LIGHT_GRAY);
            }
        });
        supportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContactForm();
            }
        });
        navbar.add(supportButton);

        add(navbar, BorderLayout.NORTH);
    }

    private void restartProgram() {
        dispose(); // Close the current frame

        // Restart the program by invoking the main method again
        Welcome welcome = new Welcome();
        welcome.setVisible(true);
        welcome.stopProgressBar(); // Stop the progress bar in the new instance
    }

    private void showContactForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2));
        panel.add(formPanel, BorderLayout.CENTER);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel messageLabel = new JLabel("Message:");
        JTextField messageField = new JTextField(30);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(messageLabel);
        formPanel.add(messageField);

        int result = JOptionPane.showOptionDialog(
                null,
                panel,
                "Contact Support",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String message = messageField.getText();

            // Process the form data
            // ...
        }
    }

    private void addFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(Color.LIGHT_GRAY);
        footer.setPreferredSize(new Dimension(getWidth(), 30));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

        JLabel footerLabel = new JLabel("Copyright @ All Rights Reserved 2022-23");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footer.add(footerLabel);

        add(footer, BorderLayout.SOUTH);
    }

    JLabel imageLabel;

    private void addLeftSection() {
        JPanel leftSection = new JPanel();
        leftSection.setBackground(Color.WHITE);
        int leftSectionWidth = (getWidth() / 12) * 8;
        leftSection.setPreferredSize(new Dimension(leftSectionWidth, getHeight()));
        leftSection.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));

        // Add image to left section
        ImageIcon westBengalImageIcon = new ImageIcon("West-Bengal-Map-PDF.gif");
        Image westBengalImage = westBengalImageIcon.getImage();

        // Calculate the new image size to fit within the panel
        int maxWidth = leftSectionWidth - 20; // Adjust the value as needed
        int maxHeight = getHeight() - 100; // Adjust the value as needed

        int originalWidth = westBengalImage.getWidth(null);
        int originalHeight = westBengalImage.getHeight(null);

        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;
        double scaleRatio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * scaleRatio);
        int newHeight = (int) (originalHeight * scaleRatio);

        // Resize the image
        Image resizedImage = westBengalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

        imageLabel = new JLabel(resizedImageIcon);
        imageLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                zoomInDistrict(e);
                imageLabel.removeMouseListener(this);
            }
        });
        leftSection.add(imageLabel);

        add(leftSection, BorderLayout.WEST);
    }

    private void zoomInDistrict(MouseEvent e) {
        // Get the x and y coordinates of the mouse click
        int x = e.getX();
        int y = e.getY();

        // Calculate the width and height of each district
        int districtWidth = imageLabel.getWidth() / 5; // Assuming 5 districts horizontally
        int districtHeight = imageLabel.getHeight() / 5; // Assuming 2 districts vertically

        // Calculate the row and column of the clicked district
        int row = y / districtHeight;
        int column = x / districtWidth;

        // Calculate the coordinates of the clicked district
        int districtX = column * districtWidth;
        int districtY = row * districtHeight;

        ImageIcon zoomedInDistrictImage = new ImageIcon("West-Bengal-Map-PDF" + (row * 5 + column + 1) + ".png");

        int newWidth = 450;
        int newHeight = 580;
        Image scaledImage = zoomedInDistrictImage.getImage().getScaledInstance(newWidth, newHeight,
                Image.SCALE_DEFAULT);
        zoomedInDistrictImage = new ImageIcon(scaledImage);

        // Update the image label with the zoomed-in district image
        imageLabel.setIcon(zoomedInDistrictImage);

        // Update the size of the image label
        imageLabel.setPreferredSize(new Dimension(newWidth, newHeight));

        int centerX = (imageLabel.getParent().getWidth() - newWidth) / 2;
        int centerY = (imageLabel.getParent().getHeight() - newHeight) / 2;
        // Set the new position of the image label
        imageLabel.setBounds(centerX, centerY, newWidth, newHeight);
        // Repaint the left section panel to reflect the changes
        imageLabel.getParent().revalidate();
        imageLabel.getParent().repaint();
        updateRightSection(row, column);
    }

    private JPanel createLinePanel(String title, String line, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 120)); // Adjust the size as needed

        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        ImageIcon originalImageIcon = new ImageIcon(imagePath);
        Image originalImage = originalImageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());

        JLabel lineLabel = new JLabel("<html>" + line + "</html>");
        lineLabel.setVerticalAlignment(SwingConstants.CENTER);
        lineLabel.setHorizontalAlignment(SwingConstants.LEFT);

        textPanel.add(lineLabel, BorderLayout.CENTER);
        contentPanel.add(imageLabel, BorderLayout.WEST);
        contentPanel.add(textPanel, BorderLayout.CENTER);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private void updateRightSection(int row, int column) {
        // Clear the existing content in the right section
        JPanel rightSection = (JPanel) getContentPane().getComponent(3); // Assuming right section is at index 3
        rightSection.removeAll();

        // Add the list of lines based on the clicked district
        JPanel linesPanel = new JPanel();
        linesPanel.setLayout(new GridLayout(0, 1));
        linesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the lines based on the clicked district image
        if (row == 0 && column == 0) {
        } else if (row == 0 && column == 1) {
        } else if (row == 0 && column == 2) {
            linesPanel.add(createLinePanel("Sandakphu",
                    "Sandakphu or Sandakpur (3636 m; 11,930 ft) is a mountain peak in the Singalila Ridge on the border between India and Nepal.",
                    "Shandakophu.jpg"));
            linesPanel.add(createLinePanel("Islampur",
                    "Islampur is a city and a municipality in Uttar Dinajpur district in the Indian state of West Bengal. It is the headquarters of the Islampur subdivision.",
                    "Islampur.jpg"));
            linesPanel.add(createLinePanel("Phallut",
                    "Phalut or Falut 3,600 metres (11,800 ft) is the second highest peak of West Bengal, India. Part of the Singalila Ridge in the Himalayas",
                    "Phallut.jpg"));
        } else if (row == 0 && column == 3) {
            linesPanel.add(createLinePanel("Darjiling",
                    "Located in the Eastern Himalayas, it has an average elevation of 2,045 metres (6,709 ft).",
                    "darjiling.jpg"));
            linesPanel.add(createLinePanel("Jolpaiguri",
                    "its special importance in respect of tourism, forest, hills, tea gardens, scenic beauty and a wide variety of tribes like the Totos, etc.",
                    "Jolpaiguri.jpg"));
            linesPanel.add(createLinePanel("Malbajar",
                    "Malbazar is a small sub divisional town also known as Mal located in the Northern part of the State of West Bengal. It is about 65 km from Jalpaiguri town",
                    "Malbajar.jpg"));
        } else if (row == 0 && column == 4) {
            linesPanel.add(createLinePanel("Buxa Fort",
                    "It is 30 kilometres (19 mi) from Alipurduar, the nearest town. The King of Bhutan used the fort to protect the portion connecting Tibet with India, via Bhutan.",
                    "Buxa Fort.jpg"));
            linesPanel.add(createLinePanel("Phuentsholing",
                    "The second largest town in Bhutan, Phuentsholing shares its borders with the Indian State of West Bengal. Serving as an entry point for travellers",
                    "Phuentsholing.jpg"));
            linesPanel.add(createLinePanel("CoachBihar Raj Palace",
                    "Cooch Behar Palace, is a landmark in Cooch Behar city, West Bengal. It was designed after the Italian Renaissance style of architecture",
                    "coachbiharrajplace.jpg"));
        } else if (row == 1 && column == 0) {
        } else if (row == 1 && column == 1) {
        } else if (row == 1 && column == 2) {
            linesPanel.add(createLinePanel("Adina Deer Park",
                    "Adina Deer Park is located in Malda district of West Bengal and lies 21 kilometres from the city of Malda. The park is an important breeding centre forDeer",
                    "Adina.jpg"));
            linesPanel.add(createLinePanel("Pandua",
                    "Pandua, also historically known as Hazrat Pandua and later Firuzabad, is a ruined city in the Malda district of the Indian state of West Bengal.",
                    "Pandua.jpg"));
        } else if (row == 1 && column == 3) {
        } else if (row == 1 && column == 4) {
        } else if (row == 2 && column == 0) {
        } else if (row == 2 && column == 1) {
        } else if (row == 2 && column == 2) {
            linesPanel.add(createLinePanel("Farakka",
                    "Farakka Barrage is a barrage across the Ganga river located in Murshidabad district in the Indian state of West Bengal, roughly 18 kilometres (11 mi)",
                    "Farakka.jpg"));
            linesPanel.add(createLinePanel("Tarapith",
                    "The Tara temple in Tarapith is a medium-sized temple in the rural precincts of Bengal. Its fame as a pilgrimage centre with the deity of Tara enshrined in it.",
                    "Tarapith.jpg"));
            linesPanel.add(createLinePanel("Nalhati",
                    "Nalhati is a town and a municipality in Rampurhat subdivision of Birbhum District in the Indian state of West Bengal near the West Bengal / Jharkhand border",
                    "Nalhati.jpg"));
        } else if (row == 2 && column == 3) {
            linesPanel.add(createLinePanel("Mursidabad",
                    "MURSHIDABAD  Area 5,324 sq km    Population 7.103 million (according to 2011 census)     Literacy Rate 63.88% ",
                    "Mursidabad.jpg"));
            linesPanel.add(createLinePanel("HazarDuari Palace",
                    "Hazarduari Palace, earlier known as the Bara Kothi, is located in the campus of Kila Nizamat in Murshidabad, in the Indian state of West Bengal.",
                    "Hazarduari.jpg"));
        } else if (row == 2 && column == 4) {
        } else if (row == 3 && column == 0) {
            linesPanel.add(createLinePanel("Ajodhya Hill",
                    "An amazingly serene place within the heart of nature. It is a pretty vast area and includes places such as the Mayur Hills, Upper Dam, Lower, Bamni Falls,",
                    "Ajodhya.jpg"));
            linesPanel.add(createLinePanel("Science Center Purulia",
                    "District Science Centre, Purulia is the first district level science centre, under National Council of Science Museums, Ministry of Culture, Govt. of India,",
                    "ScienPuru.jpg"));
        } else if (row == 3 && column == 1) {
            linesPanel.add(createLinePanel("Susunia Pahar",
                    "n the way to Bankura- Purulia, this ancient hill is 10 kilometers North-East of Chhatna which is 13 kilometers distant from Bankura town",
                    "Susunia.jpg"));
            linesPanel.add(createLinePanel("Joyrambati",
                    "At Jayrambati. The Temple of Simhavahini \u00B7 Amodar Ghat (Bathing-Ghat of Holy Mother) \u00B7 Mayer Dighi (Ghat) \u00B7 Yatra Siddhi Roy \u00B7",
                    "Joyrambati.jpg"));
            linesPanel.add(createLinePanel("Jhilomili",
                    "hilimili and Sutan Forest also known as \u201CBaro Mile-er Jungle\u201D is 45 KM away from Mukutmanipur and 70 km from Bankura Town.",
                    "jhilomili.jpg"));
        } else if (row == 3 && column == 2) {
            linesPanel.add(createLinePanel("Mayapur",
                    "t is located 130 km from kolkata and well connected by roads and rail networks from there. The holiest of all places in entire India, Mayapur Chandrodaya",
                    "Mayapur.jpg"));
            linesPanel.add(createLinePanel("Tarkeswar",
                    "Tarakeswar is a place of pilgrimage of Lord Shiva sect in West Bengal 58 kilometres (36 mi) away from State Capital Kolkata",
                    "Tarkeswar.jpg"));
            linesPanel.add(createLinePanel("Nanur",
                    "Nanoor (also spelt Nanur, called Chandidas Nanoor), is a village in Nanoor CD block in Bolpur subdivision of Birbhum district in West Bengal.",
                    "Nanur.jpg"));
        } else if (row == 3 && column == 3) {
            linesPanel.add(createLinePanel("Bandel",
                    "Bandel is a neighbourhood in the Hooghly district of the Indian state of West Bengal. It is founded by Portuguese settlers and falls under the jurisdiction ",
                    "Bandel.jpg"));
            linesPanel.add(createLinePanel("BandelChurch",
                    " it stands as a memorial to the Portuguese settlement in Bengal. Founded in 1599, it is dedicated to Nossa Senhora do Ros\u00E1rio, Our Lady of the Rosary.",
                    "BandelChurch.jpg"));
        } else if (row == 3 && column == 4) {
        } else if (row == 4 && column == 0) {
        } else if (row == 4 && column == 1) {
            linesPanel.add(createLinePanel("Belda", "District: Paschim Medinipur\r\n" + //
                    "Population: 2,500 (2011)\r\n" + //
                    "ISO 3166 code: IN-WB", "Belda.jpg"));
        } else if (row == 4 && column == 2) {
            linesPanel.add(createLinePanel("Mohisadal Raj Place",
                    "Mahishadal is a town in Mahishadal CD block in Haldia subdivision of Purba Medinipur district in the state of West Bengal, India.",
                    "Mohisadal.jpg"));
            linesPanel.add(createLinePanel("Digha", "District: Purba Medinipur\r\n" + //
                    "Elevation: 6 m (20 ft)\r\n" + //
                    "Lok Sabha constituency: Kanthi\r\n" + //
                    "Sub Division: Contai subdivision", "Digha.jpg"));
        } else if (row == 4 && column == 3) {
            linesPanel.add(createLinePanel("Victoria",
                    "Address: Victoria Memorial Hall, 1, Queens Way, Maidan, Kolkata, West Bengal 700071\r\n" + //
                            "Hours: \r\n" + //
                            "Open \u22C5 Closes 5\u202Fpm\r\n" + //
                            "Phone: 033 2223 1890\r\n" + //
                            "Architects: William Emerson, Vincent Esch\r\n" + //
                            "Construction started: 1906",
                    "tajmohal.jpg"));
            linesPanel.add(createLinePanel("Howrah Bridrw", "Address: Kolkata, West Bengal 700001\r\n" + //
                    "Total length: 705 m\r\n" + //
                    "Body of water: Bhagirathi River\r\n" + //
                    "Construction started: 1935", "howrahBridge.jpg"));
            linesPanel.add(createLinePanel("Eden Garden",
                    "The Eden Gardens is a cricket ground in Kolkata, West Bengal, India. Established in 1864, it is the oldest and second-largest cricket stadium in India",
                    "Eden.jpg"));
        } else if (row == 4 && column == 4) {
        }

        rightSection.add(linesPanel);

        // Repaint the right section panel to reflect the changes
        rightSection.revalidate();
        rightSection.repaint();
    }

    private void addRightSection() {
        JPanel rightSection = new JPanel();
        rightSection.setBackground(Color.WHITE);
        int rightSectionWidth = (getWidth() / 12) * 4;
        rightSection.setPreferredSize(new Dimension(rightSectionWidth, getHeight()));
        rightSection.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
        rightSection.setLayout(new BorderLayout());

        // Create the navbar panel
        JPanel navbar = new JPanel();
        navbar.setBackground(Color.LIGHT_GRAY);
        navbar.setPreferredSize(new Dimension(rightSectionWidth, 40));
        navbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        navbar.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align components in the center

        // Add the title label to the navbar
        JLabel titleLabel = new JLabel("Top Places");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        titleLabel.setForeground(Color.BLACK); // Set the color to blue
        navbar.add(titleLabel);

        rightSection.add(navbar, BorderLayout.CENTER);

        // Add your content below the navbar
        // ...

        add(rightSection, BorderLayout.EAST);
    }

    private Timer progressBarTimer;
    final JWindow w = new JWindow();

    private void displayWelcomeScreen() {
        w.setSize(800, 700);
        w.setLocationRelativeTo(null);
        w.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        w.add(panel, BorderLayout.CENTER);

        // Add the image label
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("welcome.png");
        imageLabel.setIcon(imageIcon);
        panel.add(imageLabel);

        JLabel textLabel = new JLabel("Welcome Travel to West Bengal");
        textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 30f));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        textLabel.setForeground(Color.blue);
        textLabel.setBackground(Color.white);
        textLabel.setOpaque(true);
        panel.add(textLabel);

        JProgressBar progressBar = new JProgressBar(0, 100);
        w.add(progressBar, BorderLayout.PAGE_END);
        w.revalidate();

        progressBarTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = progressBar.getValue();
                if (x == 100) {
                    w.dispose();
                    Welcome.this.setVisible(true);
                    ((Timer) e.getSource()).stop();
                } else {
                    progressBar.setValue(x + 4);
                }
            }
        });
        progressBarTimer.start();
    }

    public void stopProgressBar() {
        if (progressBarTimer != null) {
            progressBarTimer.stop();
            w.setVisible(false);
        }
    }

    public void setFrameIconAndTitle(ImageIcon icon, String title) {
        setIconImage(icon.getImage());
        setTitle(title);
    }

    public static void main(String[] args) {
        Welcome welcome = new Welcome();
        ImageIcon icon = new ImageIcon("welcome.png");
        welcome.setFrameIconAndTitle(icon, "PrasunVerse");
    }
}