import javax.swing.border.AbstractBorder;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.geom.*;


public class HalamanKeranjang {
    private static ArrayList<Minuman> keranjangMinuman = new ArrayList<>();
    private static ArrayList<Makanan> keranjangMakanan = new ArrayList<>();
    private static ArrayList<Jajan> keranjangJajan = new ArrayList<>();
    private static HashMap<String, Integer> jumlahItem = new HashMap<>();
    private static double totalCheckout = 0; // Menyimpan total harga item yang akan di checkout

    public static void tambahKeKeranjang(Minuman minuman) {
        keranjangMinuman.add(minuman);
        jumlahItem.put(minuman.getNama(), jumlahItem.getOrDefault(minuman.getNama(), 0) + 1);
        JOptionPane.showMessageDialog(null, minuman.getNama() + " telah ditambahkan ke keranjang.");
    }
    public static void tambahKeKeranjang(Makanan makanan) {
        keranjangMakanan.add(makanan);
        jumlahItem.put(makanan.getNama(), jumlahItem.getOrDefault(makanan.getNama(), 0) + 1);
        JOptionPane.showMessageDialog(null, makanan.getNama() + " telah ditambahkan ke keranjang.");
    }
    public static void tambahKeKeranjang(Jajan jajan) {
        keranjangJajan.add(jajan);
        jumlahItem.put(jajan.getNama(), jumlahItem.getOrDefault(jajan.getNama(), 0) + 1);
        JOptionPane.showMessageDialog(null, jajan.getNama() + " telah ditambahkan ke keranjang.");
    }

    public HalamanKeranjang() {
        JFrame frame = new JFrame("Halaman Keranjang");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());

