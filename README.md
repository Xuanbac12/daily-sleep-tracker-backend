# 💤 Daily Sleep Tracker - Backend

> Dự án backend giúp người dùng theo dõi và phân tích thói quen giấc ngủ mỗi ngày.

---

## 🚀 Tính năng chính

- 🧑 Đăng ký và đăng nhập người dùng với xác thực JWT
- 🛌 Thêm bản ghi thời gian đi ngủ và thức dậy mỗi ngày
- 📈 Phân tích dữ liệu giấc ngủ theo tuần
- 🔐 Bảo mật API với Spring Security
- 📄 API RESTful chuẩn hóa

---

## 🛠 Công nghệ sử dụng

| Công nghệ         | Vai trò                                  |
|------------------|--------------------------------------------|
| Java 24          | Ngôn ngữ lập trình chính                   |
| Spring Boot 3.4.4| Framework backend                          |
| Spring Data JPA  | Quản lý truy vấn và ánh xạ dữ liệu với MySQL |
| Spring Security  | Bảo vệ API và xử lý phân quyền             |
| JWT (jjwt)       | Xác thực người dùng bằng token             |
| Lombok           | Rút gọn code (getter/setter, constructor) |
| MySQL            | Cơ sở dữ liệu                              |
| Maven            | Quản lý thư viện và build dự án           |

---

## 📁 Cấu trúc thư mục

```text
backend/
├── src/main/java/com.example.backend/
│   ├── controllers/            # Xử lý request
│   │   ├── SleepEntryController
│   │   └── UserController
│   ├── models/                 # Entity JPA
│   │   ├── SleepEntry
│   │   └── User
│   ├── repositories/           # DAO layer
│   │   ├── SleepEntryRepository
│   │   └── UserRepository
│   ├── services/               # Business logic
│   │   ├── SleepEntryService
│   │   ├── UserService
│   │   └── UserDetailsServiceImpl
│   ├── utils/                  # Bảo mật và tiện ích
│   │   ├── JwtUtil
│   │   ├── JwtFilter
│   │   ├── SecurityBeans
│   │   └── SecurityConfig
│   └── BackendApplication      # Entry point
├── resources/
│   ├── static/
│   ├── templates/
│   └── application.properties
└── pom.xml
```

---

## ⚙️ Cấu hình MySQL

Trong `application.properties`, cấu hình kết nối như sau:

```properties
spring.application.name=backend
spring.datasource.url=jdbc:mysql://localhost:3306/sleep_tracker
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

```

> 💡 Đảm bảo đã tạo database `sleep_tracker` trong MySQL trước khi chạy app.

---

## ▶️ Cách chạy dự án

### 1. Clone project

```bash
git clone https://github.com/Xuanbac12/daily-sleep-tracker-backend.git
cd daily-sleep-tracker-backend
```

### 2. Chạy bằng IntelliJ IDEA

- Mở project bằng IntelliJ
- Cấu hình file `application.properties` cho đúng MySQL
- Run `BackendApplication.java`

### 3. Hoặc chạy bằng Maven

```bash
./mvnw spring-boot:run
```

---

## 🔐 JWT & Bảo mật

Hệ thống sử dụng `Spring Security` kết hợp với `JWT` để xác thực người dùng và bảo vệ các endpoint API.

- Người dùng đăng nhập sẽ nhận một **JWT token**
- Token được gửi trong header `Authorization` cho mỗi request cần xác thực
- Token sẽ được kiểm tra và giải mã trong `JwtFilter.java`

### 🧱 Các thành phần bảo mật:

| Class | Mô tả |
|-------|------|
| `SecurityConfig.java` | Cấu hình các quyền truy cập và filter xác thực |
| `JwtUtil.java`        | Tạo và kiểm tra JWT |
| `JwtFilter.java`      | Lọc request, xác thực JWT |
| `UserDetailsServiceImpl.java` | Lấy thông tin người dùng từ DB |
| `SecurityBeans.java`  | Cấu hình bean `PasswordEncoder` dùng `BCrypt` |

---

## 🧪 Testing

- Đã tích hợp thư viện test của Spring Boot:
    - `spring-boot-starter-test`
    - `spring-security-test`
- Test class mẫu:
    - `BackendApplicationTests.java`

---

## 🧑‍💻 Tác giả

- 👤 **Xuanbac12**
- 📫 GitHub: [github.com/Xuanbac12](https://github.com/Xuanbac12)

---

## 📜 Giấy phép

Dự án được phát triển phục vụ mục đích học tập, nghiên cứu và phi thương mại.
