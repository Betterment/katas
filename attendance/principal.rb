#! /bin/ruby
require_relative 'class_room'

class_rooms = [
	ClassRoom.new(",", "input/comma.txt", [:last_name, :first_name, :gender, :favorite_color, :date_of_birth], "%m/%d/%Y", "grade 1"),
	ClassRoom.new("|", "input/pipe.txt", [:last_name, :first_name, :middle_initial, :gender, :favorite_color, :date_of_birth], "%m-%d-%Y", "grade 2"),
	ClassRoom.new(" ", "input/space.txt", [:last_name, :first_name, :middle_initial, :gender, :date_of_birth, :favorite_color], "%m-%d-%Y", "grade 3"),
]

students = class_rooms.map(&:read).flatten

sorted = students.sort do |a, b|
	a.date_of_birth <=> b.date_of_birth
end

sorted.each do |student|
	puts student
end

#iterate over 3 files for each one concat the contents into the roster

#sorted by gender (females before males) then by last name ascending.
#1. sorted by birth date, ascending.
#1. sorted by last name, descending.