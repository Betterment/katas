class SongLinker
	attr_reader :song_bank

	def initialize(song_bank)
		@song_bank = song_bank
	end

	def link(first, last)
		paths = [] 
		if (are_linked(first, last))
			return [first, last]
		else 
			paths << first
			next_songs_from(first).each do |next_song|
				paths += link(next_song, last)
			end
		end

		paths
	end

	def are_linked(song_a, song_b)
		song_a[song_a.size - 1] == song_b[0]
	end

	def next_songs_from(song_a)
		song_bank.select { |song_b| are_linked(song_a, song_b) }
	end
end