require_relative 'student'
require 'Date'

class ClassRoom
	
	def initialize(delimiter, file_name, headers, date_format, name)
		@delimiter = delimiter
		@file_name = file_name
		@headers = headers
		@date_format = date_format
		@name = name
	end

	def read
		f = File.open(@file_name)
		f.lines.map do |line|
			self.parse(line)
		end
	end

	def parse(line)
		s = Student.new
		s.class_room = @name

		fields = line.split(@delimiter).map &:strip
		fields.each_with_index do |field, i|

			if @headers[i] == :date_of_birth
				s.date_of_birth = Date.strptime(field, @date_format)
			else
				s.public_send "#{@headers[i]}=", field
			end
		end

		s
	end

end