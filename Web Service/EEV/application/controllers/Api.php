<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Api extends CI_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see https://codeigniter.com/user_guide/general/urls.html
	 */
     function __construct(){
        parent::__construct();
        $this->load->model('ModelDatabaseHelper');
        $this->load->helper('date');
    }

	public function index()
	{
		$this->load->view('welcome_message');
	}
    public function daftar()
	{
        
        $data_pendaftaran = array(
            'id_peserta'    => null,
            'no_identitas'  => $this->input->post('no_identitas'),
            'nama'          => $this->input->post('nama'),
            'jenis_kelamin' => $this->input->post('jenis_kelamin'),
            'tanggal_lahir' => $this->input->post('tanggal_lahir'),
            'alamat'        => $this->input->post('alamat'),
            'email'         => $this->input->post('email'),
            'password'      => $this->input->post('password'),
        );
        
        if($this->ModelDatabaseHelper->masukan_data('tabel_peserta', $data_pendaftaran)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Pendaftaran Peserta',
                    'umpan'     => true
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Pendaftaran Peserta',
                    'umpan'     => false
                )
            );
        }
		$this->load->view('respone', $respone);
	}
    public function daftarpenyelenggara()
	{
        
        $data_pendaftaran = array(
            'id_penyelenggara'  => null,
            'no_identitas'      => $this->input->post('no_identitas'),
            'nama_organisasi'   => $this->input->post('nama_organisasi'),
            'nama_penyelenggara'=> $this->input->post('nama_penyelenggara'),
            'alamat'            => $this->input->post('alamat'),
            'email'             => $this->input->post('email'),
            'password'          => $this->input->post('password'),
        );
        
        if($this->ModelDatabaseHelper->masukan_data('tabel_penyelenggara', $data_pendaftaran)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Pendaftaran Penyelenggara',
                    'umpan'     => true,
                    'keterangan'=> 'Pendaftaran Berhasil'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Pendaftaran Penyelenggara',
                    'umpan'     => false,
                    'keterangan'=> 'Pendaftaran Gagal'
                )
            );
        }
		$this->load->view('respone', $respone);
	}

    public function simpanhasilperhitungan()
	{
        
        $data_suara = array(
            'id_perolehan_suara'    => null,
            'id_pemilihan'          => $this->input->post('id_pemilihan'),
            'id_kandidat'           => $this->input->post('id_kandidat'),
            'perolehan_suara'       => $this->input->post('perolehan_suara'),
        );
        
        
        if($this->ModelDatabaseHelper->masukan_data('tabel_perolehan_suara', $data_suara)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Perhitungan Suara',
                    'umpan'     => true,
                    'keterangan'=> 'Perhitungan Suara Berhasil Disimpan'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Perhitungan Suara',
                    'umpan'     => false,
                    'keterangan'=> 'Perhitungan Suara Gagal Disimpan'
                )
            );
        }
		$this->load->view('respone', $respone);
	}
    
