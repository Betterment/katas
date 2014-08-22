#!/usr/bin/env ruby
require 'table_print'
require 'date'

STUDENTS = []
DELIMS = ["|", ",", " "].freeze
GRADES = {
  "|" => "Grade 1",
  "," => "Grade 2",
  " " => "Grade 3"
}.freeze

COLUMN_ORDER = {
  "|" => %w/LastName FirstName MiddleInitial Gender FavoriteColor DateOfBirth/,
  "," => %w{LastName FirstName Gender FavoriteColor DateOfBirth},
  " " => %w(LastName FirstName MiddleInitial Gender DateOfBirth FavoriteColor),
  "desired" => %w(LastName FirstName Gender Birthdate Grade FavoriteColor)
}.freeze

def get_delim(line)
  if line.match /\|/
    "|"
  elsif line.match /,/
    ","
  else
    " "
  end
end

def normalize(field, delim)
  desired = COLUMN_ORDER['desired']
  columns = COLUMN_ORDER[delim]
  row = {}
  row["Grade"] = GRADES[delim]
  columns.each_with_index do |col,i|
    val = field[i]

    if col == 'DateOfBirth'
      # do it
    elsif col == 'Gender'
      val = val[0]
    end

    row[col] = val
  end
  row.keep_if { |k,v| desired.include?(k) }
end

Dir.glob("input/*.txt").each do |file_name|
  File.open file_name do |file|
    file.each_line do |line|
      delim ||= get_delim(line)
      fields = line.split(delim).map(&:strip)
      STUDENTS << normalize(fields,delim)
    end
  end
end

puts "\nsorted by gender (females before males) then by last name ascending.\n"
tp STUDENTS.sort_by { |s| [ s["Gender"], s["LastName"] ] }

puts "\nsorted by birth date, ascending.\n"
tp STUDENTS.sort_by { |s| [ s["DateOfBirth"] ] }

puts "\nsorted by last name, descending.\n"
tp STUDENTS.sort_by { |s| s["LastName"] }.reverse
