require "radio_playlist/song_linker"

describe SongLinker do 
	it 'can create a songlinker with a song bank' do
		songs = ['hello', 'who', 'am', 'i', 'goodbye']
		expect { SongLinker.new(songs) }.not_to raise_error
	end

	describe '#link' do
		it 'can link sequential songs' do
			songs = %w(hello og goodbye)

			song_linker = SongLinker.new songs

			expect(song_linker.link('hello', 'goodbye')).to eq(songs)
		end

		it 'can return sequence from a out of order song bank' do
			songs = %w(og goodbye hello)

			song_linker = SongLinker.new songs

			expect(song_linker.link('hello', 'goodbye')).to eq %w(hello og goodbye) 
		end
	end
end