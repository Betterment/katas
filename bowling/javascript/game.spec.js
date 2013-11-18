var Game = require('./game');
var chai = require('chai'),
    should = chai.should();

describe('Game', function () {

    var game;

    beforeEach(function () {
        game = new Game();
    });

    describe('score', function () {

        it('should return 0 for a gutter game', function () {

            for (var i = 0; i < 20; i++) {
                game.roll(0);
            }

            game.score().should.equal(0);
        });

        it('should return 20 for 1 pin on each roll', function () {
            for (var i = 0; i < 20; i++) {
                game.roll(1);
            }

            game.score().should.equal(20);
        });

        it('should return 29 for a SPARE in the FIRST frame, one pin down in each other roll', function () {

            game.roll(5);
            game.roll(5);

            for (var i = 0; i < 18; i++) {
                game.roll(1);
            }

            game.score().should.equal(29);
        });

        it('should return 29 for a SPARE in the LAST frame, one pin down in each other roll', function () {

            for (var i = 0; i < 18; i++) {
                game.roll(1);
            }

            game.roll(5);
            game.roll(5);

            game.roll(1);

            game.score().should.equal(29);
        });

        it('should return 30 for a STRIKE in the LAST frame, one pin down in each other roll', function () {

            game.roll(10);

            for (var i = 0; i < 18; i++) {
                game.roll(1);
            }

            game.score().should.equal(30);
        });

        it('should return 30 for a STRIKE in the LAST frame, one pin down in each other roll', function () {

            for (var i = 0; i < 18; i++) {
                game.roll(1);
            }

            game.roll(10);

            game.roll(1);

            game.roll(1);

            game.score().should.equal(30);
        });

        it('should return 300 for a perfect game', function () {

            for (var i = 0; i < 12; i++) {
                game.roll(10);
            }

            game.score().should.equal(300);
        });

    });

});
