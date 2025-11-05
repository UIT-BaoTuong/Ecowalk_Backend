Cách sử dụng API: API GET thì chỉ cần nhập url thì nó sẽ trả về dữ liệu, API POST thì phải thêm Request


API ở file UsersController
GET localhost:8080/api/users
POST localhost:8080/api/user/by_email
->Request mẫu
{
    "email":"tuong@gmail.com"
}
POST localhost:8080/api/user/by_phone_numbe
->Request mẫu
{
    "phoneNumber":"09670124289"
}
POST localhost:8080/api/exists_user/by_email
->Request mẫu
{
    "email":"tuong@gmail.com"
}
POST localhost:8080/api/exists_user/by_phone_number
->Request mẫu
{
    "phoneNumber":"09670124289"
}

API ở file AuthController
POST localhost:8080/api/register
->Request mẫu
{
    "full_name":"Nguyen Duy Bao Tuong",
    "email":"tuongndb1@gmail.com",
    "phone_number": "09331446771",
    "password": "12345678"
}

API ở file RunsController
POST localhost:8080/api/run_activity
->Request mẫu
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