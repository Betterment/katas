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

			expect(song_linker.link('hello', 'goodbye')).to eq songs
		end

		it 'can return sequence from a out of order acyclic song bank' do
			songs = %w(og goodbye hello)

			song_linker = SongLinker.new songs

			expect(song_linker.link('hello', 'goodbye')).to eq %w(hello og goodbye) 
		end

		it 'can return a sequence from multiple connecting songs' do
			songs = %w(og goodbye omg hello)

			song_linker = SongLinker.new songs

			expect(song_linker.link('hello', 'goodbye')).to eq %w(hello og goodbye) 
		end

		it 'can return a sequence from multiple connecting songs' do
			songs = %w(of goodbye omg hello)

			song_linker = SongLinker.new songs

			expect(song_linker.link('hello', 'goodbye')).to eq %w(hello omg goodbye) 
		end
	end

	describe "#link_all" do
		it "returns only valid paths in the face of dead ends" do
			songs = %w(hello of og goodbye)

			song_linker = SongLinker.new songs

			expect(song_linker.link_all('hello', 'goodbye')).to eq [%w(hello og goodbye)]
		end

		it "returns only valid paths in the face of cycles" do
			songs = %w(og go blab)

			song_linker = SongLinker.new songs

			expect(song_linker.link_all('og', 'blab')).to eq []
		end

	end

	describe '#are_linked' do
		it 'can determine if two songs are not linked' do
			song_linker = SongLinker.new nil

			expect(song_linker.are_linked('hello', 'goodbye')).to eq false
		end

		it 'can determine if two songs are linked' do
			song_linker = SongLinker.new nil

			expect(song_linker.are_linked('ab', 'bc')).to eq true
		end
	end

	describe '#next_songs_from' do

		it 'can find a linked song from a given song' do
			songs = %w(og goodbye hello)
			song_linker = SongLinker.new songs
			expect(song_linker.next_songs_from('ah')).to eq %w(hello)
		end

		it 'can find multiple linked songs from a given song' do
			songs = %w(og goodbye hello hi how)
			song_linker = SongLinker.new songs
			expect(song_linker.next_songs_from('ah')).to eq %w(hello hi how)
		end
	end
end