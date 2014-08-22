require 'date'
class Student

    def initialize(first_name, last_name, middle_initial, favorite_color, gender, date_of_birth, grade)
        @first_name = first_name
        @last_name = last_name
        @favorite_color = favorite_color
        @middle_initial = middle_initial
        @gender = gender
        @date_of_birth = date_of_birth
        @grade = grade
    end

    attr_accessor :first_name, :last_name, :middle_initial, :favorite_color, :gender, :date_of_birth, :grade

end


def parse_comma_file(f)
    
    students = []

    f.each_line do |line|
        fields = line.split(',')

        student = Student.new(fields[1].strip, fields[0].strip, 'Unknown', fields[3].strip, fields[2].strip, Date.strptime(fields[4].strip, "%m/%d/%Y"), 2)

        students << student
    end

    f.close

    return students
end


print 'Starting Attendance'

file_commas = File.open("/Users/Greg/src/katas/attendance/input/comma.txt", "r")
file_pipe = File.open("/Users/Greg/src/katas/attendance/input/pipe.txt", "r")
file_space = File.open("/Users/Greg/src/katas/attendance/input/space.txt", "r")

comma_students = parse_comma_file(file_commas)

comma_student_sorted_by_gender_last_name = comma_students.sort do |a,b|
        [a.gender, a.last_name] <=> [b.gender, b.last_name] 
end

comma_student_sorted_by_birthdate = comma_students.sort {|a,b| a.date_of_birth <=> b.date_of_birth}
comma_student_sorted_by_last_name = comma_students.sort {|a,b| b.last_name <=> a.last_name}

#puts comma_students.inspect
comma_student_sorted_by_gender_last_name.each do |student|
    puts student.inspect
end
comma_student_sorted_by_birthdate.each do |student|
    puts student.inspect
end
comma_student_sorted_by_last_name.each do |student|
    puts student.inspect
end










