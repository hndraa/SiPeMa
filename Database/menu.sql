-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 14 Des 2024 pada 09.56
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `menu`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `jajan`
--

CREATE TABLE `jajan` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `deskripsi` text NOT NULL,
  `harga` double NOT NULL,
  `gambar_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `jajan`
--

INSERT INTO `jajan` (`id`, `nama`, `deskripsi`, `harga`, `gambar_path`) VALUES
(1, 'Es Krim', 'Es krim dengan berbagai pilihan rasa segar.', 15000, 'Images/jajan/Jajan 1.jpg'),
(2, 'Moci', 'Moci dengan isian kacang merah dan taburan kelapa.', 20000, 'Images/jajan/Jajan 2.jpg'),
(3, 'Cookies Oreo', 'Cookies dengan campuran oreo yang renyah.', 25000, 'Images/jajan/Jajan 3.jpeg'),
(4, 'Sosis Bakar', 'Sosis bakar dengan bumbu kecap manis dan pedas.', 18000, 'Images/jajan/Jajan 4.jpg'),
(5, 'Kue Putu', 'Jajanan tradisional dari parutan kelapa yang cocok untuk menemani santai', 9000, 'Images/jajan/Jajan 5.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `makanan`
--

CREATE TABLE `makanan` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `deskripsi` text NOT NULL,
  `harga` double NOT NULL,
  `gambar_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `makanan`
--

INSERT INTO `makanan` (`id`, `nama`, `deskripsi`, `harga`, `gambar_path`) VALUES
(1, 'Nasi Goreng', 'Nasi goreng dengan bumbu rempah khas Indonesia.', 25000, 'Images/makanan/Makanan 1.jpeg'),
(2, 'Mie Ayam', 'Mie dengan potongan ayam dan saos khas.', 20000, 'Images/makanan/Makanan 2.jpeg'),
(3, 'Sate Ayam', 'Sate ayam dengan bumbu kacang.', 30000, 'Images/makanan/Makanan 3.jpeg'),
(4, 'Rendang', 'Daging sapi dimasak dengan bumbu khas Minang.', 45000, 'Images/makanan/Makanan 4.jpeg'),
(5, 'Rendang', 'Makanan khas padang dengan rempah yang menggugah selera makan.', 45300, 'Images/makanan/Makanan 5.jpeg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `minuman`
--

CREATE TABLE `minuman` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `deskripsi` text NOT NULL,
  `harga` double NOT NULL,
  `gambar_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `minuman`
--

INSERT INTO `minuman` (`id`, `nama`, `deskripsi`, `harga`, `gambar_path`) VALUES
(1, 'Boba Coklat', 'Minuman boba dengan coklat manis dan kenyal.', 22000, 'Images/minuman/Minuman 1.png'),
(2, 'Es Teh Lemon', 'Es teh dengan tambahan lemon segar.', 15000, 'Images/minuman/Minuman 2.jpeg'),
(3, 'Pop Ice', 'Minuman es serut dengan sirup rasa buah yang segar.', 18000, 'Images/minuman/Minuman 3.jpeg'),
(4, 'Americano', 'Kopi hitam tanpa susu yang kuat dan pekat.', 25000, 'Images/minuman/Minuman 4.jpg'),
(5, 'Jus Jambu', 'Segelas jus jambu yang segar dengan rasa manis alami, kaya akan vitamin C dan cocok untuk menyegarkan tubuh.', 15500, 'Images/minuman/Minuman 5.jpeg'),
(6, 'Air Mineral', 'Air mineral murni yang menyegarkan dan membantu menjaga tubuh tetap terhidrasi sepanjang hari.', 5200, 'Images/minuman/Minuman 6.jpg'),
(7, 'Coklat Panas', 'Minuman coklat panas yang kaya rasa, sempurna untuk menghangatkan tubuh di cuaca dingin.', 20500, 'Images/minuman/Minuman 7.jpeg'),
(8, 'Susu Segar', 'Susu segar dengan rasa lembut dan creamy, mengandung nutrisi yang baik untuk tubuh.', 18000, 'Images/minuman/Minuman 8.jpeg'),
(9, 'Sprite', 'Minuman soda rasa lemon-lime yang menyegarkan dengan gelembung yang menyenangkan.', 12000, 'Images/minuman/Minuman 9.jpeg');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `jajan`
--
ALTER TABLE `jajan`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `makanan`
--
ALTER TABLE `makanan`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `minuman`
--
ALTER TABLE `minuman`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `jajan`
--
ALTER TABLE `jajan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `makanan`
--
ALTER TABLE `makanan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `minuman`
--
ALTER TABLE `minuman`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
