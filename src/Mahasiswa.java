public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private Integer Kehadiran;

    // Constructor
    public Mahasiswa(String nim, String nama, String jenisKelamin, Integer Kehadiran) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.Kehadiran = Kehadiran;
    }

    // Setter untuk NIM
    public void setNim(String nim) {
        this.nim = nim;
    }

    // Setter untuk Nama
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Setter untuk Jenis Kelamin
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    // Setter untuk Kehadiran
    public void setKehadiran(Integer Kehadiran) {
        this.Kehadiran = Kehadiran;
    }

    // Getter untuk NIM
    public String getNim() {
        return this.nim;
    }

    // Getter untuk Nama
    public String getNama() {
        return this.nama;
    }

    // Getter untuk Jenis Kelamin
    public String getJenisKelamin() {
        return this.jenisKelamin;
    }

    // Getter untuk Kehadiran
    public Integer getKehadiran() {
        return this.Kehadiran;
    }
}
