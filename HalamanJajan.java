import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.RoundRectangle2D;

public class HalamanJajan {
    public HalamanJajan() {
        JFrame frame = new JFrame("Halaman Jajan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());
        
        // Panel Header
        JPanel headerPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(172, 154, 21)); // Warna header
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        frame.add(headerPanel, BorderLayout.NORTH);

        // Tombol Kembali
        JButton backButton = new JButton();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Images/icon/home.png"));
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
            new HalamanUtama();
            frame.dispose();
        });
        headerPanel.add(backButton);

        // Label Header
        JLabel titleLabel = new JLabel("JaJan", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        try {
            Font irishGrover = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("Images/fonts/IrishGrover.ttf")).deriveFont(47f);
            titleLabel.setFont(irishGrover.deriveFont(Font.BOLD, 47)); // Font Irish Grover
        } catch (Exception e) {
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30)); // Font default jika gagal
            e.printStackTrace();
        }
        titleLabel.setBounds(0, 20, frame.getWidth(), 40);
        headerPanel.add(titleLabel);

        // Panel Konten (Panel yang dapat digulir horizontal)
        JPanel contentPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Gambar Background untuk contentPanel
                ImageIcon backgroundIcon = new ImageIcon("Images/Backgorund.jpeg"); // Ganti dengan path gambar
                Image backgroundImage = backgroundIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        // Mengambil data makanan dari database
        ArrayList<Jajan> jajanList = JajanTable.getAllJajan();
        for (Jajan jajan : jajanList) {
            // Panel Makanan
            // Panel Makanan dengan sudut melengkung
            JPanel jajanPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Menambahkan border dengan sudut melengkung
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(getBackground());
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 20 adalah radius untuk sudut melengkung
                    g2d.setColor(Color.GRAY);
                    g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Menambahkan border dengan sudut melengkung
                }
            };
            jajanPanel.setLayout(new BorderLayout());
            jajanPanel.setPreferredSize(new Dimension(200, 250));
            jajanPanel.setOpaque(false);
            // Menambahkan border melengkung
            jajanPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Gambar Makanan dengan sudut melengkung
            JLabel gambarLabel = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    // Ambil gambar dari path
                    ImageIcon icon = new ImageIcon(jajan.getGambarPath());
                    Image image = icon.getImage();

                    // Gambar dengan sudut melengkung
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20)); // Sudut melengkung

                    // Gambar gambar makanan dengan ukuran dan sudut melengkung
                    g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            };
            gambarLabel.setPreferredSize(new Dimension(180, 250)); // Sesuaikan ukuran
            JPanel gambarPanel = new JPanel(new BorderLayout());
            gambarPanel.setPreferredSize(new Dimension(180, 250));
            gambarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10)); 
            gambarPanel.add(gambarLabel, BorderLayout.CENTER);
            jajanPanel.add(gambarPanel, BorderLayout.NORTH);

            // Nama dan Deskripsi
            JLabel namaLabel = new JLabel(jajan.getNama(), SwingConstants.CENTER);
            namaLabel.setFont(new Font("Arial", Font.BOLD, 16));
            JLabel deskripsiLabel = new JLabel("<html><center>" + jajan.getDeskripsi() + "</center></html>", SwingConstants.CENTER);
            deskripsiLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            namaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            deskripsiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Panel untuk nama dan deskripsi
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); 
            textPanel.add(namaLabel);
            textPanel.add(Box.createVerticalStrut(10));
            textPanel.add(deskripsiLabel);
            jajanPanel.add(textPanel, BorderLayout.CENTER);

            // Panel untuk harga dan ikon plus
            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.setPreferredSize(new Dimension(200, 50)); // Panel bawah dengan ukuran tetap

            // Harga di pojok kiri bawah
            JLabel hargaLabel = new JLabel(" Rp " + jajan.getHarga(), SwingConstants.LEFT);
            hargaLabel.setForeground(Color.RED); // Warna merah untuk harga
            // Menambah ukuran font agar lebih besar
            hargaLabel.setFont(new Font("Arial", Font.BOLD, 18));
            bottomPanel.add(hargaLabel, BorderLayout.WEST);

            // Ikon plus di pojok kanan bawah
            JButton addButton = new JButton();
            try {
                ImageIcon plusIcon = new ImageIcon(getClass().getResource("/Images/icon/plus.png"));
                addButton.setIcon(new ImageIcon(plusIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            } catch (Exception ex) {
                ex.printStackTrace();
                addButton.setText("Tambah");
            }
            addButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 5));
            addButton.setBorderPainted(false);
            addButton.setContentAreaFilled(false);
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    HalamanKeranjang.tambahKeKeranjang(jajan);
                }
            });
            bottomPanel.add(addButton, BorderLayout.EAST);

            // Tambahkan bottomPanel ke makananPanel
            jajanPanel.add(bottomPanel, BorderLayout.SOUTH);

            // Tambahkan makananPanel ke contentPanel
            contentPanel.add(jajanPanel);

        }

        // Membungkus contentPanel dalam JScrollPane dengan orientasi horizontal
        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Gesture untuk drag
        contentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
            }
        });

        contentPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int offset = e.getX() - initialX;
                int scrollPosition = scrollPane.getHorizontalScrollBar().getValue() - offset;
                scrollPane.getHorizontalScrollBar().setValue(scrollPosition);
            }
        });

        // Listener untuk perubahan ukuran frame
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Menyesuaikan ukuran elemen-elemen dalam frame
                headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
                titleLabel.setBounds(0, 20, frame.getWidth(), 40);
                scrollPane.setBounds(0, 80, frame.getWidth(), frame.getHeight());

                // Menyesuaikan gambar dan ukuran elemen lainnya jika perlu
                for (Component component : contentPanel.getComponents()) {
                    if (component instanceof JPanel) {
                        JPanel makananPanel = (JPanel) component;
                        makananPanel.setPreferredSize(new Dimension(frame.getWidth() / 5, 500)); // Menyesuaikan ukuran kotak makanan
                        makananPanel.revalidate();
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    private int initialX; // Variabel untuk menyimpan posisi awal drag
}