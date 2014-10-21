class SongLinker
	attr_reader :song_bank

	def initialize(song_bank)
		@song_bank = song_bank
	end

	def link_all(first, last, depth = 0)
		paths = [] 
		if depth > song_bank.length
			paths
		elsif (are_linked(first, last))
			paths << [first, last]
		else 
			next_songs_from(first).each do |next_song|
				link_all(next_song, last, depth + 1).each do |rest|
					paths << [first] + rest
				end
			end
		end

		paths
	end

	def link(first, last)
		link_all(first, last).first
	end

	def are_linked(song_a, song_b)
		song_a[song_a.size - 1] == song_b[0]
	end

	def next_songs_from(song_a)
		song_bank.select { |song_b| are_linked(song_a, song_b) }
	end
end