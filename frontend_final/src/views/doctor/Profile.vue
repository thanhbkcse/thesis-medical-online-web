<template>
  <div class="content">
    <div class="profile">
      <div class="profile1">
        <div class="avapr">
          <v-avatar color="#ffffff" size="128">Avatar</v-avatar>
        </div>

        <div class="baopr">
          <div class="tieudeprofile">
            <p><b>THÔNG TIN CÁ NHÂN</b></p>
          </div>
          <div class="noidungpr">
            <div class="ndpr1">
              <v-icon small color="#ffffff">event</v-icon>
              <p>18-06-2000</p>
            </div>
            <div class="ndpr1">
              <v-icon small color="#ffffff">male</v-icon>
              <p>Nam</p>
            </div>
            <div class="ndpr1">
              <v-icon small color="#ffffff">email</v-icon>
              <p>nguyenngoctan@gmail.com</p>
            </div>
            <div class="ndpr1">
              <v-icon small color="#ffffff">call</v-icon>
              <p>0848807873</p>
            </div>
            <div class="ndpr1">
              <v-icon small color="#ffffff">location_on</v-icon>
              <p>20 Lương Thạnh</p>
            </div>
          </div>
        </div>

        <div class="baopr">
          <div class="tieudeprofile">
            <p><b>TRÌNH ĐỘ TIẾNG ANH</b></p>
          </div>
          <div class="noidungpr">
            <div class="ndpr1">
              <p>Kỹ năng nghe: 8/10</p>
            </div>
            <div class="ndpr1">
              <p>Kỹ năng nói: 6/10</p>
            </div>
            <div class="ndpr1">
              <p>Kỹ năng đọc: 7/10</p>
            </div>
            <div class="ndpr1">
              <p>Kỹ năng viết: 6/10</p>
            </div>
            <div class="ndpr1">
              <p>Bằng cấp: Chứng chỉ Toeic</p>
            </div>
          </div>
        </div>
      </div>

      <div class="profile2">
        <div class="tenbacsi">
          <p><b>-----NGUYỄN NGỌC TÂN-----</b></p>
        </div>

        <div class="baopr2">
          <div class="tieudeprofile2">
            <p><b>MỤC TIÊU NGHỀ NGHIỆP</b></p>
          </div>
          <div class="noidungpr2">
            <div class="ndpr2">
              <p>
                - Khám chữa bệnh cứu người tận tâm với công việc, thể hiện được
                y đức của một người bác sĩ.
              </p>
            </div>
            <div class="ndpr2">
              <p>- Không ngừng học hỏi tiếp thu kiến thức.</p>
            </div>
          </div>
        </div>

        <div class="baopr2">
          <div class="tieudeprofile2">
            <p><b>HỌC VẤN</b></p>
          </div>
          <div class="noidungpr2">
            <div class="ndpr2">
              <p>02-2018 -> 02-2022</p>
            </div>
            <div class="ndpr2">
              <p>Đại học Y Dược TPHCM</p>
            </div>
            <div class="ndpr2">
              <p>Chuyên khoa Nội</p>
            </div>
            <div class="ndpr2">
              <p>Bằng cấp: Cử nhân đại học</p>
            </div>
            <div class="ndpr2">
              <p>Loại tốt nghiệp: Khá</p>
            </div>
          </div>
        </div>

        <div class="baopr2">
          <div class="tieudeprofile2">
            <p><b>KINH NGHIỆM LÀM VIỆC</b></p>
          </div>
          <div class="noidungpr2">
            <div class="ndpr2">
              <p>02-2019 -> 02-2022</p>
            </div>
            <div class="ndpr2">
              <p>Thực tập tại Bệnh viện TPHCM</p>
            </div>
            <div class="ndpr2">
              <p>Mô tả công việc:</p>
            </div>
            <div class="ndpr2">
              <p>- Hỗ trợ y tá</p>
            </div>
            <div class="ndpr2">
              <p>- Trực phòng y tế</p>
            </div>
            <div class="ndpr2">
              <p>- Học tập kiến thức</p>
            </div>
            <div class="ndpr2">
              <p>Loại tốt nghiệp: Khá</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="buttonsua">
      <v-btn elevation="2" color="#4caf50" style="color: #ffffff"
        >Chỉnh sửa</v-btn
      >
    </div>
  </div>
</template>

<style scoped>
.profile {
  display: flex;
  justify-content: center;
}
.profile1 {
  width: 30%;
  height: auto;
  background-color: #226a80;
  border-radius: 15px 0px 0px 15px;
}

.profile2 {
  width: 50%;
  height: auto;
  background-color: rgb(255, 255, 255);
  border-radius: 0px 15px 15px 0px;
}
.tieudeprofile,
.tieudeprofile2 {
  width: 100%;
  margin-bottom: 20px;
}
.tieudeprofile p {
  padding: 5px 10px;
  border-style: solid;
  border-color: #ffffff;
  width: fit-content;
  border-width: 2px;
  margin-left: auto;
  margin-right: auto;
  color: #ffffff;
}

.tieudeprofile2 p {
  padding: 5px 10px;
  border-style: solid;
  border-color: #226a80;
  width: fit-content;
  border-width: 2px;
  margin-left: auto;
  margin-right: auto;
  color: #226a80;
}

.ndpr1 {
  display: flex;
  width: fit-content;
  margin-left: 20%;
  margin-top: 12px;
  margin-bottom: 12px;
}

.ndpr2 {
  display: flex;

  margin-left: 20%;
  margin-top: 12px;
  margin-bottom: 12px;
}

.ndpr1 p {
  margin: 0px 10px;
  height: 16px;
  color: #ffffff;
  font-size: 15px;
}

.ndpr2 p {
  margin: 0px 10px;
  width: 80%;
  color: #226a80;
  font-size: 15px;
}

.baopr,
.baopr2 {
  margin-bottom: 40px;
  margin-top: 20px;
}

.tenbacsi {
  text-align: center;
  margin: 82px 0px;
}

.tenbacsi p {
  font-size: 20px;
}

.avapr {
  text-align: center;
  margin: 30px 0px;
}

.buttonsua {
  text-align: center;
  margin-top: 20px;
}
</style>
