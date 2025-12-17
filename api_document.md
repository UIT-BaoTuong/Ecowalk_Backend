Cách sử dụng API: API GET thì chỉ cần nhập url thì nó sẽ trả về dữ liệu, API POST thì phải thêm Request


API ở file UsersController
GET localhost:8080/api/users

POST localhost:8080/api/user/by_email
{
    "email":"tuong@gmail.com"
}

POST localhost:8080/api/user/by_phone_numbe
{
    "phoneNumber":"09670124289"
}

POST localhost:8080/api/exists_user/by_email
{
    "email":"tuong@gmail.com"
}

POST localhost:8080/api/exists_user/by_phone_number
{
    "phoneNumber":"09670124289"
}

API ở file AuthController
POST localhost:8080/api/register
{
    "full_name":"Nguyen Duy Bao Tuong",
    "email":"tuongndb1@gmail.com",
    "phone_number": "09331446771",
    "password": "12345678"
}

POST localhost:8080/api/login
{
    "email": "thao@gmailcom",
    "password": "23521466"
}


API ở file RunsController
POST localhost:8080/api/run_activity
{
    "userId": 103,
    "startTime": "2025-11-02T08:00:00",
    "endTime": "2025-11-02T08:45:30",
    "distanceKm": 51.25,
    "coordinatesJson": [
    {
      "lat": 10.7769,
      "lng": 16.7009
    },
    {
      "lat": 10.7780,
      "lng": 106.7020
    }
  ]
}

POST localhost:8080/api/run_activity/by_id
{
    "id" : "1"
}

POST localhost:8080/api/run_activity/by_user_id
{
    "userId" : "103"
}

Kiểm tra đời sống của token
App JSON Web Token (JWT) Debugger: https://www.jwt.io/
Dùng postman để lấy refresh_token và access_token.
Nhập 2 trường thông tin vừa lấy được vào ô JSON Web Token (JWT) bên tay trái, xem thông tin và của token bên tay phải.

### API ở file RewardController
* **Lấy danh sách quà:**
    `GET localhost:8081/api/rewards`

* **Tạo quà mới (Dùng Link ảnh):**
    `POST localhost:8081/api/rewards`
    ```json
    {
        "name": "Balo",
        "cost": 1000,
        "description": "Cho trẻ mẫu giáo",
        "image_url": "[https://link-anh-cua-ban.com/hinh.jpg](https://link-anh-cua-ban.com/hinh.jpg)"
    }
    ```

* **Đổi quà (Redeem):**
    `POST localhost:8081/api/rewards/redeem`
    ```json
    { "userId": 27, "rewardId": 1 }
    ```

---

### API ở file UsersController (Mới thêm)
* **Đổi tên hiển thị:**
    `POST localhost:8081/api/users/27/update_name`
    ```json
    { "fullName": "Tên Mới Của Tôi" }
    ```

* **Upload Avatar (Cái này vẫn dùng form-data):**
    `POST localhost:8081/api/users/27/avatar`
    * *Lưu ý:* Chọn Body -> **form-data** (Vì upload từ điện thoại)
    * Key: `file` (Chọn file từ máy)
    Kiểm tra ảnh được tải lên chưa ở console.cloudinary.com (dùng mail của nhóm)

