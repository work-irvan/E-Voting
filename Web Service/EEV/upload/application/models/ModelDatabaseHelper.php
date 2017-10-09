<?php

class ModelDatabaseHelper extends CI_Model{

    function __construct(){
        parent::__construct();
        $this->load->dbforge();
    }

    public function masukan_data($tabel, $data){
        if($this->db->insert($tabel, $data)){
            return true;
        }else{
            return false;
        }
    }
    public function cekdata($tabel, $where){
        $this->db->where($where);
        $this->db->from($tabel);
        return $this->db->count_all_results(); // Produces an integer, like 17
    }

    public function proses_masuk($tabel, $data){
        $this->db->like($data);
        $this->db->from($tabel);
        if($this->db->count_all_results() < 1){
            return false;
        }else{
            return true;
        }
    }

    

    public function getInfo($tabel, $where, $select){
        $this->db->where($where);
        $this->db->select($select);
        return $query = $this->db->get($tabel)->result();
    }
    public function ambildata($tabel, $where){
        if($tabel == "tabel_pemilihan"){
            $this->db->order_by('tanggal_dibuat', 'DESC');                
        }
        
        $this->db->where($where);
        return $query = $this->db->get($tabel)->result();
    }
	public function ambmbildatapemilihan($where){
        $this->db->select('tabel_peserta_pemilihan.id_peserta, tabel_peserta_pemilihan.id_pemilihan, tabel_pemilihan.id_penyelenggara, tabel_pemilihan.tanggal_dibuat, tabel_pemilihan.nama_pemilihan, tabel_pemilihan.max_gabung, tabel_pemilihan.max_vote, tabel_pemilihan.perhitungan, tabel_pemilihan.keterangan, tabel_pemilihan.icon, tabel_pemilihan.status, tabel_pemilihan.selesai');
        $this->db->where($where);
        $this->db->from('tabel_peserta_pemilihan');
        $this->db->join('tabel_pemilihan', 'tabel_peserta_pemilihan.id_pemilihan = tabel_pemilihan.id_pemilihan');
        return $query = $this->db->get()->result();
    }

    public function ambmbildatapenyelenggara($where){
        $this->db->select('tabel_panitia.id_panitia, tabel_panitia.id_penyelenggara, tabel_panitia.nama, tabel_panitia.level, tabel_panitia.email, tabel_panitia.password, tabel_penyelenggara.nama_organisasi');
        $this->db->where($where);
        $this->db->from('tabel_panitia');
        $this->db->join('tabel_penyelenggara', 'tabel_panitia.id_penyelenggara = tabel_penyelenggara.id_penyelenggara');
        return $query = $this->db->get()->result();
    }

    public function ambmbildatapeserta($where){
        $this->db->select('tabel_pemilihan_peserta.id_pemilihan, tabel_pemilihan_peserta.id_peserta, tabel_peserta.nama, tabel_peserta.jenis_kelamin, tabel_peserta.tanggal_lahir, tabel_peserta.alamat, tabel_peserta.email');
        $this->db->where($where);
        $this->db->from('tabel_pemilihan_peserta');
        $this->db->join('tabel_peserta', 'tabel_pemilihan_peserta.id_peserta = tabel_peserta.id_peserta');
        return $query = $this->db->get()->result();
    }
    public function hasilperhitungan($where){
        $this->db->select('tabel_pemilihan_suara.id_peserta, tabel_pemilihan_suara.suara');
        $this->db->where($where);
        $this->db->from('tabel_pemilihan_suara');
        $this->db->join('tabel_peserta', 'tabel_pemilihan_suara.id_pemilihan = tabel_peserta.id_pemilihan');
        return $query = $this->db->get()->result();
    }
    public function hasilhasilperhitungan($where){
        $this->db->select('tabel_perolehan_suara.id_perolehan_suara, tabel_perolehan_suara.id_pemilihan, tabel_perolehan_suara.id_kandidat, tabel_perolehan_suara.perolehan_suara, tabel_kandidat.nama_kanidat, tabel_kandidat.gambar');
        $this->db->where($where);
        $this->db->order_by('tabel_perolehan_suara.perolehan_suara', 'DESC');
        $this->db->from('tabel_perolehan_suara');
        $this->db->join('tabel_kandidat', 'tabel_perolehan_suara.id_kandidat = tabel_kandidat.id_kandidat');
        return $query = $this->db->get()->result();
    }
    public function update($tabel, $where, $set){
        $this->db->set($set);
        $this->db->where($where);
        if($this->db->update($tabel)){
            return true;
        }else{
            return false;
        }
    }
    
    public function hapus($tabel, $where){
        $this->db->where($where);
        if($this->db->delete($tabel)){
            return true;
        }else{
            return false;
        }
    }

}