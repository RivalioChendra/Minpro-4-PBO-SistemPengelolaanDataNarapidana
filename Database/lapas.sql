-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 12, 2026 at 03:02 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lapas`
--

-- --------------------------------------------------------

--
-- Table structure for table `narapidana`
--

CREATE TABLE `narapidana` (
  `id_napi` varchar(10) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kasus` varchar(150) NOT NULL,
  `masa_tahanan` int NOT NULL,
  `nomor_sel` varchar(10) NOT NULL,
  `blok_sel` varchar(20) NOT NULL,
  `kategori` varchar(20) NOT NULL,
  `jenis_rehabilitasi` varchar(50) DEFAULT NULL,
  `tingkat_risiko` varchar(20) DEFAULT NULL,
  `uang_pengganti` bigint DEFAULT NULL,
  `kategori_pembunuhan` varchar(30) DEFAULT NULL,
  `nilai_kerugian` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `narapidana`
--

INSERT INTO `narapidana` (`id_napi`, `nama`, `kasus`, `masa_tahanan`, `nomor_sel`, `blok_sel`, `kategori`, `jenis_rehabilitasi`, `tingkat_risiko`, `uang_pengganti`, `kategori_pembunuhan`, `nilai_kerugian`) VALUES
('N001', 'Galeh Pramudi', 'Kepemilikan Sabu 5 Gram', 48, 'B1', 'Blok B', 'NARKOTIKA', 'Rehabilitasi Medis', NULL, NULL, NULL, NULL),
('N002', 'Rian Saputra', 'Percobaan Pengeboman', 180, 'A1', 'Blok A', 'TERORISME', NULL, 'Tinggi', NULL, NULL, NULL),
('N003', 'Glen Allen', 'Korupsi Dana Desa', 50, 'B2', 'Blok B', 'KORUPSI', NULL, NULL, 150000000, NULL, NULL),
('N004', 'Doni Hartono', 'Pembunuhan Berencana dalam Sengketa Warisan', 145, 'A2', 'Blok A', 'PEMBUNUHAN', NULL, NULL, NULL, 'Berencana', NULL),
('N005', 'Suki', 'Pencurian Kendaraan Bermotor', 18, 'C1', 'Blok C', 'PENCURIAN', NULL, NULL, NULL, NULL, 15000000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `narapidana`
--
ALTER TABLE `narapidana`
  ADD PRIMARY KEY (`id_napi`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
