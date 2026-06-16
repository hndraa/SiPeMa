import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.geom.*;

public class HalamanCheckout {

    private static JLabel backgroundLabel;
    private static JLabel titleLabel;
    private static JLabel qrCodeLabel;
    private static JButton payButton;

    public HalamanCheckout() {
        // Membuat frame utama
        JFrame frame = new JFrame("Halaman Checkout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());

        // Layered Pane untuk mengatur layer
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(layeredPane);

        // Label Background
        backgroundLabel = new JLabel();
        ImageIcon originalBackgroundIcon = new ImageIcon("Images/Backgorund.jpeg");
        Image scaledBackgroundImage = originalBackgroundIcon.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledBackgroundImage));
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        // Panel Header
        JPanel headerPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(172, 154, 21));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBounds(0, 0, frame.getWidth(), 80);
        layeredPane.add(headerPanel, JLayeredPane.PALETTE_LAYER);

        // Tombol Kembali
        JButton backButton = new JButton();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/icon/back.png"));
            backButton.setIcon(new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        } catch (Exception ex) {
            ex.printStackTrace();
            backButton.setText("Kembali"); // Gunakan teks sebagai alternatif jika ikon gagal dimuat
        }
        backButton.setBounds(10, 20, 40, 40);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> {
            System.out.println("Tombol kembali ditekan!");
            new HalamanKeranjang();
            frame.dispose();
        });
        headerPanel.add(backButton);

        // Menambahkan Label Checkout dengan font kustom
        titleLabel = new JLabel("Checkout", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);

        try {
            // Memuat font kustom dari file
            Font irishGroverFont = Font.createFont(Font.TRUETYPE_FONT, new File("Images/fonts/IrishGrover.ttf")).deriveFont(47f);
            titleLabel.setFont(irishGroverFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        }

        titleLabel.setBounds(0, 20, frame.getWidth(), 40);
        headerPanel.add(titleLabel);

        // QR Code Label dengan sudut melengkung
        qrCodeLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Ambil gambar dari path
                ImageIcon qrCodeIcon = new ImageIcon("Images/QRcode.jpg");
                Image qrCodeImage = qrCodeIcon.getImage();

                // Gambar dengan sudut melengkung
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30)); // Sudut melengkung 30px

                // Gambar QR code dengan ukuran dan sudut melengkung
                g2d.drawImage(qrCodeImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

// Atur ukuran dan posisi QR code label
qrCodeLabel.setPreferredSize(new Dimension(400, 430));
qrCodeLabel.setBounds((frame.getWidth() - 400) / 2, (frame.getHeight() - 430) / 2, 400, 430);

// Tambahkan label ke layeredPane
layeredPane.add(qrCodeLabel, JLayeredPane.PALETTE_LAYER);


        // Event untuk menyesuaikan elemen-elemen saat ukuran frame berubah
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();

                // Sesuaikan ukuran background
                Image scaledBackground = originalBackgroundIcon.getImage().getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
                backgroundLabel.setIcon(new ImageIcon(scaledBackground));
                backgroundLabel.setBounds(0, 0, frameWidth, frameHeight);

                // Sesuaikan posisi "Checkout" Label
                titleLabel.setBounds(0, 20, frameWidth, 40);

                // Sesuaikan posisi QR code
                qrCodeLabel.setBounds((frameWidth - 400) / 2, (frameHeight - 430) / 2, 400, 430);
            }
        });

        // Menambahkan Tombol Bayar
        payButton = new JButton("Bayar");
        payButton.setFont(new Font("Arial", Font.BOLD, 18));
        payButton.setBounds((frame.getWidth() - 200) / 2, frame.getHeight() - 130, 200, 50);
        payButton.setBackground(new Color(255, 195, 0));
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // URL yang ingin dibuka di browser
                    String url = "https://wa.link/kggm32"; // Ganti dengan URL yang sesuai
                    // Cek jika Desktop tersedia dan dapat membuka URL
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.browse(new URI(url)); // Membuka URL di browser default
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Terjadi kesalahan! Gagal membuka browser.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        

        layeredPane.add(payButton, JLayeredPane.MODAL_LAYER);

        // Set visibilitas frame
        frame.setVisible(true);
    }
}
