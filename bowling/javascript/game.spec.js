var Game = require('./game');
var chai = require('chai'),
    should = chai.should();

describe('Game', function () {
    var rolls;

    beforeEach(function () {
        rolls = [];
    });

    describe('is bonus', function () {
        it('should not think two gutters are a bonus', function () {
            Game.isBonus([0,0]).should.be.false;
        });
        it('should think a spare is a bonus', function () {
            Game.isBonus([6,4]).should.be.true;
        });
        it('should think a spare is a bonus when equal', function () {
            Game.isBonus([5,5]).should.be.true;
        });
        it('should think a strike is a bonus', function () {
            Game.isBonus([10]).should.be.true;
        });
    });

    describe('frames from rolls', function () {
        it('should proper frames for a spare followed by all one pin', function () {

            rolls.push(5);
            rolls.push(5);

            for (var i = 0; i < 18; i++) {
                rolls.push(1);
            }

            Game.framesFromRolls(rolls).should.eql([
                [5,5,1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1]
            ]);
        });

        it('should proper frames for a spare in the last frame', function () {

            for (var i = 0; i < 18; i++) {
                rolls.push(1);
            }

            rolls.push(5);
            rolls.push(5);
            rolls.push(1);

            Game.framesFromRolls(rolls).should.eql([
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [1, 1],
                [5, 5, 1]
            ]);
        });
    });

    describe('score', function () {

        it('should return 0 for a gutter game', function () {

            for (var i = 0; i < 20; i++) {
                rolls.push(0);
            }

            Game.score(rolls).should.equal(0);
        });

        it('should return 20 for 1 pin on each roll', function () {
            for (var i = 0; i < 20; i++) {
                rolls.push(1);
            }

            Game.score(rolls).should.equal(20);
        });

        it('should return 29 for a SPARE in the FIRST frame, one pin down in each other roll', function () {

            rolls.push(5);
            rolls.push(5);

            for (var i = 0; i < 18; i++) {
                rolls.push(1);
            }

            Game.score(rolls).should.equal(29);
        });

        it('should return 29 for a SPARE in the LAST frame, one pin down in each other roll', function () {

            for (var i = 0; i < 18; i++) {
                rolls.push(1);
            }

            rolls.push(5);
            rolls.push(5);

            rolls.push(1);

            Game.score(rolls).should.equal(29);
        });

        it('should return 30 for a STRIKE in the FIRST frame, one pin down in each other roll', function () {

            rolls.push(10);

            for (var i = 0; i < 18; i++) {
                rolls.push(1);
            }

            Game.score(rolls).should.equal(30);
        });

        it('should return 30 for a STRIKE in the LAST frame, one pin down in each other roll', function () {

            for (var i = 0; i < 18; i++) {
                rolls.push(1);
            }

            rolls.push(10);

            rolls.push(1);

            rolls.push(1);

            Game.score(rolls).should.equal(30);
        });

        it('should return 300 for a perfect game', function () {

            for (var i = 0; i < 12; i++) {
                rolls.push(10);
            }

            Game.score(rolls).should.equal(300);
        });

    });

});
