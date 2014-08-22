require "date"
require "awesome_print"
require "table_print"

students = []

File.open('input/comma.txt') do |f|
  f.lines.each do |line|
    data = line.split(",")
    temp_student = {}
    temp_student["last_name"]      = data[0].strip
    temp_student["first_name"]     = data[1].strip
    temp_student["middle_initial"] = ""
    temp_student["gender"]         = data[2].strip[0].upcase
    temp_student["favorite_color"] = data[3].strip
    temp_student["dob"]            = Date.strptime(data[4].strip, "%m/%d/%Y")
    temp_student["grade"]          = 2
    students << temp_student
  end
end

File.open('input/pipe.txt') do |f|
  f.lines.each do |line|
    data = line.split("|")
    temp_student = {}
    temp_student["last_name"]      = data[0].strip
    temp_student["first_name"]     = data[1].strip
    temp_student["middle_initial"] = data[2].strip
    temp_student["gender"]         = data[3].strip[0].upcase
    temp_student["favorite_color"] = data[4].strip
    temp_student["dob"]            = Date.strptime(data[5].strip, "%m-%d-%Y")
    temp_student["grade"]          = 1
    students << temp_student
  end
end

File.open('input/space.txt') do |f|
  f.lines.each do |line|
    data = line.split(" ")
    temp_student = {}
    temp_student["last_name"]      = data[0].strip
    temp_student["first_name"]     = data[1].strip
    temp_student["middle_initial"] = data[2].strip
    temp_student["gender"]         = data[3].strip[0].upcase
    temp_student["dob"]            = Date.strptime(data[4].strip, "%m-%d-%Y")
    temp_student["favorite_color"] = data[5].strip
    temp_student["grade"]          = 3
    students << temp_student
  end
end

result_one   = students.sort_by { |student| [student["gender"], student["last_name"]] }
result_two   = students.sort_by { |student| [student["dob"]] }
result_three = students.sort_by { |student| [student["last_name"]] }.reverse!

puts
tp result_one

puts
tp result_two

puts
tp result_three

def print_table(students)
  students.each do |student|
    puts "#{student["last_name"]} #{student["first_name"]} #{student["gender"]} #{student["dob"]} #{student["grade"]} #{student["favorite_color"]}"
  end
end

puts
print_table result_one

puts
print_table result_two

puts
print_table result_three

puts
ap result_one

puts
ap result_two

puts
ap result_three

puts
