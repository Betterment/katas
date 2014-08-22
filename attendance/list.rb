require 'optparse'
require 'date'

options = {}
options[:files] = []
options[:sortBy] = 'LastName'

OptionParser.new do |opts|
    opts.banner = 'Usage: list.rb -f file [-f file...] -s sortBy'

    opts.on('-f', '--file FILE', 'Include this file') do |file|
        options[:files] << file
    end

    opts.on('-s', '--sort SORT', 'Sort by this column') do |sort|
        options[:sortBy] = sort
    end
end.parse!

# returns [ { field: value } ]
def parse_file(contents)
    result = []

    contents.split(/\n/).each do |line|
        parser = determine_csv_type(line)

        parsed = parser.call(line)

        if parsed
            result << parsed
        end
    end

    result
end

def determine_csv_type(contents)
    if contents.count('|') == 5
        parser = lambda { |line| pipe_parser(line) }
    elsif contents.count(',') == 4
        parser = lambda { |line| comma_parser(line) }
    elsif contents.length == 0
        parser = lambda { |line| noop_parser(line) }
    else
        parser = lambda { |line| space_parser(line) }
    end

    parser
end

def noop_parser(line)
    nil
end

def pipe_parser(line)
    fields = [:last_name, :first_name, :middle_initial, :gender, :favorite_color, :date_of_birth]
    parse(fields, line, /\|/, 1)
end

def normalize_field(fieldname, field)
    case fieldname
    when :gender
        normalize_gender(field)
    when :date_of_birth
        normalize_date(field)
    else
        field
    end
end

def comma_parser(line)
    fields = [:last_name, :first_name, :gender, :favorite_color, :date_of_birth]
    parse(fields, line, /,/, 2)
end

def space_parser(line)
    fields = [:last_name, :first_name, :middle_initial, :gender, :date_of_birth, :favorite_color]
    parse(fields, line, / /, 3)
end

def parse(fields, line, delimiter, grade)
    pieces = line.split(delimiter).map { |col| col.strip }
    result = {}

    fields.each_index do |i|
        field = fields[i]
        result[field] = normalize_field(field, pieces[i])

    end

    result[:grade] = grade

    result
end


def normalize_gender(gender)
    if gender[0] == 'M'
        return :m
    end

    return :f
end

def normalize_date(date)
    date.sub!('/', '-')
    Date.parse(date)
end

students = []

options[:files].each do |filename|
    File.open(filename) do |file|
        students = students.concat(parse_file(file.read))
    end
end

students.sort! do |left, right|
    case options[:sortBy]
    when 'LastName'
        right[:last_name] <=> left[:last_name]
    when 'Gender'
        if right[:gender] == left[:gender]
            left[:last_name] <=> right[:last_name]
        elsif left[:gender] == :f
            -1
        else
            1
        end
    when 'BirthDate'
        left[:date_of_birth] <=> right[:date_of_birth]
    end
end

students.each do |student|
    fields = [student[:last_name], student[:first_name], student[:gender], student[:date_of_birth], student[:grade], student[:favorite_color]]
    print fields.join(' ')
    print "\n"
end

