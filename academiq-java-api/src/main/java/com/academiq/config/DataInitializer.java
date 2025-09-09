package com.academiq.config;

import com.academiq.model.*;
import com.academiq.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepo;
    private final CourseRepository courseRepo;
    private final SubjectRepository subjectRepo;
    private final StudentRepository studentRepo;
    private final TeacherRepository teacherRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(AppUserRepository userRepo, CourseRepository courseRepo,
                          SubjectRepository subjectRepo, StudentRepository studentRepo,
                          TeacherRepository teacherRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.courseRepo = courseRepo;
        this.subjectRepo = subjectRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create sample users if they don't exist
        if (userRepo.count() == 0) {
            // Create a student user
            AppUser studentUser = AppUser.builder()
                    .username("student1")
                    .password(encoder.encode("password123"))
                    .role("ROLE_STUDENT")
                    .build();
            userRepo.save(studentUser);

            // Create a staff user
            AppUser staffUser = AppUser.builder()
                    .username("staff1")
                    .password(encoder.encode("password123"))
                    .role("ROLE_STAFF")
                    .build();
            userRepo.save(staffUser);

            System.out.println("Sample users created:");
            System.out.println("Student: username=student1, password=password123");
            System.out.println("Staff: username=staff1, password=password123");
        }

        // Create sample courses - DISABLED (using KTU data instead)
        // if (courseRepo.count() == 0) {
        //     Course cse = new Course();
        //     courseRepo.save(cse);
        // }

        // Create sample subjects
        if (subjectRepo.count() == 0) {
            Subject java = Subject.builder()
                    .subject_code("CS101")
                    .subject_name("Java Programming")
                    .credits(4)
                    .build();
            subjectRepo.save(java);

            Subject dbms = Subject.builder()
                    .subject_code("CS102")
                    .subject_name("Database Management Systems")
                    .credits(3)
                    .build();
            subjectRepo.save(dbms);
        }

        // Create sample teachers - DISABLED (using KTU data instead)
        // if (teacherRepo.count() == 0) {
        //     Teacher teacher1 = new Teacher();
        //     teacherRepo.save(teacher1);
        // }

        // Create sample students - DISABLED (using KTU data instead)
        // if (studentRepo.count() == 0) {
        //     Student student1 = new Student();
        //     studentRepo.save(student1);
        // }
    }
}