# URL Manager Web Application

A JSP/Servlet web application for managing URLs with user authentication and session management.

## Prerequisites

- macOS (tested on macOS 23.6.0)
- Homebrew package manager
- Java Development Kit (JDK) 17 or later
- MySQL 8.0 or later
- Maven 3.8 or later
- Tomcat 9

## Installation Steps

1. **Install MySQL**

   ```bash
   brew install mysql
   brew services start mysql
   ```

2. **Install Tomcat**

   ```bash
   brew install tomcat@9
   ```

3. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd jsp-web
   ```

4. **Set up the database**

   ```bash
   mysql -u root < src/main/resources/schema.sql
   ```

5. **Configure database connection**
   Edit `src/main/resources/database.properties`:
   ```properties
   db.url=jdbc:mysql://localhost:3306/url_manager?useSSL=false&serverTimezone=UTC
   db.username=root
   db.password=your_password_here
   db.driver=com.mysql.cj.jdbc.Driver
   db.poolSize=10
   ```

## Running the Application

1. **Build the project**

   ```bash
   mvn clean package
   ```

2. **Deploy to Tomcat**

   ```bash
   cp target/url-manager-1.0-SNAPSHOT.war /opt/homebrew/opt/tomcat@9/libexec/webapps/
   ```

3. **Start Tomcat**

   ```bash
   /opt/homebrew/opt/tomcat@9/libexec/bin/startup.sh
   ```

4. **Access the application**
   Open your browser and navigate to:
   ```
   http://localhost:8080/url-manager-1.0-SNAPSHOT/login
   ```

## Stopping the Application

1. **Stop Tomcat**

   ```bash
   /opt/homebrew/opt/tomcat@9/libexec/bin/shutdown.sh
   ```

2. **Stop MySQL (optional)**
   ```bash
   brew services stop mysql
   ```

## Troubleshooting

1. **If Tomcat fails to start**

   - Check Tomcat logs:
     ```bash
     tail -f /opt/homebrew/opt/tomcat@9/libexec/logs/catalina.out
     ```
   - Verify Tomcat is running:
     ```bash
     ps aux | grep tomcat
     ```

2. **If database connection fails**

   - Verify MySQL is running:
     ```bash
     brew services list
     ```
   - Check database credentials in `database.properties`
   - Ensure the database exists:
     ```bash
     mysql -u root -e "SHOW DATABASES;"
     ```

3. **If application is not accessible**
   - Verify the WAR file is deployed:
     ```bash
     ls -l /opt/homebrew/opt/tomcat@9/libexec/webapps/
     ```
   - Check if the application directory exists:
     ```bash
     ls -l /opt/homebrew/opt/tomcat@9/libexec/webapps/url-manager-1.0-SNAPSHOT/
     ```

## Project Structure

```
jsp-web/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── urlmanager/
│   │   │           ├── dao/
│   │   │           ├── model/
│   │   │           ├── servlet/
│   │   │           └── util/
│   │   ├── resources/
│   │   │   ├── database.properties
│   │   │   └── schema.sql
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml
│   │       ├── css/
│   │       │   └── style.css
│   │       └── views/
│   │           ├── login.jsp
│   │           ├── register.jsp
│   │           ├── urls.jsp
│   │           └── edit-url.jsp
└── pom.xml
```

## Features

- User authentication (login/register)
- Session management
- URL management (add/edit/delete)
- Popular URLs view for guests
- Configurable URL list for authenticated users
- Responsive design
- Modern UI with unique styling

```bash
mvn clean package && cp target/url-manager-1.0-SNAPSHOT.war /opt/homebrew/opt/tomcat@9/libexec/webapps/
```
