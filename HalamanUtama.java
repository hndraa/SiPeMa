import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HalamanUtama {
    public HalamanUtama() {
        // Membuat frame utama
        JFrame frame = new JFrame("SiPeMa - Sistem Pemesanan Makanan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,700);
        frame.setLayout(null);

        // Layered Pane untuk mengatur layer
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(layeredPane);

        // Label Background
        JLabel backgroundLabel = new JLabel();
        ImageIcon originalBackgroundIcon = new ImageIcon("Images/Backgorund.jpeg");
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER); // Latar belakang di layer terendah

        // Panel Header
        JPanel headerPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(172, 154, 21)); // Warna header
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBounds(0, 0, frame.getWidth(), 80);
        layeredPane.add(headerPanel, JLayeredPane.PALETTE_LAYER); // Header di atas background

        // Label SiPeMa
        JLabel titleLabel = new JLabel("SiPeMa", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        try {
            Font irishGroverFont = Font.createFont(Font.TRUETYPE_FONT, new File("Images/fonts/IrishGrover.ttf")).deriveFont(47f);
            titleLabel.setFont(irishGroverFont);
        } catch (FontFormatException | IOException e) {
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        }
        headerPanel.add(titleLabel);

        // Label Subtitle
        JLabel subtitleLabel = new JLabel("Pesan Sekarang Langsung di Antar", SwingConstants.CENTER);
        subtitleLabel.setForeground(Color.WHITE);
        try {
            Font abelFont = Font.createFont(Font.TRUETYPE_FONT, new File("Images/fonts/Abel-Regular.ttf")).deriveFont(14f);
            subtitleLabel.setFont(abelFont);
        } catch (FontFormatException | IOException e) {
            subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        }
        headerPanel.add(subtitleLabel);

        // Ikon Keranjang
        JLabel cartLabel = new JLabel();
        ImageIcon cartIcon = new ImageIcon("Images/icon/keranjang.png");
        cartLabel.setIcon(new ImageIcon(cartIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        headerPanel.add(cartLabel);

        // Tombol dengan sudut melengkung
        JButton makananButton = createRoundedButton("Makanan");
        JButton minumanButton = createRoundedButton("Minuman");
        JButton jajanButton = createRoundedButton("Jajan/Ngemil");

        // Menambahkan tombol ke Layered Pane
        JButton[] buttons = {makananButton, minumanButton, jajanButton};
        for (JButton button : buttons) {
            layeredPane.add(button, JLayeredPane.MODAL_LAYER); // Tombol di atas background
        }

        // Menambahkan ActionListener pada tombol
        makananButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HalamanMakanan();
                frame.dispose();
            }
        });
        

        minumanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HalamanMinuman();
                frame.dispose();
            }
        });

        jajanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HalamanJajan();
                frame.dispose();
            }
        });

        // Menambahkan ActionListener pada Logo Keranjang
        cartLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new HalamanKeranjang();
                frame.dispose();
            }
        });

        // Event untuk menyesuaikan elemen ketika ukuran frame berubah
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();

                // Sesuaikan ukuran Layered Pane
                layeredPane.setBounds(0, 0, frameWidth, frameHeight);

                // Sesuaikan ukuran header
                headerPanel.setBounds(0, 0, frameWidth, frameHeight / 8);

                // Posisi dan ukuran judul dan subtitle (tengah header)
                titleLabel.setBounds(0, 10, frameWidth, frameHeight / 16);
                subtitleLabel.setBounds(0, headerPanel.getHeight() - 30, frameWidth, 20);

                // Posisi keranjang
                cartLabel.setBounds(frameWidth - 80, 20, 30, 30);

                // Background
                Image scaledBackground = originalBackgroundIcon.getImage().getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
                backgroundLabel.setIcon(new ImageIcon(scaledBackground));
                backgroundLabel.setBounds(0, 0, frameWidth, frameHeight);

                // Posisi dan ukuran tombol
                int buttonWidth = frameWidth / 2;
                int buttonHeight = 40;
                int buttonX = (frameWidth - buttonWidth) / 2;
                int startY = headerPanel.getHeight() + 100;
                int gap = 20;

                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setBounds(buttonX, startY + (i * (buttonHeight + gap)), buttonWidth, buttonHeight);
                }
            }
        });
        
        // Set visibilitas frame
        frame.setVisible(true);
    }

    // Method untuk membuat tombol dengan sudut melengkung
    private static JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Warna tombol
                g2d.setColor(new Color(255, 195, 0));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Sudut melengkung (20)

                // Teks tombol
                g2d.setColor(Color.WHITE);
                FontMetrics fm = g.getFontMetrics();
                int stringWidth = fm.stringWidth(getText());
                int stringHeight = fm.getAscent();
                g2d.drawString(getText(), (getWidth() - stringWidth) / 2, (getHeight() + stringHeight) / 2 - 2);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Tidak ada border
            }
        };
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        // Memanggil halaman utama
        new HalamanUtama();

        
    }
}