import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Menu extends JFrame {

    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(480, 560);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.white);
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> jenisKelaminComboBox;
    private JSlider kehadiranSlider;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel KehadiranLabel;  // Label Kehadiran

    public Menu() {
        listMahasiswa = new ArrayList<>();
        populateList();

        mahasiswaTable.setModel(setTable());

        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        String[] jenisKelaminData = { "Laki-laki", "Perempuan" };
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        deleteButton.setVisible(false);

        // Tambahkan MouseListener pada slider
        kehadiranSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int value = kehadiranSlider.getValue();  // Ambil nilai dari JSlider
                KehadiranLabel.setText("Kehadiran: " + value + "%");  // Perbarui JLabel
            }
        });

        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    deleteData();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = mahasiswaTable.getSelectedRow();
                String selectedNim = mahasiswaTable.getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getValueAt(selectedIndex, 3).toString();

                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);

                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);
            }
        });
    }

    // Add KehadiranLabel to JFrame
    private void addKehadiranLabel() {
        KehadiranLabel = new JLabel("Kehadiran");
        KehadiranLabel.setBounds(20, 180, 100, 20);  // Adjust position
        mainPanel.add(KehadiranLabel);  // Add to panel
    }

    public final DefaultTableModel setTable() {
        Object[] column = { "No.", "NIM", "Nama", "Jenis Kelamin", "Kehadiran" };
        DefaultTableModel temp = new DefaultTableModel(null, column);

        for (int i = 0; i < listMahasiswa.size(); i++) {
            Object[] row = new Object[5];
            row[0] = i + 1;
            row[1] = listMahasiswa.get(i).getNim();
            row[2] = listMahasiswa.get(i).getNama();
            row[3] = listMahasiswa.get(i).getJenisKelamin();
            row[4] = listMahasiswa.get(i).getKehadiran();
            temp.addRow(row);
        }

        return temp;
    }

    public void insertData() {
        // ambil value dari textfield, combobox, dan slider
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        Integer kehadiran = kehadiranSlider.getValue();  // Mendapatkan nilai Kehadiran dari slider

        // tambahkan data ke dalam list
        listMahasiswa.add(new Mahasiswa(nim, nama, jenisKelamin, kehadiran));

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert Berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambah!");
    }

    public void updateData() {
        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        Integer kehadiran = kehadiranSlider.getValue();

        // ubah data mahasiswa di list
        listMahasiswa.get(selectedIndex).setNim(nim);
        listMahasiswa.get(selectedIndex).setNama(nama);
        listMahasiswa.get(selectedIndex).setJenisKelamin(jenisKelamin);
        listMahasiswa.get(selectedIndex).setKehadiran(kehadiran);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update Berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
    }

    public void deleteData() {
        // Tampilkan konfirmasi sebelum menghapus data
        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        // Jika user menekan 'Yes'
        if (confirm == JOptionPane.YES_OPTION) {
            // hapus data dari list
            listMahasiswa.remove(selectedIndex);

            // update tabel
            mahasiswaTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Delete berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        }
    }

    public void clearForm() {
        // kosongkan semua textfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");

        // reset Kehadiran slider
        kehadiranSlider.setValue(50);  // reset slider ke nilai tengah (misal 50%)

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private void populateList() {
        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", 90));
        listMahasiswa.add(new Mahasiswa("2202292", "Muhammad Iqbal Fadhilah", "Laki-laki", 85));
        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki", 92));
        listMahasiswa.add(new Mahasiswa("2210239", "Muhammad Hanif Abdillah", "Laki-laki", 88));
        listMahasiswa.add(new Mahasiswa("2202046", "Nurainun", "Perempuan", 80));
        listMahasiswa.add(new Mahasiswa("2205101", "Kelvin Julian Putra", "Laki-laki", 95));
        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan", 87));
        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan", 93));
        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki", 76));
        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki", 89));
        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki", 82));
        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan", 91));
        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki", 94));
        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki", 77));
        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki", 85));
        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki", 90));
        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki", 80));
        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan", 92));
        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki", 88));
        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan", 85));
    }
}
