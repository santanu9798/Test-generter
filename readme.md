# Test Paper Generator System Design

## 1. **High-Level Overview**
The Test Paper Generator system is a backend-focused solution for generating test papers from a questionDTO bank. It supports:
- Storing and managing questions with metadata.
- Automated test paper generation based on user-defined criteria.
- Logging and export functionality.

### Core Features:
1. **Question Bank Management**: Add, edit, and fetch questions with metadata like topic, difficulty, and type.
2. **Test Paper Generation**: Select questions based on difficulty, topics, and marks distribution.
3. **Export and Audit Logs**: Export test papers and log system actions.

---

## 2. **Database Design**

### Schema Overview

#### **1. Questions Table**
```sql
CREATE TABLE Questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    text TEXT NOT NULL,
    type ENUM('MCQ', 'Descriptive', 'True/False') NOT NULL,
    topic VARCHAR(100) NOT NULL,
    difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
    options JSON NULL,
    correct_answer VARCHAR(255) NULL,
    marks INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### **2. Test Papers Table**
```sql
CREATE TABLE TestPapers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    total_marks INT NOT NULL,
    total_questions INT NOT NULL,
    created_by INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### **3. Test Paper Questions Table**
```sql
CREATE TABLE TestPaperQuestions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    test_paper_id INT NOT NULL,
    question_id INT NOT NULL,
    FOREIGN KEY (test_paper_id) REFERENCES TestPapers(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES Questions(id) ON DELETE CASCADE
);
```

#### **4. Users Table**
```sql
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Educator') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### **5. Audit Logs Table**
```sql
CREATE TABLE AuditLogs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    action VARCHAR(255) NOT NULL,
    entity VARCHAR(100) NOT NULL,
    entity_id INT NOT NULL,
    performed_by INT NOT NULL,
    performed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Optimization Features
- **Indexing**: Index frequently queried columns such as `topic`, `difficulty`, and `created_by`.
- **Partitioning**: Use range partitioning on `difficulty` for scalability.
- **Full-Text Search**: Enable full-text indexing on the `text` column of `Questions`.

---

## 3. **API Design**

### **Core Endpoints**

#### **1. User Management**
- **Signup**: `POST /api/users/signup`
- **Login**: `POST /api/users/login`

#### **2. Question Management**
- **Add Question**: `POST /api/questions`

  ```{
    "text": "ABC",
    "type": "MCQ",
    "topic": "Basic",
    "difficulty": "EASY",
    "options": [
    "a",
    "b",
    "c",
    "d"
    ],
    "correctAnswer": "c",
    "marks": 1
    } 
  ```
- **Fetch Questions**: `GET /api/questions`
    - Query parameters: `topic`, `difficulty`, `type`, `limit`.

#### **3. Test Paper Management**
- **Generate Test Paper**: `POST /api/test-papers/generate`
```
{
    "name": "Final",
    "courseName": "MCA",
    "courseCode": "MCA-503",
    "description": "This is final paper.",
    "totalMarks": 100,
    "totalQuestions": 65,
    "credit": 4,
    "totalTime": "2:30",
    "distributions": [
        {
            "type": "MCQ",
            "count": 30,
            "marksPerQuestion": 1
        },
        {
            "type": "Ture/False",
            "count": 20,
            "marksPerQuestion": 1
        },
        {
            "type": "Short",
            "count": 10,
            "marksPerQuestion": 2
        },
        {
            "type": "Long",
            "count": 5,
            "marksPerQuestion": 6
        }
    ]
}
```
- **Get Test Paper Details**: `GET /api/test-papers/{id}`
- **Export Test Paper**: `GET /api/test-papers/{id}/export?format=pdf`

#### **4. Audit Logs**
- **Fetch Logs**: `GET /api/audit-logs`
    - Query parameters: `entity`, `user_id`, `date`.

---

## 4. **Stored Procedure for Test Paper Generation**
Automate test paper generation using MySQL stored procedures.

```sql
DELIMITER $$
CREATE PROCEDURE GenerateTestPaper (
    IN paper_name VARCHAR(255),
    IN topics JSON,
    IN difficulty_distribution JSON,
    IN total_questions INT,
    IN total_marks INT,
    IN created_by INT
)
BEGIN
    DECLARE question_count INT DEFAULT 0;
    DECLARE question_id INT;
    DECLARE remaining_marks INT;

    -- Create a new test paper
    INSERT INTO TestPapers (name, total_marks, total_questions, created_by)
    VALUES (paper_name, total_marks, total_questions, created_by);

    SET @test_paper_id = LAST_INSERT_ID();
    SET remaining_marks = total_marks;

    -- Select questions based on difficulty distribution
    FOR difficulty IN ('Easy', 'Medium', 'Hard') DO
        SET question_count = JSON_EXTRACT(difficulty_distribution, CONCAT('$."', difficulty, '"'));

        WHILE question_count > 0 DO
            SELECT id INTO question_id
            FROM Questions
            WHERE difficulty = difficulty
              AND topic IN (SELECT JSON_UNQUOTE(JSON_EXTRACT(topics, '$[*]')))
              AND id NOT IN (SELECT question_id FROM TestPaperQuestions WHERE test_paper_id = @test_paper_id)
            ORDER BY RAND()
            LIMIT 1;

            INSERT INTO TestPaperQuestions (test_paper_id, question_id)
            VALUES (@test_paper_id, question_id);

            SET question_count = question_count - 1;
        END WHILE;
    END FOR;
END$$
DELIMITER ;
```

---

## 5. **Security Measures**
- Use **JWT** for authentication and include it in `Authorization` headers.
- **Role-Based Access Control (RBAC)**:
    - Admin: Manage all users and data.
    - Educator: Add questions and generate test papers.
- Encrypt sensitive data like passwords using **bcrypt**.

---

## 6. **Performance and Scalability**
- **Connection Pooling**: Use libraries like HikariCP.
- **Read Replicas**: Scale read-heavy workloads using MySQL replicas.
- **Caching**: Cache frequently accessed data (e.g., questions) using Redis.
- **Sharding**: Shard large tables (e.g., `Questions`) by `topic` or `difficulty`.

---

## 7. **Export Functionality**
- Support export formats: PDF and DOCX.
- Use libraries like **iText** or **Apache POI** for document generation.
- Example endpoint: `GET /api/test-papers/{id}/export?format=pdf`

---

## 8. **Conclusion**
This design ensures scalability, performance, and ease of use for generating test papers efficiently. The use of MySQL optimizations, secure APIs, and automation with stored procedures makes the system robust for educational purposes.

https://www.baeldung.com/java-pdf-creation