class Student

	attr_accessor :last_name, :first_name, :middle_initial, :gender, :favorite_color, :date_of_birth, :class_room

	def to_s
		"#{@last_name} #{@first_name} #{@gender} #{@date_of_birth} #{@class_room} #{@favorite_color}"
	end
end