        // Panel Header
        JPanel headerPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(172, 154, 21));
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
        JLabel titleLabel = new JLabel("Keranjang", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        try {
            Font irishGrover = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("Images/fonts/IrishGrover.ttf")).deriveFont(47f);
            titleLabel.setFont(irishGrover.deriveFont(Font.BOLD, 47));
        } catch (Exception e) {
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30)); // Font default jika gagal
            e.printStackTrace();
        }
        titleLabel.setBounds(0, 20, frame.getWidth(), 40);
        headerPanel.add(titleLabel);

        // Panel Konten (Panel yang dapat digulir vertikal)
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
        
        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Padding kiri dan kanan


        // Menggunakan GroupLayout untuk menata item secara vertikal
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Mengatur tata letak vertikal
        GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();


        if (keranjangMinuman.isEmpty() && keranjangMakanan.isEmpty() && keranjangJajan.isEmpty()) {
            JLabel emptyLabel = new JLabel("Keranjang kosong", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 20));
            emptyLabel.setForeground(Color.GRAY);
            contentPanel.add(emptyLabel);
        } else {
            HashMap<String, JPanel> itemPanels = new HashMap<>();
            //minuman
            for (Minuman minuman : keranjangMinuman) {
                int totalItem = jumlahItem.getOrDefault(minuman.getNama(), 1);

                if (!itemPanels.containsKey(minuman.getNama())) {
                    JPanel itemPanel = new JPanel();
                    itemPanel.setLayout(null);
                    itemPanel.setPreferredSize(new Dimension(700, 100));
                    itemPanel.setBorder(new RoundBorder(20));
                    itemPanel.setOpaque(false);
                    
                    // Membuat JLabel dengan gambar bersudut melengkung
                    JLabel imageLabel = new JLabel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);

                            try {
                                // Ambil gambar dari path
                                ImageIcon imageIcon = new ImageIcon(getClass().getResource(minuman.getGambarPath()));
                                Image image = imageIcon.getImage();

                                // Gambar dengan sudut melengkung
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                g2d.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20)); // Sudut melengkung

                                // Gambar gambar dengan ukuran yang diatur
                                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                            } catch (Exception e) {
                                // Jika terjadi error, tampilkan placeholder teks
                                g.drawString("[Gambar]", getWidth() / 2 - 20, getHeight() / 2);
                            }
                        }
                    };

                    // Set ukuran dan posisi JLabel
                    imageLabel.setBounds(40, 10, 80, 80);
                    itemPanel.add(imageLabel);

                    // Panel untuk Nama dan Harga
                    JPanel infoPanel = new JPanel();
                    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                    infoPanel.setOpaque(false);

                    JLabel namaLabel = new JLabel(minuman.getNama());
                    namaLabel.setFont(new Font("Arial", Font.BOLD, 16));
                    infoPanel.setBounds(130, 10, 500, 60);
                    infoPanel.add(namaLabel);

                    // Menambahkan jarak antara nama dan harga
                    infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                    JLabel hargaLabel = new JLabel("Rp " + minuman.getHarga());
                    hargaLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                    hargaLabel.setForeground(Color.RED);
                    hargaLabel.setBounds(130, 30, 120, 50);
                    infoPanel.add(hargaLabel);

                    itemPanel.add(infoPanel);

                    // Total Item di kanan atas
                    JLabel totalItemLabel = new JLabel("Total: " + totalItem);
                    totalItemLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                    totalItemLabel.setForeground(Color.BLUE);
                    totalItemLabel.setBounds(700, 10, 80, 20);
                    itemPanel.add(totalItemLabel);

                    // Total Harga di bawah total item
                    JLabel totalHargaLabel = new JLabel("Rp " + totalItem * minuman.getHarga());
                    totalHargaLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                    totalHargaLabel.setForeground(Color.RED);
                    totalHargaLabel.setBounds(700, 30, 120, 50);
                    itemPanel.add(totalHargaLabel);

                    // Checkbox untuk memilih item
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.setBounds(10, 40, 20, 20);
                    checkBox.addActionListener(e -> {
                        double harga = minuman.getHarga() * jumlahItem.get(minuman.getNama());
                        if (checkBox.isSelected()) {
                            totalCheckout += harga;
                        } else {
                            totalCheckout -= harga;
                        }
                        // Gunakan thread untuk memperbarui footer
                        new Thread(() -> updateFooter(frame)).start();
                    });
                    itemPanel.add(checkBox);

                    // Tombol + (dengan ikon)
                    JButton plusButton = new JButton();
                    try {
                        ImageIcon plusIcon = new ImageIcon(getClass().getResource("/Images/icon/plus.png"));
                        plusButton.setIcon(new ImageIcon(plusIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        plusButton.setText("+"); // Alternatif jika ikon gagal dimuat
                    }
                    plusButton.setBounds(680, 70, 45, 20);
                    plusButton.setBorderPainted(false);
                    plusButton.setContentAreaFilled(false);
                    plusButton.addActionListener(e -> {
                        jumlahItem.put(minuman.getNama(), jumlahItem.get(minuman.getNama()) + 1);
                        totalItemLabel.setText("Total: " + jumlahItem.get(minuman.getNama()));
                        totalHargaLabel.setText("Rp " + (jumlahItem.get(minuman.getNama()) * minuman.getHarga()));
                    });
                    itemPanel.add(plusButton);

                    // Tombol - (dengan ikon)
                    JButton minusButton = new JButton();
                    try {
                        ImageIcon minusIcon = new ImageIcon(getClass().getResource("/Images/icon/minus.png"));
                        minusButton.setIcon(new ImageIcon(minusIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        minusButton.setText("-"); // Alternatif jika ikon gagal dimuat
                    }
                    minusButton.setBounds(740, 70, 45, 20);
                    minusButton.setBorderPainted(false);
                    minusButton.setContentAreaFilled(false);
                    minusButton.addActionListener(e -> {
                        int currentTotal = jumlahItem.get(minuman.getNama());
                        if (currentTotal > 1) {
                            jumlahItem.put(minuman.getNama(), currentTotal - 1);
                            totalItemLabel.setText("Total: " + jumlahItem.get(minuman.getNama()));
                            totalHargaLabel.setText("Rp " + (jumlahItem.get(minuman.getNama()) * minuman.getHarga()));
                        } else {
                            jumlahItem.remove(minuman.getNama());
                            keranjangMinuman.remove(minuman);
                            contentPanel.remove(itemPanel);
                            contentPanel.revalidate();
                            contentPanel.repaint();
                        }
                    });
                    itemPanel.add(minusButton);

                    itemPanels.put(minuman.getNama(), itemPanel);

                    // Tambahkan itemPanel ke verticalGroup
                    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(itemPanel));

                    verticalGroup.addGap(20);
                    // Tambahkan itemPanel ke horizontalGroup
                    horizontalGroup.addComponent(itemPanel);
                }
            }
            //Makanan
            for (Makanan makanan : keranjangMakanan) {
                int totalItem = jumlahItem.getOrDefault(makanan.getNama(), 1);

                if (!itemPanels.containsKey(makanan.getNama())) {
                    JPanel itemPanel = new JPanel();
                    itemPanel.setLayout(null);
                    itemPanel.setPreferredSize(new Dimension(700, 100));
                    itemPanel.setBorder(new RoundBorder(20));
                    itemPanel.setOpaque(false);
                    
                    // Membuat JLabel dengan gambar bersudut melengkung
                    JLabel imageLabel = new JLabel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);

                            try {
                                // Ambil gambar dari path
                                ImageIcon imageIcon = new ImageIcon(getClass().getResource(makanan.getGambarPath()));
                                Image image = imageIcon.getImage();

                                // Gambar dengan sudut melengkung
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                g2d.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20)); // Sudut melengkung

                                // Gambar gambar dengan ukuran yang diatur
                                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                            } catch (Exception e) {
                                // Jika terjadi error, tampilkan placeholder teks
                                g.drawString("[Gambar]", getWidth() / 2 - 20, getHeight() / 2);
                            }
                        }
                    };

                    // Set ukuran dan posisi JLabel
                    imageLabel.setBounds(40, 10, 80, 80);
                    itemPanel.add(imageLabel);

                    // Panel untuk Nama dan Harga
                    JPanel infoPanel = new JPanel();
                    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                    infoPanel.setOpaque(false);

                    JLabel namaLabel = new JLabel(makanan.getNama());
                    namaLabel.setFont(new Font("Arial", Font.BOLD, 16));
                    infoPanel.setBounds(130, 10, 500, 60);
                    infoPanel.add(namaLabel);

                    // Menambahkan jarak antara nama dan harga
                    infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                    JLabel hargaLabel = new JLabel("Rp " + makanan.getHarga());
                    hargaLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                    hargaLabel.setForeground(Color.RED);
                    hargaLabel.setBounds(130, 30, 120, 50);
                    infoPanel.add(hargaLabel);

                    itemPanel.add(infoPanel);

                    // Total Item di kanan atas
                    JLabel totalItemLabel = new JLabel("Total: " + totalItem);
                    totalItemLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                    totalItemLabel.setForeground(Color.BLUE);
                    totalItemLabel.setBounds(700, 10, 80, 20);
                    itemPanel.add(totalItemLabel);

                    // Total Harga di bawah total item
                    JLabel totalHargaLabel = new JLabel("Rp " + totalItem * makanan.getHarga());
                    totalHargaLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                    totalHargaLabel.setForeground(Color.RED);
                    totalHargaLabel.setBounds(700, 30, 120, 50);
                    itemPanel.add(totalHargaLabel);

                    // Checkbox untuk memilih item
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.setBounds(10, 40, 20, 20);
                    checkBox.addActionListener(e -> {
                        double harga = makanan.getHarga() * jumlahItem.get(makanan.getNama());
                        if (checkBox.isSelected()) {
                            totalCheckout += harga;
                        } else {
                            totalCheckout -= harga;
                        }
                        // Gunakan thread untuk memperbarui footer
                        new Thread(() -> updateFooter(frame)).start();
                    });
                    itemPanel.add(checkBox);

                    // Tombol + (dengan ikon)
                    JButton plusButton = new JButton();
                    try {
                        ImageIcon plusIcon = new ImageIcon(getClass().getResource("/Images/icon/plus.png"));
                        plusButton.setIcon(new ImageIcon(plusIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        plusButton.setText("+"); // Alternatif jika ikon gagal dimuat
                    }
                    plusButton.setBounds(680, 70, 45, 20);
                    plusButton.setBorderPainted(false);
                    plusButton.setContentAreaFilled(false);
                    plusButton.addActionListener(e -> {
                        jumlahItem.put(makanan.getNama(), jumlahItem.get(makanan.getNama()) + 1);
                        totalItemLabel.setText("Total: " + jumlahItem.get(makanan.getNama()));
                        totalHargaLabel.setText("Rp " + (jumlahItem.get(makanan.getNama()) * makanan.getHarga()));
                    });
                    itemPanel.add(plusButton);

                    // Tombol - (dengan ikon)
                    JButton minusButton = new JButton();
                    try {
                        ImageIcon minusIcon = new ImageIcon(getClass().getResource("/Images/icon/minus.png"));
                        minusButton.setIcon(new ImageIcon(minusIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        minusButton.setText("-"); // Alternatif jika ikon gagal dimuat
                    }
                    minusButton.setBounds(740, 70, 45, 20);
                    minusButton.setBorderPainted(false);
                    minusButton.setContentAreaFilled(false);
                    minusButton.addActionListener(e -> {
                        int currentTotal = jumlahItem.get(makanan.getNama());
                        if (currentTotal > 1) {
                            jumlahItem.put(makanan.getNama(), currentTotal - 1);
                            totalItemLabel.setText("Total: " + jumlahItem.get(makanan.getNama()));
                            totalHargaLabel.setText("Rp " + (jumlahItem.get(makanan.getNama()) * makanan.getHarga()));
                        } else {
                            jumlahItem.remove(makanan.getNama());
                            keranjangMakanan.remove(makanan);
                            contentPanel.remove(itemPanel);
                            contentPanel.revalidate();
                            contentPanel.repaint();
                        }
                    });
                    itemPanel.add(minusButton);

                    itemPanels.put(makanan.getNama(), itemPanel);

                    // Tambahkan itemPanel ke verticalGroup
                    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(itemPanel));

                    verticalGroup.addGap(20);
                    // Tambahkan itemPanel ke horizontalGroup
                    horizontalGroup.addComponent(itemPanel);
                }
            }
            // jajan
            for (Jajan jajan : keranjangJajan) {
                int totalItem = jumlahItem.getOrDefault(jajan.getNama(), 1);

                if (!itemPanels.containsKey(jajan.getNama())) {
                    JPanel itemPanel = new JPanel();
                    itemPanel.setLayout(null);
                    itemPanel.setPreferredSize(new Dimension(700, 100));
                    itemPanel.setBorder(new RoundBorder(20));
                    itemPanel.setOpaque(false);
                    
                    // Membuat JLabel dengan gambar bersudut melengkung
                    JLabel imageLabel = new JLabel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);

                            try {
                                // Ambil gambar dari path
                                ImageIcon imageIcon = new ImageIcon(getClass().getResource(jajan.getGambarPath()));
                                Image image = imageIcon.getImage();

                                // Gambar dengan sudut melengkung
                                Graphics2D g2d = (Graphics2D) g;
                                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                g2d.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20)); // Sudut melengkung

                                // Gambar gambar dengan ukuran yang diatur
                                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                            } catch (Exception e) {
                                // Jika terjadi error, tampilkan placeholder teks
                                g.drawString("[Gambar]", getWidth() / 2 - 20, getHeight() / 2);
                            }
                        }
                    };

                    // Set ukuran dan posisi JLabel
                    imageLabel.setBounds(40, 10, 80, 80);
                    itemPanel.add(imageLabel);

                    // Panel untuk Nama dan Harga
                    JPanel infoPanel = new JPanel();
                    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                    infoPanel.setOpaque(false);

                    JLabel namaLabel = new JLabel(jajan.getNama());
                    namaLabel.setFont(new Font("Arial", Font.BOLD, 16));
                    infoPanel.setBounds(130, 10, 500, 60);
                    infoPanel.add(namaLabel);

                    // Menambahkan jarak antara nama dan harga
                    infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                    JLabel hargaLabel = new JLabel("Rp " + jajan.getHarga());
                    hargaLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                    hargaLabel.setForeground(Color.RED);
                    hargaLabel.setBounds(130, 30, 120, 50);
                    infoPanel.add(hargaLabel);

                    itemPanel.add(infoPanel);

                    // Total Item di kanan atas
                    JLabel totalItemLabel = new JLabel("Total: " + totalItem);
                    totalItemLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                    totalItemLabel.setForeground(Color.BLUE);
                    totalItemLabel.setBounds(700, 10, 80, 20);
                    itemPanel.add(totalItemLabel);

                    // Total Harga di bawah total item
                    JLabel totalHargaLabel = new JLabel("Rp " + totalItem * jajan.getHarga());
                    totalHargaLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                    totalHargaLabel.setForeground(Color.RED);
                    totalHargaLabel.setBounds(700, 30, 120, 50);
                    itemPanel.add(totalHargaLabel);

                    // Checkbox untuk memilih item
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.setBounds(10, 40, 20, 20);
                    checkBox.addActionListener(e -> {
                        double harga = jajan.getHarga() * jumlahItem.get(jajan.getNama());
                        if (checkBox.isSelected()) {
                            totalCheckout += harga;
                        } else {
                            totalCheckout -= harga;
                        }
                        // Gunakan thread untuk memperbarui footer
                        new Thread(() -> updateFooter(frame)).start();
                    });
                    itemPanel.add(checkBox);

                    // Tombol + (dengan ikon)
                    JButton plusButton = new JButton();
                    try {
                        ImageIcon plusIcon = new ImageIcon(getClass().getResource("/Images/icon/plus.png"));
                        plusButton.setIcon(new ImageIcon(plusIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        plusButton.setText("+"); // Alternatif jika ikon gagal dimuat
                    }
                    plusButton.setBounds(680, 70, 45, 20);
                    plusButton.setBorderPainted(false);
                    plusButton.setContentAreaFilled(false);
                    plusButton.addActionListener(e -> {
                        jumlahItem.put(jajan.getNama(), jumlahItem.get(jajan.getNama()) + 1);
                        totalItemLabel.setText("Total: " + jumlahItem.get(jajan.getNama()));
                        totalHargaLabel.setText("Rp " + (jumlahItem.get(jajan.getNama()) * jajan.getHarga()));
                    });
                    itemPanel.add(plusButton);

                    // Tombol - (dengan ikon)
                    JButton minusButton = new JButton();
                    try {
                        ImageIcon minusIcon = new ImageIcon(getClass().getResource("/Images/icon/minus.png"));
                        minusButton.setIcon(new ImageIcon(minusIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                    } catch (Exception e) {
                        e.printStackTrace();
                        minusButton.setText("-"); // Alternatif jika ikon gagal dimuat
                    }
                    minusButton.setBounds(740, 70, 45, 20);
                    minusButton.setBorderPainted(false);
                    minusButton.setContentAreaFilled(false);
                    minusButton.addActionListener(e -> {
                        int currentTotal = jumlahItem.get(jajan.getNama());
                        if (currentTotal > 1) {
                            jumlahItem.put(jajan.getNama(), currentTotal - 1);
                            totalItemLabel.setText("Total: " + jumlahItem.get(jajan.getNama()));
                            totalHargaLabel.setText("Rp " + (jumlahItem.get(jajan.getNama()) * jajan.getHarga()));
                        } else {
                            jumlahItem.remove(jajan.getNama());
                            keranjangJajan.remove(jajan);
                            contentPanel.remove(itemPanel);
                            contentPanel.revalidate();
                            contentPanel.repaint();
                        }
                    });
                    itemPanel.add(minusButton);

                    itemPanels.put(jajan.getNama(), itemPanel);

                    // Tambahkan itemPanel ke verticalGroup
                    verticalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(itemPanel));

                    verticalGroup.addGap(20);
                    // Tambahkan itemPanel ke horizontalGroup
                    horizontalGroup.addComponent(itemPanel);
                }
            }
            for (JPanel panel : itemPanels.values()) {
                contentPanel.add(panel);
            }
        }

        // Set layout groups
        layout.setHorizontalGroup(horizontalGroup);
        layout.setVerticalGroup(verticalGroup);

        // Menggunakan JScrollPane untuk membuat panel dapat digulir
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Selalu tampilkan scrollbar vertikal
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel Footer
        JPanel footerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(172, 154, 21)); // Warna footer
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        footerPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));

        // Tombol Checkout di kiri (Lebih kecil dan warna putih)
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(Color.WHITE);
        checkoutButton.setBounds(700, 20, 10, 10);
        checkoutButton.setPreferredSize(new Dimension(150, 50));
        // Mengatur font tombol menggunakan font IrishGrover
        try {
            Font irishGrover = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("Images/fonts/IrishGrover.ttf")).deriveFont(20f); // Ukuran font 20
            checkoutButton.setFont(irishGrover);
        } catch (Exception e) {
            e.printStackTrace();
            checkoutButton.setFont(new Font("SansSerif", Font.BOLD, 20)); // Font default jika gagal
        }
        

        // Menambahkan efek hover untuk mengubah warna tombol saat mouse berada di atasnya
        checkoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                checkoutButton.setBackground(Color.WHITE); // Warna terang saat hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                checkoutButton.setBackground(Color.WHITE); // Kembali ke warna putih saat mouse keluar
            }
        });
        
        // Tombol Checkout di kiri (Lebih kecil dan warna putih)
        checkoutButton.addActionListener(e -> {
            // Menjalankan logika checkout dalam thread terpisah
            new Thread(() -> {
                // Periksa apakah keranjang kosong
                if (keranjangMinuman.isEmpty() && keranjangMakanan.isEmpty() && keranjangJajan.isEmpty()) {
                    SwingUtilities.invokeLater(() -> 
                        JOptionPane.showMessageDialog(frame, "Keranjang kosong! Silakan pilih menu di halaman menu.", "Peringatan", JOptionPane.WARNING_MESSAGE)
                    );
                    return;
                }
        
                // Periksa apakah ada item yang dicentang
                boolean itemDicentang = false;
                ArrayList<JPanel> panelsToRemove = new ArrayList<>(); // Menyimpan panel yang akan dihapus setelah checkout
                for (Component component : contentPanel.getComponents()) {
                    if (component instanceof JPanel) {
                        JPanel panel = (JPanel) component;
                        for (Component child : panel.getComponents()) {
                            if (child instanceof JCheckBox) {
                                JCheckBox checkBox = (JCheckBox) child;
                                if (checkBox.isSelected()) {
                                    itemDicentang = true;
                                    panelsToRemove.add(panel); // Menyimpan panel untuk dihapus
                                    // Hapus item yang dicentang dari keranjang
                                    String itemName = getItemNameFromPanel(panel); // Fungsi untuk mendapatkan nama item dari panel
                                    removeItemFromCart(itemName);
                                }
                            }
                        }
                    }
                }
        
                if (!itemDicentang) {
                    SwingUtilities.invokeLater(() -> 
                        JOptionPane.showMessageDialog(frame, "Silakan centang item yang ingin Anda checkout.", "Peringatan", JOptionPane.WARNING_MESSAGE)
                    );
                    return;
                }
        
                // Hapus panel-item yang dicentang
                for (JPanel panel : panelsToRemove) {
                    contentPanel.remove(panel);
                }
        
                // Revalidate dan repaint contentPanel untuk memperbarui tampilan
                SwingUtilities.invokeLater(() -> {
                    contentPanel.revalidate();
                    contentPanel.repaint();
                });
        
                // Lanjutkan ke halaman checkout setelah menghapus item
                SwingUtilities.invokeLater(() -> {
                    new HalamanCheckout();
                    frame.dispose();
                });
            }).start();
        });
        // m
        footerPanel.add(checkoutButton, BorderLayout.WEST);

        // Label Total harga di kanan
        JLabel totalHargaLabelFooter = new JLabel("Total: Rp 0", SwingConstants.CENTER);
        totalHargaLabelFooter.setFont(new Font("Arial", Font.BOLD, 16));
        totalHargaLabelFooter.setForeground(Color.RED);
        totalHargaLabelFooter.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        footerPanel.add(totalHargaLabelFooter, BorderLayout.EAST);

        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Fungsi untuk mendapatkan nama item dari panel (asumsi setiap panel memiliki label dengan nama item)
    private String getItemNameFromPanel(JPanel panel) {
        for (Component child : panel.getComponents()) {
            if (child instanceof JPanel) {
                JPanel infoPanel = (JPanel) child;
                for (Component infoChild : infoPanel.getComponents()) {
                    if (infoChild instanceof JLabel) {
                        JLabel label = (JLabel) infoChild;
                        if (label.getText() != null && !label.getText().isEmpty()) {
                            return label.getText(); // Mengembalikan nama item
                        }
                    }
                }
            }
        }
        return "";
    }

    // Fungsi untuk menghapus item dari keranjang berdasarkan nama item
    private void removeItemFromCart(String itemName) {
        // Menghapus item dari keranjangMinuman, keranjangMakanan, atau keranjangJajan sesuai dengan nama item
        keranjangMinuman.removeIf(minuman -> minuman.getNama().equals(itemName));
        keranjangMakanan.removeIf(makanan -> makanan.getNama().equals(itemName));
        keranjangJajan.removeIf(jajan -> jajan.getNama().equals(itemName));
        jumlahItem.remove(itemName); // Menghapus jumlah item dari HashMap
    }

    // Method untuk memperbarui total harga di footer
    private void updateFooter(JFrame frame) {
        JPanel footerPanel = (JPanel) frame.getContentPane().getComponent(2); // Mengambil footer panel
        JLabel totalHargaLabelFooter = (JLabel) footerPanel.getComponent(1);
        totalHargaLabelFooter.setText("Total: Rp " + totalCheckout);
    }
    
}


class RoundBorder extends AbstractBorder {
    private final int radius;

    public RoundBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.ORANGE);
        g.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(5, 5, 5, 5);
    }
}