public function masuk(){
        $userst = $this->input->get_request_header('USER', TRUE);
        $tabel = "";
        $sebagai = "";
        $select = '';
        if($userst == "0"){
            $tabel = "tabel_penyelenggara";
            $sebagai = "Penyelenggara";
            $select = 'id_penyelenggara, no_identitas, nama_organisasi, nama_penyelenggara, alamat, email';
        }else if($userst == "1"){
            $tabel = "tabel_panitia";
            $sebagai = "Panitia";
            $select = 'id_penyelenggara, no_identitas, nama_organisasi, nama_penyelenggara, alamat, email';
            
        }else{
	        $tabel = "tabel_peserta";
            $sebagai = "Peserta";
            $select = 'id_peserta, no_identitas, nama, jenis_kelamin, tanggal_lahir, alamat, email';
	    }
        $data_masuk = array(
            'email'             => $this->input->post('email'),
            'password'          => $this->input->post('password'),
        );

        if($this->ModelDatabaseHelper->proses_masuk($tabel, $data_masuk)){
            
            if($userst == "1"){
                $data_masuk = array(
                    'tabel_panitia.email'             => $this->input->post('email'),
                    'tabel_panitia.password'          => $this->input->post('password'),
                );
                $data = $this->ModelDatabaseHelper->ambmbildatapenyelenggara($data_masuk);
            }else{
                $data = $this->ModelDatabaseHelper->getInfo($tabel, $data_masuk, $select);
            }
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Masuk Sebagai '.$sebagai,
                    'umpan'     => true,
                    'info_akun' => $data,
                    'keterangan'=> 'Berhasil'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Masuk Sebagai '.$sebagai,
                    'umpan'     => false,
                    'info_akun' => null,
                    'keterangan'=> 'Email atau Password yang anda masukan salah'
                )
            );
        }
        $this->load->view('respone', $respone);
    }



    public function tampilpemilihanpenyelenggara(){
        $where = array(
            'id_penyelenggara'             => $this->input->post('id_penyelenggara'),
        );
        $respone = array(
            'respone' => array(
                'pemintaan' => 'Menampilkan Data Pemilihan',
                'umpan'     => $this->ModelDatabaseHelper->ambildata("tabel_pemilihan", $where),
                'keterangan'=> 'Data Pemilihan Berhasil Ditampilkan'
            )
        );
        $this->load->view('respone', $respone);
    }

    public function tampilpanitia(){
        $where = array(
            'id_penyelenggara'             => $this->input->post('id_penyelenggara'),
        );
        $respone = array(
            'respone' => array(
                'pemintaan' => 'Menampilkan Data Pemilihan',
                'umpan'     => $this->ModelDatabaseHelper->ambildata("tabel_panitia", $where),
                'keterangan'=> 'Data Pemilihan Berhasil Ditampilkan'
            )
        );
        $this->load->view('respone', $respone);
    }

    public function tampilhasilperhitungan(){
        $where = array(
            'tabel_perolehan_suara.id_pemilihan'             => $this->input->post('id_pemilihan'),
        );
        $respone = array(
            'respone' => array(
                'pemintaan' => 'Menampilkan Data Perhitungan',
                'umpan'     => $this->ModelDatabaseHelper->hasilhasilperhitungan($where),
                'keterangan'=> 'Data Perhitungan Berhasil Ditampilkan'
            )
        );
        $this->load->view('respone', $respone);
    }

    public function updaselsesaipemilihan(){
        $set = array(
            'selesai'        => "1",
        );
        $where = array(
            'id_pemilihan'  => $this->input->post('id_pemilihan'),
        );
        if($this->ModelDatabaseHelper->update("tabel_pemilihan", $where, $set)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Merubah Pemilihan',
                    'umpan'     => true,
                    'keterangan'=> 'Pemilihan Berhasil Dirubah'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Publish Pemilihan',
                    'umpan'     => false,
                    'keterangan'=> 'Pemilihan Gagal Dirubah'
                )
            );
        }

        $this->load->view('respone', $respone);
        
    }

    public function updateterimapeserta(){
        $set = array(
            'status_permintaan'        => "1",
        );
        $where = array(
            'id_pemilihan'  => $this->input->post('id_pemilihan'),
            'id_peserta'    => $this->input->post('id_peserta'),
        );
        if($this->ModelDatabaseHelper->update("tabel_pemilihan_peserta", $where, $set)){
            $data_pendaftaran = array(
                'id_peserta_pemilihan'  => null,
                'id_pemilihan'          => $this->input->post('id_pemilihan'),
                'id_peserta'            => $this->input->post('id_peserta'),
            );
            $this->ModelDatabaseHelper->masukan_data('tabel_peserta_pemilihan', $data_pendaftaran);
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Peserta Diterima',
                    'umpan'     => true,
                    'keterangan'=> 'Peserta Berhasil Diterima'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Publish Pemilihan',
                    'umpan'     => false,
                    'keterangan'=> 'Pemilihan Gagal di Publish'
                )
            );
        }

        $this->load->view('respone', $respone);
        
    }

    public function tampilpeserta(){
        $where = array(
            'id_pemilihan'             => $this->input->post('id_pemilihan'),
            'status_permintaan'        => "1",
        );
        $respone = array(
            'respone' => array(
                'pemintaan' => 'Menampilkan Data Peserta Pemilihan',
                'umpan'     => $this->ModelDatabaseHelper->ambmbildatapeserta($where),
                'keterangan'=> 'Data Peserta Pemilihan Berhasil Ditampilkan'
            )
        );
        $this->load->view('respone', $respone);
    }
    public function tampilpesertapermintaan(){
        $where = array(
            'id_pemilihan'             => $this->input->post('id_pemilihan'),
            'status_permintaan'        => "0",
        );
        $respone = array(
            'respone' => array(
                'pemintaan' => 'Menampilkan Data Peserta Pemilihan',
                'umpan'     => $this->ModelDatabaseHelper->ambmbildatapeserta($where),
                'keterangan'=> 'Data Peserta Pemilihan Berhasil Ditampilkan'
            )
        );
        $this->load->view('respone', $respone);
    }

    public function hapuspeserta(){
        $where = array(
            'id_pemilihan'             => $this->input->post('id_pemilihan'),
            'id_peserta'               => $this->input->post('id_peserta'),
        );
        $tabel = array('tabel_peserta_pemilihan', 'tabel_pemilihan_peserta');
        $respone = array(
            'respone' => array(
                'pemintaan' => 'Keluarkan Peserta',
                'umpan'     => $this->ModelDatabaseHelper->hapus($tabel, $where),
                'keterangan'=> 'Mengeluarkan Peserta'
            )
        );
        $this->load->view('respone', $respone);
    }
    
	public function tampilpemilihanpeserta(){
        $where = array(
            'tabel_peserta_pemilihan.id_peserta' => $this->input->post('id_peserta'),
            'tabel_pemilihan.status'             => "1",
			
        );
        $respone = array(
            'respone' => array(
                'pemintaan' => 'Menampilkan Data Pemilihan',
                'umpan'     => $this->ModelDatabaseHelper->ambmbildatapemilihan($where),
                'keterangan'=> 'Data Pemilihan Berhasil Ditampilkan'
            )
        );
        $this->load->view('respone', $respone);
    }
    public function tampilkandidatpenyelenggara(){
        $where = array(
            'id_pemilihan'             => $this->input->post('id_pemilihan'),
        );
        $respone = array(
            'respone' => array(
                'pemintaan' => 'Menampilkan Data Kandidat',
                'umpan'     => $this->ModelDatabaseHelper->ambildata("tabel_kandidat", $where),
                'keterangan'=> 'Data Pemilihan Berhasil Ditampilkan'
            )
        );
        $this->load->view('respone', $respone);
    }
    public function tampilperhitungan(){
        $where = array(
            'id_pemilihan'            => $this->input->post('id_pemilihan'),
        );
        $respone = array(
            'respone' => array(
                'pemintaan' => 'Menampilkan Data Perhitungan',
                'umpan'     => $this->ModelDatabaseHelper->ambildata("tabel_pemilihan_suara", $where),
                'keterangan'=> 'Data Pemilihan Berhasil Ditampilkan'
            )
        );
        $this->load->view('respone', $respone);
    }
    public function hapuspemilihan(){
        $where = array(
            'id_pemilihan'  => $this->input->post('id_pemilihan')
        );
        if($this->ModelDatabaseHelper->hapus("tabel_pemilihan", $where)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Hapus Pemilihan',
                    'umpan'     => true,
                    'keterangan'=> 'Pemilihan Berhasil di Hapus'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Publish Pemilihan',
                    'umpan'     => true,
                    'keterangan'=> 'Pemilihan Gagal di Hapus'
                )
            );
        }
        $this->load->view('respone', $respone);
    }
    public function updatepemilihan(){
        $set = array(
            'nama_pemilihan'    => $this->input->post('nama_pemilihan'),
            'max_gabung'        => $this->input->post('max_gabung'),
            'max_vote'          => $this->input->post('max_vote'),
            'perhitungan'       => $this->input->post('perhitungan'),
            'keterangan'        => $this->input->post('keterangan'),
            'icon'              => $this->input->post('icon')
        );
        $where = array(
            'id_pemilihan'  => $this->input->post('id_pemilihan')
        );

        if($this->ModelDatabaseHelper->update("tabel_pemilihan", $where, $set)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Perubahan Pemilihan',
                    'umpan'     => true,
                    'keterangan'=> 'Pemilihan Berhasil di Rubah'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Perubahan Pemilihan',
                    'umpan'     => false,
                    'keterangan'=> 'Pemilihan Gagal di Rubah'
                )
            );
        }

        $this->load->view('respone', $respone);

    }
    public function updatestatuspublish(){
        $set = array(
            'status'        => $this->input->post('status'),
        );
        $where = array(
            'id_pemilihan'  => $this->input->post('id_pemilihan'),
        );
        if($this->ModelDatabaseHelper->update("tabel_pemilihan", $where, $set)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Publish Pemilihan',
                    'umpan'     => true,
                    'keterangan'=> 'Pemilihan Berhasil di Publish'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Publish Pemilihan',
                    'umpan'     => false,
                    'keterangan'=> 'Pemilihan Gagal di Publish'
                )
            );
        }

        $this->load->view('respone', $respone);
        
    }

    public function updatestatuselesai(){
        $set = array(
            'selesai'        => "1",
        );
        $where = array(
            'id_pemilihan'  => $this->input->post('id_pemilihan'),
        );
        if($this->ModelDatabaseHelper->update("tabel_pemilihan", $where, $set)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Publish Pemilihan',
                    'umpan'     => true,
                    'keterangan'=> 'Pemilihan Berhasil di Publish'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Publish Pemilihan',
                    'umpan'     => false,
                    'keterangan'=> 'Pemilihan Gagal di Publish'
                )
            );
        }

        $this->load->view('respone', $respone);
        
    }
    
    public function tambahpemilihan()
	{
        $datestring = '%Y-%m-%d';
        $time = time();
        $data_pendaftaran = array(
            'id_pemilihan'      => null,
            'id_penyelenggara'  => $this->input->post('id_penyelenggara'),
            'nama_pemilihan'    => $this->input->post('nama_pemilihan'),
            'tanggal_dibuat'    => mdate($datestring, $time),
            'max_gabung'        => $this->input->post('max_gabung'),
            'max_vote'          => $this->input->post('max_vote'),
            'perhitungan'       => $this->input->post('perhitungan'),
            'keterangan'        => $this->input->post('keterangan'),
            'icon'              => $this->input->post('icon'),
            'status'            => "0",
            'selesai'           => "0",
            
            
        );
        
        if($this->ModelDatabaseHelper->masukan_data('tabel_pemilihan', $data_pendaftaran)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Menambah Pemilihan',
                    'umpan'     => true,
                    'keterangan'=> 'Pemilihan Berhasil Ditambahkan'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Pendaftaran Penyelenggara',
                    'umpan'     => false,
                    'keterangan'=> 'Pemilihan Gagal Ditambahkan'
                )
            );
        }
		$this->load->view('respone', $respone);
	}

    public function tambahkandidat()
	{
        $data_pendaftaran = array(
            'id_kandidat'   => null,
            'id_pemilihan'  => $this->input->post('id_pemilihan'),
            'nama_kanidat'  => $this->input->post('nama_kanidat'),
            'slogan'        => $this->input->post('slogan'),
            'tgl_lahir'     => $this->input->post('tgl_lahir'),
            'alamat'        => $this->input->post('alamat'),
            'keterangan'    => $this->input->post('keterangan'),
            'gambar'        => $this->input->post('gambar'),
        );
        
        if($this->ModelDatabaseHelper->masukan_data('tabel_kandidat', $data_pendaftaran)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Menambah Kandidat',
                    'umpan'     => true,
                    'keterangan'=> 'Kandidat Berhasil Ditambahkan'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Menambah Kandidat',
                    'umpan'     => false,
                    'keterangan'=> 'Kandidat Gagal Ditambahkan'
                )
            );
        }
		$this->load->view('respone', $respone);
	}

    public function hapuskandidat(){
        $where = array(
            'id_kandidat'  => $this->input->post('id_kandidat')
        );
        if($this->ModelDatabaseHelper->hapus("tabel_kandidat", $where)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Hapus Kandidat',
                    'umpan'     => true,
                    'keterangan'=> 'Kandidat Berhasil di Hapus'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Kanidat Pemilihan',
                    'umpan'     => true,
                    'keterangan'=> 'Kandidat Gagal di Hapus'
                )
            );
        }
        $this->load->view('respone', $respone);
    }

    public function masukansuara()
	{
        $data_pendaftaran = array(
            'id_pemilihan_suara'    => null,
            'id_pemilihan'  => $this->input->post('id_pemilihan'),
            'id_peserta'    => $this->input->post('id_peserta'),
            'suara'         => $this->input->post('suara'),
        );
        
        if($this->ModelDatabaseHelper->masukan_data('tabel_pemilihan_suara', $data_pendaftaran)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Tambah Suara',
                    'umpan'     => true,
                    'keterangan'=> 'Saura Berhasil Ditambahkan'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Tambah Suara',
                    'umpan'     => false,
                    'keterangan'=> 'Saura Gagal Ditambahkan'
                )
            );
        }
		$this->load->view('respone', $respone);
	}

    public function updatepanitia(){
        $set = array(
            'nama'          => $this->input->post('nama'),
            'level'         => $this->input->post('level'),
            'email'         => $this->input->post('email'),
            'password'      => $this->input->post('password'),
        );
        $where = array(
            'id_panitia'  => $this->input->post('id_panitia')
        );

        if($this->ModelDatabaseHelper->update("tabel_panitia", $where, $set)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Perubahan Panitia',
                    'umpan'     => true,
                    'keterangan'=> 'Panitia Berhasil di Rubah'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Perubahan Panitia',
                    'umpan'     => false,
                    'keterangan'=> 'Panitia Gagal di Rubah'
                )
            );
        }

        $this->load->view('respone', $respone);

    }

    public function tambahpanitia()
	{
        $data_pendaftaran = array(
            'id_panitia'    => null,
            'id_penyelenggara'  => $this->input->post('id_penyelenggara'),
            'nama'          => $this->input->post('nama'),
            'level'         => $this->input->post('level'),
            'email'         => $this->input->post('email'),
            'password'      => $this->input->post('password'),
        );
        
        if($this->ModelDatabaseHelper->masukan_data('tabel_panitia', $data_pendaftaran)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Menambah Panitia',
                    'umpan'     => true,
                    'keterangan'=> 'Panitia Berhasil Ditambahkan'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Menambah Panitia',
                    'umpan'     => false,
                    'keterangan'=> 'Panitia Gagal Ditambahkan'
                )
            );
        }
		$this->load->view('respone', $respone);
	}
    public function hapuspanitia(){
        $where = array(
            'id_panitia'  => $this->input->post('id_panitia')
        );
        if($this->ModelDatabaseHelper->hapus("tabel_panitia", $where)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Hapus Panitia',
                    'umpan'     => true,
                    'keterangan'=> 'Panitia Berhasil di Hapus'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Hapus Panitia',
                    'umpan'     => true,
                    'keterangan'=> 'Panitia Gagal di Hapus'
                )
            );
        }
        $this->load->view('respone', $respone);
    }

    public function updatekandidat(){
        $set = array(
            'id_pemilihan'  => $this->input->post('id_pemilihan'),
            'nama_kanidat'  => $this->input->post('nama_kanidat'),
            'slogan'        => $this->input->post('slogan'),
            'tgl_lahir'     => $this->input->post('tgl_lahir'),
            'alamat'        => $this->input->post('alamat'),
            'keterangan'    => $this->input->post('keterangan'),
            'gambar'        => $this->input->post('gambar'),
        );
        $where = array(
            'id_kandidat'  => $this->input->post('id_kandidat')
        );

        if($this->ModelDatabaseHelper->update("tabel_kandidat", $where, $set)){
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Perubahan Pemilihan',
                    'umpan'     => true,
                    'keterangan'=> 'Pemilihan Berhasil di Rubah'
                )
            );
        }else{
            $respone = array(
                'respone' => array(
                    'pemintaan' => 'Perubahan Pemilihan',
                    'umpan'     => false,
                    'keterangan'=> 'Pemilihan Gagal di Rubah'
                )
            );
        }

        $this->load->view('respone', $respone);

    }

    public function tambahpermintaan()
	{
        $data_pendaftaran = array(
            'id_pemilihan_peserta'      => null,
            'id_pemilihan'              => $this->input->post('id_pemilihan'),
            'id_peserta'                => $this->input->post('id_peserta'),
            'status_permintaan'         => "0",
        );
        $data_masuk = array(
            'id_peserta'             => $this->input->post('id_peserta'),
            'id_pemilihan'           => $this->input->post('id_pemilihan'),
        );

        if(!$this->ModelDatabaseHelper->proses_masuk("tabel_pemilihan_peserta", $data_masuk)){
            if($this->ModelDatabaseHelper->masukan_data('tabel_pemilihan_peserta', $data_pendaftaran)){
                $respone = array(
                    'respone' => array(
                        'pemintaan' => 'Menambah Permintaan',
                        'umpan'     => true,
                        'keterangan'=> 'Permintaan Berhasil Ditambahkan'
                    )
                );
            }else{
                $respone = array(
                    'respone' => array(
                        'pemintaan' => 'Menambah Permintaan',
                        'umpan'     => false,
                        'keterangan'=> 'Permintaan Gagal Ditambahkan'
                    )
                );
            }
            $this->load->view('respone', $respone);
        }else{
            $respone = array(
                    'respone' => array(
                    'pemintaan' => 'Menambah Permintaan',
                    'umpan'     => false,
                    'keterangan'=> 'Permintaan Telah Terdaftar'
                )
            );
            $this->load->view('respone', $respone);
        }

	}
    
}